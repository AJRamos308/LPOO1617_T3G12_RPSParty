package com.rpsparty.game.controller;

import com.badlogic.gdx.net.Socket;
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

    public void setWriteSocketm(Socket socket) {
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

    }

    /**
     * receber resposta do nosso oponente
     * @return resposta do outro utilizador/ jogador
     */
    public String receiveMessage () {
        String answer = "";
        return answer;
    }

    /**
     * devemos invocar o reset() sempre que nos queiramos desconectar
     * dos sockets
     */
    public void reset() {
        instance = null;
    }


}
