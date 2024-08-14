import java.io.*;
import java.net.*;

public class ThreadClient extends Thread {

    private final Socket client;

    public ThreadClient(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //ObjectInputStream para receber o nome do arquivo
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            DataOutputStream output = new DataOutputStream(client.getOutputStream());

            // Recebe o nome do arquivo
            String fileName = (String) inputStream.readObject();

            //path que está o arquivo
            String directoryPath = "C:\\Users\\Eric Ferreira\\sourceJDK\\project-sockets\\src\\utils\\";
            File file = new File(directoryPath + fileName);

            if (file.exists() && !file.isDirectory()) {
                // Leitura do arquivo solicitado
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[512];
                int read;

                // Lendo os bytes do arquivo e enviando para o socket
                while ((read = fileInputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }

                fileInputStream.close();
                System.out.println("Client successfully served: " + fileName +
                        client.getRemoteSocketAddress().toString());
            } else {
                System.out.println("File not found: " + fileName);
            }

            inputStream.close();
            output.close();
            client.close();
        } catch (Exception e) {
            System.out.println("Thread Exception: " + e.getMessage());
            try {
                client.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
