package chat.server;

import chat.network.TCPConnection;
import chat.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args) {
        new ChatServer();

    }

    private ChatServer(){
        System.out.println("Server running... ");
        try(ServerSocket serverSocket = new ServerSocket(8189)){
            while(true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                }
            }catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {

    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {

    }

    @Override
    public synchronized void OnDisconnect(TCPConnection tcpConnection) {

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {

    }
}
