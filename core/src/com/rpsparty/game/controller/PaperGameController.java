package com.rpsparty.game.controller;

public class PaperGameController {
    /**
     * The singleton instance of this controller
     */
    private static PaperGameController instance;
    private float timeToNextFrame;
    private float distance;
    private float opponentDistance;

    /**
     * class constructor
     */
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

    /**
     * update the length of toilet paper unrolled
     * @param d
     */
    public void setDistance(float d) { distance += d;}

    /**
     * set the time to change to the next frame of the animation
     * @param time
     */
    public void setTimeToNextFrame(float time) { timeToNextFrame = time; }

    /**
     * gets the time to change to the next frame of the animation
     * @return
     */
    public float getTimeToNextFrame() { return  timeToNextFrame; }

    /**
     * conclude who won the game
     */
    public void finalResult() {//ve quem e que ganhou o jogo

        ConnectionSockets.getInstance().sendMessage(Float.toString(distance)+"\n");
        System.out.println("escreveu para o oponente");
        opponentDistance = Float.parseFloat(ConnectionSockets.getInstance().receiveMessage());
        System.out.println("leu do oponente");
        if(distance != opponentDistance) {
            if (distance > opponentDistance) {//ganhei
                MatchController.getInstance().getSets().add(1);
                MatchController.getInstance().increaseSet();
            } else {
                MatchController.getInstance().getSets().add(0);
                MatchController.getInstance().increaseSet();
            }
        }
    }

    /**
     *
     * @return true if is a tie
     */
    public boolean isTie() { return distance == opponentDistance; }

    /**
     * reset class
     */
    public void reset() { instance = null; }

    /**
     * get player's game points
     * @return
     */
    public float getMyPoints() { return distance; }
}