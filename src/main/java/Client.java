import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static int PORT = 12345;
    private static String HOST = "127.0.0.1";

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.println("please enter username to begin");
        String userName = s.nextLine();

        try(Socket socket = new Socket(HOST, PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            output.writeUTF(userName);
            new Thread(() -> {
                while (socket.isConnected()) {
                    try {
                        System.out.println(input.readUTF());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (socket.isConnected()) {
                output.writeUTF(s.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
