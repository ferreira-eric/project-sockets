import java.net.*;

public class ServerWithThread {

    public static void main(String[] args) {

        try {
            int port = 12345;
            System.out.println("Initialize Server...");
            ServerSocket serv = new ServerSocket(port);
            System.out.println("Server listening port: " + port);

            //Aguarda conexões
            while(true) {
                Socket client = serv.accept();
                //Inicia thread do cliente
                ThreadClient thread = new ThreadClient(client);
                thread.start();
            }
        }
        catch(Exception e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}
