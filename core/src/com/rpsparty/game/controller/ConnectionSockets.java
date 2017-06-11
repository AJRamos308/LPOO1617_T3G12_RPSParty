package com.rpsparty.game.controller;

import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConnectionSockets {
    /**
     * The singleton instance of this controller
     */
    private static ConnectionSockets instance;

    private Socket socket; //client socket; para onde vamos escrever
    private ServerSocket server;

    /**
     * Returns a singleton instance of a game controller
     *
     * @return the singleton instance
     */
    public static ConnectionSockets getInstance() {
        if (instance == null)
            instance = new ConnectionSockets();
        return instance;
    }

    /**
     * Class constructor
     */
    ConnectionSockets () {
        socket = null;
    }

    /**
     * Save the socket used for communication
     * @param socket Socket used for connection.
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }



    /**
     * Sends message to the other player by writing in the server socket.
     * @param s string of what is going to be sent
     */
    public void sendMessage (String s) {
        try {
            // write our entered message to the stream
            socket.getOutputStream().write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Receive message from the other player
     * @return answer from the other player.
     */
    public String receiveMessage () {
        if(!socket.isConnected())
            return "";
        String answer = "";
        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Buffer Reader criado.");

        try {
            // Read to the next newline (\n) and display that text on labelMessage
            answer = buffer.readLine();
            System.out.println("valor lido: " + answer);
            return answer;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Function called when we want to disconnect from the sockets.
     */
    public void reset() {
        System.out.println("cagou... :(");
        instance = null;
        if(socket != null) {
            System.out.println("vai apagar os sockets!");
            socket.dispose();
        }
    }


}
