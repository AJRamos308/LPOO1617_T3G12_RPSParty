package com.rpsparty.game.controller;

/**
 * Created by afonso on 23/05/2017.
 */

public class PaperGameController {
    /**
     * The singleton instance of this controller
     */
    private static PaperGameController instance;
    private float timeToNextFrame;
    private float distance;
    private float opponentDistance;

    private PaperGameController() {
        timeToNextFrame = 0.1f;
        distance = 0;
        opponentDistance = 0;
    }

    public static PaperGameController getInstance() {
        if (instance == null)
            instance = new PaperGameController();
        return instance;
    }

    public void update(float increment){
        distance += Math.abs(increment);
    }

    public void setDistance(float d) { distance += d;}

    public void setTimeToNextFrame(float time) { timeToNextFrame = time; }

    public float getTimeToNextFrame() { return  timeToNextFrame; }

    public void finalResult() {//ve quem e que ganhou o jogo

        ConnectionSockets.getInstance().sendMessage(Float.toString(distance)+"\n");
        System.out.println("escreveu para o oponente");
        opponentDistance = Float.parseFloat(ConnectionSockets.getInstance().receiveMessage());
        System.out.println("leu do oponente");
        if(distance != opponentDistance) {
            if (distance > opponentDistance) {//ganhei
                MatchController.getInstance().getSets().add(1);
            } else {
                MatchController.getInstance().getSets().add(0);
            }
            MatchController.getInstance().increaseSet();
        }
    }

    public void reset() { instance = null; }
}
