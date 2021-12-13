import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while(true) {
                ClientHandler clienthandler = new ClientHandler(serverSocket.accept());
                System.out.println("a new client has connected");
                new Thread(() -> {
                    try {
                        clienthandler.runClient();
                    } catch (IOException e) {
                        clienthandler.closeClient();
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
