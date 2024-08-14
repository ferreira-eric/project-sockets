import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Client");
        JTextField txt = new JTextField(20);
        JLabel lblImage = new JLabel();
        JButton button = new JButton("Send");

        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("File Name:"));
        frame.add(txt);
        frame.add(button);
        frame.add(lblImage);
        frame.setSize(1000, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        button.addActionListener(e -> {
            try {
                //Define IP e Port
                int port = 12345;
                String ip = "localhost";

                //Define o path save
                String pathSave = "C:\\Users\\Eric Ferreira\\sourceJDK\\project-sockets\\src\\save\\";

                // Cria o Socket para buscar o arquivo no servidor
                Socket socket = new Socket(ip, port);

                //Enviando o nome do arquivo a ser baixado do servidor
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String nameFile = txt.getText();
                outputStream.writeObject(nameFile);

                //DataInputStream para processar os bytes recebidos
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                byte[] br = new byte[512];
                int read = inputStream.read(br);

                if (read <= 0) {
                    throw new Exception("File Not Found or Empty");
                }

                // FileOutputStream para salvar o arquivo recebido
                FileOutputStream fileOutputStream = new FileOutputStream(pathSave + nameFile);

                do {
                    fileOutputStream.write(br, 0, read);
                } while ((read = inputStream.read(br)) != -1);

                outputStream.close();
                inputStream.close();
                fileOutputStream.close();
                socket.close();

                // Exibe a imagem recebida
                ImageIcon img = new ImageIcon(pathSave + nameFile);
                lblImage.setIcon(img);
                lblImage.setText(nameFile);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Exceção: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}