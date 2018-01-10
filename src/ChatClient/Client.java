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
    public String Password;
    public String Username;
    public String RoomName;
    public Map<String, List<String>> RoomMap;
    

    
    public Client(String serverHost, int serverPort, String password, String username, ChatFrame cF)  {
        
        ClientName = "default";
        IsActive = true;
        ServerHost = serverHost;
        ServerPort = serverPort;
        Password = password;
        Username = username;      
        ChatFrame= cF;
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
                    outputMessage(msg);
                }
              
            }
        } catch (Exception ex) {
            System.out.println("<Client>Exception while waiting for incoming messages: " + ex.getMessage());
        }
    }
    
    private void outputMessage(Message msg){
        String logText = ChatFrame.getTime(false) +" "+msg.Sender+": "+msg.Text[1];
        ChatFrame.updateArea(logText); 
    }
    
    private void handleInternalMessage(Message msg) {
        switch(msg.Text[0]){
            case "CLIENT_CONNECTED":
               break;
            case "CLIENT_DISCONNECTED":
                break;
            case "ROOM_ADDED": 
                List<String> emptyList= new ArrayList<>();
                RoomMap.put(msg.Text[1],emptyList);
                break;
            case "ROOM_RENAMED":
                List<String> list = RoomMap.get(msg.Text[1]);
                RoomMap.remove(msg.Text[1]);
                RoomMap.put(msg.Text[2], list);
                ChatFrame.updateRooms();
                break;
            case "ROOM_REMOVED":
                RoomMap.remove(msg.Text[1]);
            case "CLIENT_ROOM_CHANGED":
                    RoomMap.get(msg.Text[2]).remove(msg.Text[1]);
                    RoomMap.get(msg.Text[3]).add(msg.Text[1]);
                    ChatFrame.updateRooms();
                break;
            case "CLOSE_CONNECTION":
                Message recievedMsg = new Message(MessageType.RECEIVED, null, null, "");
                try {
                    sendMessage(recievedMsg);
                    closeInternal();
                } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            case "REPLY_CHANGE_ROOM":
                if(msg.Text[1].equals("true")){
                RoomName= msg.Text[3];
                ChatFrame.updateChatRoom();
                ChatFrame.updatePeople();
                ChatFrame.clearArea();
                }
                else ChatFrame.errorMsg();
                
                break;
            case "REPLY_STATUSLIST":
        
            try {
                //HashMap <String, String[]> statusMap = (HashMap<String, String[]>)_in.readObject();
                convertMap((HashMap<String, String[]>)_in.readObject());
                ChatFrame.updateRooms();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                break;
        }
    }

    public Map<String, List<String>> convertMap(HashMap<String, String[]> inMap){
        Map<String, List<String>> map= new HashMap<>();
        
        for(String key : inMap.keySet()){
            String[] peopleArray =inMap.get(key); 
            List<String> list= Arrays.asList(peopleArray);
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
    
    public boolean connect() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name = Username;
            String password = Password;
            
            Message credentialMsg = new Message(MessageType.LOGIN_REQUEST, "newClient", "server", new String[] {name, password});           
            sendMessage(credentialMsg);
            ClientName = name;
        
            Message replyMsg = (Message)_in.readObject();
            if(replyMsg.Text[0].equals("ACCEPTED")) {
                IsLoggedin =true;
                Message msg= new Message(MessageType.INTERNAL, Username,"server","REQUEST_STATUSLIST");
                sendMessage(msg);

                ChatFrame.updateRooms();
                return true;
            }
            else if (replyMsg.Text[0].equals("ACCEPTED_NEW")) {
                IsLoggedin =true;
                Message msg= new Message(MessageType.INTERNAL, Username,"server","REQUEST_STATUSLIST");
                sendMessage(msg);
                
                ChatFrame.updateRooms();
                return true;
            } 
            else if (replyMsg.Text[0].equals("WRONG_CREDENTIALS"))  {
                IsLoggedin =false;
                reader.close();
                return false;
            }
            else if (replyMsg.Text[0].equals("ALREADY_CONNECTED"))  {
                IsLoggedin =false;
                reader.close();
                return false;
            }
            
        } catch(ClassNotFoundException | IOException ex) {
            System.out.println("<Client>Exception while trying to connect to server: " + ex.getMessage());
        }  
        return false;
    }

   
}
