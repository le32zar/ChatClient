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
    private InputThread _userInput;

    public String ClientName;
    public boolean IsActive;
    public String ServerHost;
    public int ServerPort;

    public Client(String serverHost, int serverPort)  {
        ClientName = "default";
        IsActive = true;
        ServerHost = serverHost;
        ServerPort = serverPort;
        
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
            if(!connect()) {
                closeInternal();
                return;
            }
            _userInput.start();
            
            while(IsActive) {                
                Message msg = (Message)_in.readObject();
                printMessage(msg);
                
                if(msg.Type == MessageType.INTERNAL) {
                    if(msg.Text[0].equals("CONNECTION_CLOSED")) {
                        Message receivedMsg = new Message(MessageType.RECEIVED, null, null, "");
                        sendMessage(receivedMsg);
                        
                        closeInternal();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("<Client>Exception while waiting for incoming messages: " + ex.getMessage());
        }
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
        
        InputThread.currentThread().interrupt();
        _in.close();
        _out.close();
        _socket.close();
    } 
    
    private void printMessage(Message msg) {
        System.out.println("[" + msg.Sender + "] " + msg.Text[0]);
        
        for(Integer i = 1; i < msg.Text.length; i++) {
            System.out.println(msg.Text[i]);
        }
    }

    private boolean connect() throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("<Client>Username: ");
            String name = reader.readLine();
            System.out.print("<Client>Password: ");
            String password = reader.readLine();
            
            Message credentialMsg = new Message(MessageType.LOGIN_ATTEMPT, "newClient", "server", new String[] {name, password});           
            sendMessage(credentialMsg);
            ClientName = name;
        
            Message replyMsg = (Message)_in.readObject();
            if(replyMsg.Text[0].equals("ACCEPTED")) {
                _userInput = new InputThread(this, reader);
                return true;
            } else {
                reader.close();
                return false;
            }
        } catch(IOException ex) {
            System.out.println("<Client>Exception while trying to connect to server: " + ex.getMessage());
        }  
        return false;
    }

}
