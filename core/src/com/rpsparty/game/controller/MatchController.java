package com.rpsparty.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import com.rpsparty.game.controller.entities.PaperHandBody;
import com.rpsparty.game.controller.entities.RockHandBody;
import com.rpsparty.game.controller.entities.ScissorHandBody;
import com.rpsparty.game.model.MatchModel;

import java.util.ArrayList;

public class MatchController implements ContactListener {
    /**
     * The singleton instance of this controller
     */
    private static MatchController instance;

    public static final int ARENA_WIDTH = 800;
    public static final int ARENA_HEIGHT = 480;

    private final World world;
    private final RockHandBody rock;
    private final PaperHandBody paper;
    private final ScissorHandBody scissor;

    private String myChoice;
    private String opponentChoice;

    private float lastUpdate =0.0f;
    private float lastX = 0.0f;
    private int shakeStage = 0; // 0 = X do telemovel esta a aumentar ; 1 = X do telemovel esta a diminuir
    private int updateTime = 1; // 1 = primeiro update de inicio de um shake ; 2 = segundo update de inicio de um shake
    private int flagDirection = 2; // 0 = shake aumenta no  sentido positivo de X ; 1 = shake aumenta no  sentido negativo de X ; 2 = ainda não se sabe o sentido
    private int count = 3;//numero de shakes a dar

    private int currSet = 0;
    private ArrayList sets;

    private MatchController() {
        world = new World(new Vector2(0, 0), true);

        rock = new RockHandBody(world, MatchModel.getInstance().getRock());
        paper = new PaperHandBody(world, MatchModel.getInstance().getPaper());
        scissor = new ScissorHandBody(world, MatchModel.getInstance().getScissor());

        world.setContactListener(this);
        myChoice = "";
        opponentChoice = "";
    }

    /**
     * Returns a singleton instance of a game controller
     *
     * @return the singleton instance
     */
    public static MatchController getInstance() {
        if (instance == null)
            instance = new MatchController();
        return instance;
    }

    public void update(float delta) {

    }

    public World getWorld() {
        return world;
    }

    public void chooseRock() {
        rock.setTransform(Gdx.graphics.getWidth()/12, 5*Gdx.graphics.getHeight()/8,0);
        MatchModel.getInstance().getRock().setPosition(Gdx.graphics.getWidth()/12, 5*Gdx.graphics.getHeight()/8);
        ConnectionSockets.getInstance().sendMessage("rock");
    }

    public void choosePaper() {
        paper.setTransform(Gdx.graphics.getWidth()/12, 5*Gdx.graphics.getHeight()/8,0);
        MatchModel.getInstance().getPaper().setPosition(Gdx.graphics.getWidth()/12, 5*Gdx.graphics.getHeight()/8);
        ConnectionSockets.getInstance().sendMessage("paper");
    }

    public void chooseScissor() {
        scissor.setTransform(Gdx.graphics.getWidth()/12, 5*Gdx.graphics.getHeight()/8,0);
        MatchModel.getInstance().getScissor().setPosition(Gdx.graphics.getWidth()/12, 5*Gdx.graphics.getHeight()/8);
        ConnectionSockets.getInstance().sendMessage("scissor");
    }

    /**
     * A contact between two objects was detected
     *
     * @param contact the detected contact
     */
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        //TODO: adicionar animacao ao choque de elementos

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public String getMyChoice() {
        return myChoice;
    }

    public String getOpponentChoice() {
        return opponentChoice;
    }

    public void setMyChoice(String choice) {
        myChoice = choice;
    }

    public void setOpponentChoice(String choice) {
        opponentChoice = choice;
    }

    public boolean waitForOpponent() {
        System.out.println("\nESTA NO WAIT FOR OPPONENT|\n");
        opponentChoice = ConnectionSockets.getInstance().receiveMessage();
        System.out.println("consegui ler?...");
        System.out.println(opponentChoice);
        if(opponentChoice != "") {
            return true;
        }
        return false;
    }

    public void createReadThread() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                while(!waitForOpponent());
                while(myChoice == "");

            }
        }).start();
    }

    public boolean shake() {
        float currX = Gdx.input.getAccelerometerX();
        System.out.println("\n\nX: "+Gdx.input.getAccelerometerX()+"\n\n");
        if(shakeStage == 0) {//primeira parte do shake
            System.out.println("no shake stage 0");
            if(((flagDirection == 0) && (currX < lastX)) || ((flagDirection == 1) && (currX > lastX))) {//um shake
                System.out.println("transicao da direcao");
                lastX = currX;
                shakeStage = 1;
                return true;
            }
        } else {
            System.out.println("no shake stage 1");
            if(((flagDirection == 0) && (currX > lastX)) || ((flagDirection == 1) && (currX < lastX))) {
                lastX = updateLastX();
                shakeStage = 0;
                return false;
            }
        }
        lastX = updateLastX();
        return false;
    }

    public boolean verifyDirectionShake() {
        if(updateTime > 2) {
            return true;
        }
        if(flagDirection == 2) {
            if(updateTime == 1) {//primeira vez no render
                System.out.println("primeira vez a verificar a direcao");
                lastX = Gdx.input.getAccelerometerX();
                updateTime++;
            } else {//ja da para concluir a direcao do shake
                System.out.println("vamos concluir a direcao...");
                if(Gdx.input.getAccelerometerX() > lastX) {
                    System.out.println("aumenta no sentido positivo");
                    flagDirection = 0;
                } else {
                    System.out.println("aumenta no sentido negativo");
                    flagDirection = 1;
                }
                updateTime++;
                return true;
            }
        }
        return false;
    }

    public float updateLastX() {
        float currX = Gdx.input.getAccelerometerX();
        if(Math.abs(lastX - currX) > 3) {
            return currX;
        }
        return lastX;
    }

    public void shakeUpdate(float delta) {
        lastUpdate += delta;
        if(lastUpdate > 0.25f) {
            if(Math.abs(lastX - Gdx.input.getAccelerometerX()) > 3) {
                if (verifyDirectionShake()) {
                    if (shake()) {
                        System.out.println("SHAKE!");
                        Gdx.input.vibrate(300);
                        count--;
                    }
                }
            }
            lastUpdate = 0;
        }
    }

    public boolean isVictory() {
        boolean result = false;
        if(myChoice == "paper") {
            result = opponentChoice == "rock" ? true : false;
        } else if(myChoice == "rock") {
            result = opponentChoice == "scissor" ? true : false;
        } else if(myChoice == "scissor") {
            result = opponentChoice == "paper" ? true : false;
        }
        return result;
    }

    public void finalResult() {
        if(count == 0) {
            if(isVictory()) {
                System.out.println("ganhaste esta partida!");
            } else {
                System.out.println("perdeste esta partida...");
            }
        }
    }


}
