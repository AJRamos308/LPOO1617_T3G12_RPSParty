package com.rpsparty.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;
import com.rpsparty.game.controller.entities.EntityBody;
import com.rpsparty.game.controller.entities.PaperHandBody;
import com.rpsparty.game.controller.entities.RockHandBody;
import com.rpsparty.game.controller.entities.ScissorHandBody;
import com.rpsparty.game.model.MatchModel;
import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.model.entities.PaperHandModel;
import com.rpsparty.game.model.entities.RockHandModel;
import com.rpsparty.game.model.entities.ScissorHandModel;

import java.util.ArrayList;

public class MatchController implements ContactListener {
    /**
     * The singleton instance of this controller
     */
    private static MatchController instance;

    public static final int ARENA_WIDTH = 800;
    public static final int ARENA_HEIGHT = 480;

    private final World world;
    /**
     * Accumulator used to calculate the simulation step.
     */
    private float accumulator;
   /* private final RockHandBody rock;
    private final PaperHandBody paper;
    private final ScissorHandBody scissor;
   */
    private EntityBody player1Entity = null;
    private EntityBody player2Entity =  null;


    private String myChoice;
    private String opponentChoice;

    private float lastUpdate =0.0f;
    private float lastX = 0.0f;
    private int shakeStage = 0; // 0 = X do telemovel esta a aumentar ; 1 = X do telemovel esta a diminuir
    private int updateTime = 1; // 1 = primeiro update de inicio de um shake ; 2 = segundo update de inicio de um shake
    private int flagDirection = 2; // 0 = shake aumenta no  sentido positivo de X ; 1 = shake aumenta no  sentido negativo de X ; 2 = ainda não se sabe o sentido
    private int count = 0;//numero de shakes a dar

    private int currSet;
    private ArrayList<Integer> sets = new ArrayList<Integer>();

    private boolean sync = false;
    private boolean animation = false;
    private boolean collision = false;

    private MatchController() {
        world = new World(new Vector2(0, 0), true);

       /* rock = new RockHandBody(world, MatchModel.getInstance().getRock());
        paper = new PaperHandBody(world, MatchModel.getInstance().getPaper());
        scissor = new ScissorHandBody(world, MatchModel.getInstance().getScissor());
        */
        world.setContactListener(this);
        myChoice = "";
        opponentChoice = "";
        currSet = 1;
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

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }
    }

    public World getWorld() {
        return world;
    }

    public boolean isAnimation() {
        return animation;
    }

    public void chooseRock() {
        player1Entity = new RockHandBody(world, (RockHandModel)MatchModel.getInstance().setMyChoice("rock"));
        player1Entity.setLinearVelocity(0f,200f);
        ConnectionSockets.getInstance().sendMessage("rock");
    }

    public void choosePaper() {
        player1Entity = new PaperHandBody(world, (PaperHandModel)MatchModel.getInstance().setMyChoice("paper"));
        player1Entity.setLinearVelocity(0f,200f);
        ConnectionSockets.getInstance().sendMessage("paper");
    }

    public void chooseScissor() {
        player1Entity = new ScissorHandBody(world, (ScissorHandModel)MatchModel.getInstance().setMyChoice("scissor"));
        player1Entity.setLinearVelocity(0f,200f);
        ConnectionSockets.getInstance().sendMessage("scissor");
    }

    public void opponentChooseRock() {
        player2Entity = new RockHandBody(world, (RockHandModel)MatchModel.getInstance().setOpponentChoice("rock"));
        player2Entity.setLinearVelocity(0f,-200f);
    }

    public void opponentChoosePaper() {
        player2Entity = new PaperHandBody(world, (PaperHandModel)MatchModel.getInstance().setOpponentChoice("paper"));
        player2Entity.setLinearVelocity(0f,-200f);
    }

    public void opponentChooseScissor() {
        player2Entity = new ScissorHandBody(world, (ScissorHandModel)MatchModel.getInstance().setOpponentChoice("scissor"));
        player2Entity.setLinearVelocity(0f,-200f);
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
        System.out.println("CHOQUE!");
        collision = true;
    }

    @Override
    public void endContact(Contact contact) {
        //resetMatch();
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
                if(opponentChoice.equals("rock"))
                    opponentChooseRock();
                else if(opponentChoice.equals("paper"))
                    opponentChoosePaper();
                else if(opponentChoice.equals("scissor"))
                    opponentChooseScissor();
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
        if (lastUpdate > 0.25f) {
            if (Math.abs(lastX - Gdx.input.getAccelerometerX()) > 3) {
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
            result = opponentChoice.equals("rock") ? true : false;
        } else if(myChoice == "rock") {
            result = opponentChoice.equals("scissor") ? true : false;
        } else if(myChoice == "scissor") {
            result = opponentChoice.equals("paper") ? true : false;
        }

        return result;
    }

    public boolean finalResult() {
        if(count == 0) {
            animation = true;
            if(isVictory()) {
                System.out.println("ganhaste esta partida!");
                sets.add(1);
            } else {
                System.out.println("perdeste esta partida...");
                if(!myChoice.equals(opponentChoice)) //senao e empate
                    sets.add(0);
            }
            currSet++;
            if(!sync) {
                String s;
                ConnectionSockets.getInstance().sendMessage("lixo\n");
                s = ConnectionSockets.getInstance().receiveMessage();
                sync = true;
            }
            return true;
        }
        return false;
    }

    public boolean resetMatch(float delta) {
        lastUpdate += delta;
        if(lastUpdate >= 3) {
            Array<Body> bodies = new Array<Body>();
            world.getBodies(bodies);
            for (Body body : bodies) {
                world.destroyBody(body);
            }
            player1Entity = null;
            player2Entity = null;

            myChoice = "";
            opponentChoice = "";

            lastUpdate = 0.0f;
            lastX = 0.0f;
            shakeStage = 0;
            updateTime = 1;
            flagDirection = 2;
            count = 0;
            sync = false;
            animation = false;
            collision = false;
            MatchModel.getInstance().reset();
            return true;
        }
        return false;
    }

    public ArrayList<Integer> getSets() {
        return sets;
    }

    public boolean isCollision() { return collision; }

}
