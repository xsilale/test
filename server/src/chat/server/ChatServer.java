package chat.server;

import chat.network.TCPConnection;
import chat.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args) {
        new ChatServer();

    }

    private final ArrayList<TCPConnection> connections= new ArrayList<>();
    private final ArrayList<String> nickNames= new ArrayList<>();
    private boolean justConnected = false;

    private ChatServer(){
        System.out.println("Server running... ");
        try(ServerSocket serverSocket = new ServerSocket(8189)){
            while(true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
        justConnected = true;
        //todo: print all connections
        for (TCPConnection tcpCon:connections) {
            System.out.println("tcpConnection" + tcpCon);

        }

    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        System.out.println("xsilale_server");
        if (justConnected == true){
            nickNames.add(value);
            justConnected = false;
            sendToAllConnections("forList: " + value);
            for (String nickName:nickNames) {
                System.out.println("nickNames: " + nickName);

            }
            return;
        }

        sendToAllConnections(value);

    }

    @Override
    public synchronized void OnDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);

    }

    private void sendToAllConnections(String value){
        System.out.println(value);
        final int cnt = connections.size();
        for(int i = 0; i < cnt; i++){
            connections.get(i).sendString(value);
        }

    }
}
