package ChatClient;

import ChatServer.Message;
import ChatServer.MessageType;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputThread extends Thread {
    public Client Client;
    public boolean IsActive;
    
    private BufferedReader _reader;
    
    public InputThread(Client client, BufferedReader cmdReader) {
        Client = client;
      
        _reader = cmdReader;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        IsActive = true;
        try {
            System.out.print("<Client>Input: ");
            while(IsActive) {
                String text = _reader.readLine();
                
                if(text.equals("QUIT")) {
                    Client.close();
                    return;
                }
                
                Message msg = new Message(MessageType.ROOM, Client.ClientName, "Default", text);
                Client.sendMessage(msg);
                
                System.out.print("<Client>Input: ");
            }
        } catch(ClassNotFoundException | IOException ex) {
            System.out.println("<Client>Exception while reading user input: " + ex.getMessage());
        }
        
        try {
            _reader.close();
        } catch (IOException ex) {
            Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
