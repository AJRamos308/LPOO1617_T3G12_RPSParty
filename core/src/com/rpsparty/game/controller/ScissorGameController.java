package com.rpsparty.game.controller;

import com.badlogic.gdx.Gdx;
import java.util.Random;
import com.rpsparty.game.controller.entities.RockGameBody;
import com.rpsparty.game.model.RockGameModel;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.point;

/**
 * Created by bibib on 22/05/2017.
 */

public class ScissorGameController {

    private static ScissorGameController instance;
    private float points = 0;
    private float opponentPoints = 0;
    private Integer radius;
    private Integer centerX = Gdx.graphics.getWidth()/2;
    private Integer centerY = Gdx.graphics.getHeight()/2;
    private int circleSize;

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    private ScissorGameController() {
        Random rand = new Random();
        circleSize = rand.nextInt(4);

    }

    public static ScissorGameController getInstance() {
        if (instance == null)
            instance = new ScissorGameController();
        return instance;
    }

    public void setRadius(Integer r) { radius = r; }

    public int getCircleSize() { return circleSize; }

    public float getMyPoints() { return points; }

    public float getOpponentPoints() { return opponentPoints; }

    public void setMyPoints(Integer x, Integer y) {
        double distance = Math.sqrt(Math.pow(x-centerX, 2)+Math.pow(y-centerY, 2));
        double deltaDist = Math.abs(radius-distance);
        if(deltaDist < 200) {
            if (deltaDist == 0) {
                points++;
            } else {
                points += 1 / deltaDist;
            }
            System.out.println(points);
        }
    }

    public void finalResult() {//ve quem e que ganhou o jogo
        ConnectionSockets.getInstance().sendMessage(Float.toString(points)+"\n");
        System.out.println("escreveu para o oponente");
        opponentPoints = Float.parseFloat(ConnectionSockets.getInstance().receiveMessage());
        System.out.println("leu do oponente");
        if(points != opponentPoints) {
            if (points > opponentPoints) {//ganhei
                MatchController.getInstance().getSets().add(1);
            } else {
                MatchController.getInstance().getSets().add(0);
            }
            MatchController.getInstance().increaseSet();
        }
    }
}
