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

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

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
    private EntityBody player1Entity = null;
    private EntityBody player2Entity =  null;


    private String myChoice;
    private String opponentChoice;

    private float lastUpdate =0.0f;
    private float lastX = 0.0f;
    private int shakeStage = 0; // 0 = X do telemovel esta a aumentar ; 1 = X do telemovel esta a diminuir
    private int updateTime = 1; // 1 = primeiro update de inicio de um shake ; 2 = segundo update de inicio de um shake
    private int flagDirection = 2; // 0 = shake aumenta no  sentido positivo de X ; 1 = shake aumenta no  sentido negativo de X ; 2 = ainda não se sabe o sentido
    private int count =3;//numero de shakes a dar

    private int currSet = 1;
    private int nSets = 3;
    private ArrayList<Integer> sets = new ArrayList<Integer>();

    private boolean sync = false;
    private boolean animation = false;
    private boolean collision = false;

    /**
     * Constructor for the Match Controller
     */
    private MatchController() {
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(this);
        myChoice = "";
        opponentChoice = "";
        currSet = 1;
    }

    /**
     * Returns a singleton instance of a game controller
     * @return the singleton instance
     */
    public static MatchController getInstance() {
        if (instance == null)
            instance = new MatchController();
        return instance;
    }

    /**
     * Update the bodies from the world
     * @param delta update accordingly to the provided delta
     */
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

    /**
     * Get the world object
     * @return world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Verify if the animation is being played
     * @return animation
     */
    public boolean isAnimation() {
        return animation;
    }

    /**
     * If the player chooses rock
     */
    public void chooseRock() {
        player1Entity = new RockHandBody(world, (RockHandModel)MatchModel.getInstance().setMyChoice("rock"));
        player1Entity.setLinearVelocity(0f,400f);
        //ConnectionSockets.getInstance().sendMessage("rock"+"\n");
    }

    /**
     * If the player chooses paper
     */
    public void choosePaper() {
        player1Entity = new PaperHandBody(world, (PaperHandModel)MatchModel.getInstance().setMyChoice("paper"));
        player1Entity.setLinearVelocity(0f,400f);
        //ConnectionSockets.getInstance().sendMessage("paper"+"\n");
    }

    /**
     * If the player chooses scissors
     */
    public void chooseScissor() {
        player1Entity = new ScissorHandBody(world, (ScissorHandModel)MatchModel.getInstance().setMyChoice("scissor"));
        player1Entity.setLinearVelocity(0f,400f);
       // ConnectionSockets.getInstance().sendMessage("scissor"+"\n");
    }

    /**
     * If the opponent chooses rock
     */
    public void opponentChooseRock() {
        player2Entity = new RockHandBody(world, (RockHandModel)MatchModel.getInstance().setOpponentChoice("rock"));
        player2Entity.setLinearVelocity(0f,-400f);
    }

    /**
     * If the opponent chooses paper
     */
    public void opponentChoosePaper() {
        player2Entity = new PaperHandBody(world, (PaperHandModel)MatchModel.getInstance().setOpponentChoice("paper"));
        player2Entity.setLinearVelocity(0f,-400f);
    }

    /**
     * If the opponent chooses scissors
     */
    public void opponentChooseScissor() {
        player2Entity = new ScissorHandBody(world, (ScissorHandModel)MatchModel.getInstance().setOpponentChoice("scissor"));
        player2Entity.setLinearVelocity(0f,-400f);
    }

    /**
     * A contact between two objects was detected
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

    /**
     * Ends contact
     * @param contact the detected collision
     */
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

    /**
     * Get my option
     * @return choice
     */
    public String getMyChoice() {
        return myChoice;
    }

    /**
     * Get opponent option
     * @return opponent choice
     */
    public String getOpponentChoice() {
        return opponentChoice;
    }

    /**
     * Save my choice
     * @param choice choice
     */
    public void setMyChoice(String choice) {
        myChoice = choice;
    }

    /**
     * Sync; Waits for opponent to choose
     * @return boolean
     */
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

    /**
     * Creates a thread that will read the opponent choice
     */
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

    /**
     * Detect one shake
     * @return boolean indicating success
     */
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

    /**
     * Verify the direction of the shake
     * @return true if the direction was already found, false if not yet
     */
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

    /**
     * Get the last position x detected
     * @return last position
     */
    public float updateLastX() {
        float currX = Gdx.input.getAccelerometerX();
        if(Math.abs(lastX - currX) > 3) {
            return currX;
        }
        return lastX;
    }

    /**
     * Count one shake
     * @param delta timings
     */
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

    /**
     * Verify if both players already chose between rock, paper or scissors
     * @return choices
     */
    public boolean arePlayersReady() { return (myChoice != "" && opponentChoice != ""); }

    /**
     * Checks if it was a victory
     * @return boolean if it is victory
     */
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

    /**
     * Conclude which one won this set
     * @return boolean indicating if the user has already shook 3 times
     */
    public boolean finalResult() {
        if(count == 0) {//ja se agitou 3 vezes
            if(!animation) {
                if(isVictory()) {
                    System.out.println("ganhaste esta partida!");
                    sets.add(1);
                    currSet++;
                    System.out.println("incrementou currSet");
                } else {

                    if(!isTie()) { //senao e empate
                        System.out.println("perdeste esta partida...");
                        sets.add(0);
                        currSet++;
                        System.out.println("incrementou currSet");
                    }
                }
            }
            animation = true;

            synchronizeThreads();

            return true;
        }
        return false;
    }

    /**
     * Sync players communication
     */
    public void synchronizeThreads() {
        if(!sync) {//sincronizar os shakes dos dois jogadores
            String s;
            ConnectionSockets.getInstance().sendMessage("lixo"+"\n");
            s = ConnectionSockets.getInstance().receiveMessage();
            sync = true;
        }
    }

    /**
     * Verification if it is a tie
     * @return return true if is a tie
     */
    public boolean isTie() { return myChoice.equals(opponentChoice); }

    /**
     * Reset almost all class attributes
     * @param delta difference in times
     * @return true if the attributes were already reset
     */
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

            lastUpdate = 0.0f;
            lastX = 0.0f;
            shakeStage = 0;
            updateTime = 1;
            flagDirection = 2;
            count = 3;
            sync = false;
            animation = false;
            collision = false;
            MatchModel.getInstance().reset();
            return true;
        }
        return false;
    }

    /**
     * Reset players choices
     */
    public void resetChoices() {
        myChoice = "";
        opponentChoice = "";
        sync = false;
    }

    /**
     * Reset the class attributes that were not reseted yet
     */
    public void resetAll() { instance = null; }

    /**
     *  Gets rounds scores
     * @return the sets that players already played
     */
    public ArrayList<Integer> getSets() {
        return sets;
    }

    /**
     * Checks if collision
     * @return true if bodies collied
     */
    public boolean isCollision() { return collision; }

    /**
     * Update the current set's number
     */
    public void increaseSet() { currSet++; }

    /**
     * Get the result from the previous set
     * @return last result
     */
    public Integer getLastResult() { return sets.get(sets.size()-1);}

    /**
     * Rmpty the array of sets played
     */
    public void clearSets() {
        sets.clear(); }

    /**
     *
     * @return true if the game is over
     */
    public boolean isEndOfGame() {return (sets.size() == nSets); }

    /**
     * return the total number of sets that will be played in this game
     * @param bestOf best of how many
     */
    public void setNSets(int bestOf) { nSets = bestOf; }
}
