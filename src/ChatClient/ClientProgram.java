package ChatClient;

public class ClientProgram {

    public static void main(String[] args) {
        Client client = new Client("localhost", 1501);
        if(client.IsActive) client.start(); 
    }
    
}
