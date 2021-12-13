import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private String userName;
    private static List<ClientHandler> clientHandlers = new ArrayList<>();

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        userName = input.readUTF();
        clientHandlers.add(this);
        broadcastMsg(String.format("has joined the chat"));
    }

     public void runClient() throws IOException {
        while(socket.isConnected()) {
            broadcastMsg(": " + input.readUTF());
        }
    }

    private void broadcastMsg(String msg) {
        try {
            for (ClientHandler client: clientHandlers) {
                if (!client.userName.equals(this.userName)) {
                    client.output.writeUTF(String.format("%s %s", userName, msg));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() {
        clientHandlers.remove(this);
        broadcastMsg("has left the chat");
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
