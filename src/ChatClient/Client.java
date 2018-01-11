package ChatClient;

import ChatServer.Message;
import ChatServer.MessageType;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private Socket _socket;
    private ObjectInputStream _in;
    private ObjectOutputStream _out;
    
    public ChatFrame ChatFrame;
    public String ClientName;
    public boolean IsActive;
    public boolean IsLoggedin;
    public String ServerHost;
    public int ServerPort;
    public String RoomName;
    public Map<String, List<String>> RoomMap;
    

    
    public Client(String serverHost, int serverPort, ChatFrame chatFrame)  {
        
        RoomName = "Default";
        IsActive = true;
        ServerHost = serverHost;
        ServerPort = serverPort;
        ChatFrame= chatFrame;
        RoomMap = new HashMap<String, List<String>>();
        
        try {
            _socket = new Socket(ServerHost, ServerPort);
            _in = new ObjectInputStream(_socket.getInputStream());
            _out = new ObjectOutputStream(_socket.getOutputStream());
        } catch (IOException ex) {
            IsActive = false;
            System.out.println("<Client>Failed to connect to server: " + ex.getMessage());
        }
    }

    public void start() {
        try {
            if(!IsLoggedin) {
                closeInternal();
                return;
            }
            
            while(IsActive) {                
                Message msg = (Message)_in.readObject();
                
                
                if(msg.Type==MessageType.INTERNAL) {
                    handleInternalMessage(msg);
                }
                if(msg.Type== MessageType.ROOM){
                    printMessage(msg);
                }
              
            }
        }
        catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
//        } catch (Exception ex) {
//            ChatFrame.errorMsg("Exception while waiting for incoming messages: " + ex.getMessage());
//        }
    }
    
    private void printMessage(Message msg){
        String logText = ChatFrame.getTime(false) +" "+msg.Sender+": "+msg.Text[1];
        ChatFrame.updateChatArea(logText); 
    }
    
    private void handleInternalMessage(Message msg) {
        switch(msg.Text[0]){
            case "CLIENT_CONNECTED":
                RoomMap.get("Default").add(msg.Text[1]);
                System.out.println("Name: " + msg.Text[1]);
                
                ChatFrame.updateList();
               break;
            case "CLIENT_DISCONNECTED":
                RoomMap.get(msg.Text[2]).remove(msg.Text[1]);
                
                ChatFrame.updateList();
                break;
            case "ROOM_ADDED": 
                RoomMap.put(msg.Text[1], new ArrayList<>());
                
                ChatFrame.updateList();
                break;
            case "ROOM_RENAMED":
                List<String> list = RoomMap.get(msg.Text[1]);
                RoomMap.remove(msg.Text[1]);
                RoomMap.put(msg.Text[2], list);
                
                ChatFrame.updateList();
                break;
            case "ROOM_REMOVED":
                List<String> peopleList = RoomMap.get(msg.Text[1]);
                RoomMap.remove(msg.Text[1]);
                RoomMap.get("Default").addAll(peopleList);
                
                ChatFrame.updateList();
                break;
            case "CLIENT_ROOM_CHANGED":
                RoomMap.get(msg.Text[2]).remove(msg.Text[1]);
                RoomMap.get(msg.Text[3]).add(msg.Text[1]);
                
                ChatFrame.updateList();
                break;
            case "CLOSE_CONNECTION":
                Message recievedMsg = new Message(MessageType.RECEIVED, ClientName, "server", "");
                try {
                    sendMessage(recievedMsg);
                    closeInternal();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "REPLY_CHANGE_ROOM":
                if(msg.Text[1].equals("true")){
                    RoomName = msg.Text[3];
                    
                    RoomMap.get(msg.Text[2]).remove(ClientName);
                    RoomMap.get(msg.Text[3]).add(ClientName);
                    
                    ChatFrame.updateRoomName();
                    ChatFrame.clearArea();
                    ChatFrame.updateList();
                }
                else ChatFrame.errorMsg("Room change failed.");
                
                break;
            case "REPLY_STATUSLIST":
                try {
                    RoomMap = convertMap((HashMap<String, String[]>)_in.readObject());
                    ChatFrame.updateList();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    public Map<String, List<String>> convertMap(HashMap<String, String[]> inMap){
        Map<String, List<String>> map= new HashMap<>();
        
        for(String key : inMap.keySet()){
            String[] peopleArray =inMap.get(key); 
            ArrayList<String> list = new ArrayList<>();
            
            for(String user : inMap.get(key)) {
                list.add(user);
            }
            
            map.put(key, list);
        }
        RoomMap= map;
        return map;
    }
    
    public void sendMessage(Message msg) throws IOException {
        _out.writeObject(msg);
        _out.flush();
    }
     
    public void close() throws IOException, ClassNotFoundException {
        Message closeMsg = new Message(MessageType.INTERNAL , ClientName, "server", "CONNECTION_CLOSED");
        sendMessage(closeMsg);
        
        closeInternal();
    }   
    
    private void closeInternal() throws IOException {
        IsActive = false;
        
        _in.close();
        _out.close();
        _socket.close();
    } 
    
    public boolean connect(String name, String password) {
        try {
            Message credentialMsg = new Message(MessageType.LOGIN_REQUEST, "newClient", "server", new String[] {name, password});           
            sendMessage(credentialMsg);
            ClientName = name;
        
            Message replyMsg = (Message)_in.readObject();
            if(replyMsg.Text[0].equals("ACCEPTED")) {
                IsLoggedin = true;
                //RoomMap.put("Default", new ArrayList<>());
                //RoomMap.get("Default").add(name);
                
                Message msg= new Message(MessageType.INTERNAL, name, "server","REQUEST_STATUSLIST");
                sendMessage(msg);
                return true;
            }
            else if (replyMsg.Text[0].equals("ACCEPTED_NEW")) {
                IsLoggedin =true;
                //RoomMap.put("Default", new ArrayList<>());
                //RoomMap.get("Default").add(name);
                
                Message msg= new Message(MessageType.INTERNAL, name, "server","REQUEST_STATUSLIST");
                sendMessage(msg);
                return true;
            } 
            else if (replyMsg.Text[0].equals("WRONG_CREDENTIALS"))  {
                IsLoggedin =false;
                return false;
            }
            else if (replyMsg.Text[0].equals("ALREADY_CONNECTED"))  {
                IsLoggedin =false;
                return false;
            }
            
        } catch(ClassNotFoundException | IOException ex) {
            System.out.println("<Client>Exception while trying to connect to server: " + ex.getMessage());
        }  
        return false;
    }

   
}
