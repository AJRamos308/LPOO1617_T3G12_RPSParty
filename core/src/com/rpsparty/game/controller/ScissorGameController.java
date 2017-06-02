package com.rpsparty.game.controller;

import com.badlogic.gdx.Gdx;
import java.util.Random;


public class ScissorGameController {

    private static ScissorGameController instance;
    private Float points;
    private Float opponentPoints;
    private Integer radius;
    private Integer centerX;
    private Integer centerY;
    private int circleSize;
    private double scissorPosition[];
    private double scissorLastVel[];
    private double scissorVel[];
    private float scissorAng;
    private double w;//constante a multiplicar por t, sendo angulo = w*t
    private float stateTime;


    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    private ScissorGameController() {
        Random rand = new Random();
        circleSize = rand.nextInt(4);
        points = 0.0f;
        opponentPoints = 0.0f;
        radius = 1;
        centerX = Gdx.graphics.getWidth()/2;
        centerY = Gdx.graphics.getHeight()/2;
        scissorPosition = new double[] {0, 0};
        scissorLastVel = new double[] {0, 1};
        scissorVel = new double[] {0, 1};
        scissorAng = 0;
        w = 1;
        stateTime = 0;
    }

    public static ScissorGameController getInstance() {
        if (instance == null)
            instance = new ScissorGameController();
        return instance;
    }

    public void setCenter(Integer Xcenter, Integer Ycenter){
        centerX = Xcenter;
        centerY = Ycenter;
    }

    public void setRadius(Integer r) { radius = r; }

    public int getCircleSize() { return circleSize; }

    public float getMyPoints() { return points; }

    public void setMyPoints(Integer x, Integer y) {
        double distance = Math.sqrt(Math.pow(x-centerX, 2)+Math.pow(y-centerY, 2));
        double deltaDist = Math.abs(radius-distance);
        if(deltaDist < 200) {
            if (deltaDist == 0) {
                points++;
            } else {
                points += (float)(1 / deltaDist);
            }
            System.out.println(points);
        }
    }

    public void finalResult() {//ve quem e que ganhou o jogo
        points /= radius;//para os pontos serem proporcionais ao tamanho da circunferencia
        ConnectionSockets.getInstance().sendMessage(Float.toString(points)+"\n");
        opponentPoints = Float.parseFloat(ConnectionSockets.getInstance().receiveMessage());

        if(points != opponentPoints) {
            if (points > opponentPoints) {//ganhei
                MatchController.getInstance().getSets().add(1);
                MatchController.getInstance().increaseSet();
            } else {
                MatchController.getInstance().getSets().add(0);
                MatchController.getInstance().increaseSet();
            }
        }
    }

    public Integer getRadius () { return radius; }

    public boolean setScissorPosition (float delta) {
        stateTime += delta;
        if(stateTime*w < 2*Math.PI) {
            scissorPosition[0] = centerX + radius * Math.cos(w * stateTime);
            scissorPosition[1] = centerY + radius * Math.sin(w * stateTime);
            scissorLastVel[0] = scissorVel[0];
            scissorLastVel[1] = scissorVel[1];
            scissorVel[0] = -radius * w * Math.sin(w * stateTime);
            scissorVel[1] = radius * w * Math.cos(w * stateTime);
            double prevNorma = Math.sqrt(Math.pow(scissorLastVel[0], 2) + Math.pow(scissorLastVel[1], 2));
            double norma = Math.sqrt(Math.pow(scissorVel[0], 2) + Math.pow(scissorVel[1], 2));
            scissorAng = (float) Math.acos((scissorLastVel[0] * scissorVel[0] + scissorLastVel[1] * scissorVel[1]) / (prevNorma * norma));//angulo que a sprite vai ter de rodar (em radianos)

            return true;
        }
        return false;
    }

    public double[] getScissorPosition() { return scissorPosition; }

    public double[] getScissorVel() { return scissorVel; }

    public float getScissorAng() { return scissorAng; }

    public boolean isTie() { return points == opponentPoints; }

    public void reset() {
        instance = null;
    }
}
