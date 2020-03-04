package chat.client;

import chat.network.TCPConnection;
import chat.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    //dy

    private static final String IP_ADDR = "localhost";
    private static final int PORT = 8189;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });




    }

    private final JButton button = new JButton("connect");
    private final JList nickList = new JList();
    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField("xsilale");
    private final JTextField fieldInput = new JTextField();

    private TCPConnection connection;

    private ClientWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

        log.setEditable(false);
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);

        add(nickList, BorderLayout.EAST);
        //button.addActionListener(this);
        nickList.setVisible(false);

        add(button, BorderLayout.EAST);
        button.addActionListener(this);

        fieldInput.addActionListener(this);
        add(fieldNickname, BorderLayout.NORTH);
        add(fieldInput, BorderLayout.SOUTH);
      /*  try {
            connection = new TCPConnection(this, IP_ADDR,PORT);
        } catch (IOException e) {
            printMsg("Connection exception... " + e);
        }*/

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource()== button){

            try {
                connection = new TCPConnection(this, IP_ADDR,PORT);
            } catch (IOException e) {
                printMsg("Connection exception... " + e);
            }

            String msg = fieldNickname.getText();
            if (msg.equals("")) return;
            connection.sendString(msg);
            button.setVisible(false);
            fieldNickname.setEnabled(false);
            nickList.setVisible(true);

        }
        String msg = fieldInput.getText();
        if (msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendString(fieldNickname.getText() + ": " + msg);

    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready");

    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        System.out.println("xsilale_client");
        if(value.matches("forList:*")){


        }
        printMsg(value);


    }

    @Override
    public void OnDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection closed");

    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception... " + e);

    }

    private synchronized void printMsg (String msg){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
