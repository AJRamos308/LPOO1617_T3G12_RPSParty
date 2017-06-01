package com.rpsparty.game.controller;

import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by bibib on 06/05/2017.
 */

public class ConnectionSockets {
    /**
     * The singleton instance of this controller
     */
    private static ConnectionSockets instance;

    private Socket socket; //client socket; para onde vamos escrever
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

    ConnectionSockets () {
        socket = null;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }



    /**
     * enviar mensagem para o outro jogador; escrever no server socket
     * @param s o que vamos enviar
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
     * receber resposta do nosso oponente
     * @return resposta do outro utilizador/ jogador
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
     * devemos invocar o reset() sempre que nos queiramos desconectar
     * dos sockets
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
