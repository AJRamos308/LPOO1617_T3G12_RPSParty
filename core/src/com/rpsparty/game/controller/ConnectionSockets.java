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

    private Socket writeSocket; //client socket; para onde vamos escrever
    private Socket readSocket; //server socket; de onde vamos ler
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
        writeSocket = null;
        readSocket = null;
    }

    public void setWriteSocket(Socket socket) {
        writeSocket = socket;
    }


    public void setReadSocket(Socket socket) {
        readSocket = socket;
    }

    /**
     * enviar mensagem para o outro jogador; escrever no server socket
     * @param s o que vamos enviar
     */
    public void sendMessage (String s) {
        try {
            // write our entered message to the stream
            writeSocket.getOutputStream().write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * receber resposta do nosso oponente
     * @return resposta do outro utilizador/ jogador
     */
    public String receiveMessage () {
        if(!readSocket.isConnected())
            return "";
        String answer = "";
        BufferedReader buffer = new BufferedReader(new InputStreamReader(readSocket.getInputStream()));
        System.out.println("Buffer Reader criado.\no seu valor e: ");

        try {
            // Read to the next newline (\n) and display that text on labelMessage
            answer = buffer.readLine();
            System.out.println("vai retornar a resposta");
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
        instance = null;
        if(writeSocket != null && readSocket != null) {
            writeSocket.dispose();
            readSocket.dispose();
        }
    }


}
