package com.rpsparty.game.model;

import com.badlogic.gdx.Gdx;
import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.model.entities.PaperHandModel;
import com.rpsparty.game.model.entities.RockHandModel;
import com.rpsparty.game.model.entities.RockObjectGameModel;
import com.rpsparty.game.model.entities.ScissorHandModel;

import static com.rpsparty.game.model.entities.RockObjectGameModel.RockNumber.FIVE;
import static com.rpsparty.game.model.entities.RockObjectGameModel.RockNumber.FOUR;
import static com.rpsparty.game.model.entities.RockObjectGameModel.RockNumber.ONE;
import static com.rpsparty.game.model.entities.RockObjectGameModel.RockNumber.THREE;
import static com.rpsparty.game.model.entities.RockObjectGameModel.RockNumber.TWO;

/**
 * Created by bibib on 18/05/2017.
 */

public class RockGameModel {
    /**
     * The singleton instance of the MatchGame model
     */
    private static RockGameModel instance;

    private RockObjectGameModel rockOne;
    private RockObjectGameModel rockTwo;
    private RockObjectGameModel rockThree;
    private RockObjectGameModel rockFour;
    private RockObjectGameModel rockFive;

    public static RockGameModel getInstance() {
        if (instance == null)
            instance = new RockGameModel();
        return instance;
    }

    /**
     * class constructor
     */
    private RockGameModel(){

        rockOne = new RockObjectGameModel(Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, ONE);
        rockTwo = new RockObjectGameModel(6*Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, TWO);
        rockThree = new RockObjectGameModel(Gdx.graphics.getWidth()/16,5*Gdx.graphics.getHeight()/16,0, THREE);
        rockFour = new RockObjectGameModel(10*Gdx.graphics.getWidth()/16,6*Gdx.graphics.getHeight()/16,0, FOUR);
        rockFive = new RockObjectGameModel(2*Gdx.graphics.getWidth()/16,10*Gdx.graphics.getHeight()/16,0, FIVE);
    }

    /**
     *
     * @return rock one model
     */
    public RockObjectGameModel getRockOne() { return rockOne; }
    /**
     *
     * @return rock two model
     */
    public RockObjectGameModel getRockTwo() { return rockTwo; }
    /**
     *
     * @return rock three model
     */
    public RockObjectGameModel getRockThree() { return rockThree; }
    /**
     *
     * @return rock four model
     */
    public RockObjectGameModel getRockFour() { return rockFour; }
    /**
     *
     * @return rock five model
     */
    public RockObjectGameModel getRockFive() { return rockFive; }

    /**
     *
     * @param model
     * @return true if rock from model has a coin inside
     */
    public boolean hasCoin(RockObjectGameModel model) {
        return model.hasCoin();
    }

    /**
     *
     * @param model
     * @return true if rock from model is destroid
     */
    public boolean isDestroid (RockObjectGameModel model) {
        return model.isDestroied();
    }

    /**
     * destroy the rock from model
     * @param model
     */
    public void destroyRock(RockObjectGameModel model) {
        model.destroy();
    }

    /**
     * reset rock's model (after being destroid)
     * @param model
     */
    public void resetModel(RockObjectGameModel model) {
        if(model.getNumber().equals(RockObjectGameModel.RockNumber.ONE)) {
            rockOne = new RockObjectGameModel(Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, ONE);
            return;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            rockTwo = new RockObjectGameModel(6*Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, TWO);
            return;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            rockThree = new RockObjectGameModel(Gdx.graphics.getWidth()/16,5*Gdx.graphics.getHeight()/16,0, THREE);
            return;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            rockFour = new RockObjectGameModel(10*Gdx.graphics.getWidth()/16,6*Gdx.graphics.getHeight()/16,0, FOUR);
            return;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            rockFive = new RockObjectGameModel(2*Gdx.graphics.getWidth()/16,10*Gdx.graphics.getHeight()/16,0, FIVE);
            return;
        }
        System.out.println("MAL RESET!");
    }

    /**
     * reset class
     */
    public void reset() { instance = null; }

}
