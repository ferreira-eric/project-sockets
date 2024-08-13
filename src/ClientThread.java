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
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        button.addActionListener(e -> {
            try {
                // Cria o Socket para buscar o arquivo no servidor
                Socket socket = new Socket("localhost", 12345);

                //Enviando o nome do arquivo a ser baixado do servidor
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String nameFile = txt.getText();
                outputStream.writeObject(nameFile);

                //DataInputStream para processar os bytes recebidos
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                // FileOutputStream para salvar o arquivo recebido
                FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Eric Ferreira\\sourceJDK\\project-sockets\\src\\save\\" + nameFile);
                byte[] br = new byte[512];
                int read;
                while ((read = inputStream.read(br)) != -1) {
                    fileOutputStream.write(br, 0, read);
                }

                outputStream.close();
                inputStream.close();
                fileOutputStream.close();
                socket.close();

                // Exibe a imagem recebida
                ImageIcon img = new ImageIcon("C:\\Users\\Eric Ferreira\\sourceJDK\\project-sockets\\src\\save\\" + nameFile);
                lblImage.setIcon(img);
                lblImage.setText(nameFile);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Exceção: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}