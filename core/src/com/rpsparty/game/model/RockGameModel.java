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

    private RockGameModel(){

        rockOne = new RockObjectGameModel(Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, ONE);
        rockTwo = new RockObjectGameModel(3*Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, TWO);
        rockThree = new RockObjectGameModel(6*Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, THREE);
        rockFour = new RockObjectGameModel(Gdx.graphics.getWidth()/16,5*Gdx.graphics.getHeight()/16,0, FOUR);
        rockFive = new RockObjectGameModel(5*Gdx.graphics.getWidth()/16,7*Gdx.graphics.getHeight()/16,0, FIVE);
    }

    public RockObjectGameModel getRockOne() { return rockOne; }
    public RockObjectGameModel getRockTwo() { return rockTwo; }
    public RockObjectGameModel getRockThree() { return rockThree; }
    public RockObjectGameModel getRockFour() { return rockFour; }
    public RockObjectGameModel getRockFive() { return rockFive; }

    public boolean hasCoin(RockObjectGameModel model) {
        if(model.getNumber() == RockObjectGameModel.RockNumber.ONE) {
            return rockOne.hasCoin();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            return rockTwo.hasCoin();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            return rockThree.hasCoin();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            return rockFour.hasCoin();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            return rockFive.hasCoin();
        }
        return false;
    }

    public boolean isDestroid (RockObjectGameModel model) {
        if(model.getNumber() == RockObjectGameModel.RockNumber.ONE) {
            return rockOne.isDestroied();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            return rockTwo.isDestroied();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            return rockThree.isDestroied();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            return rockFour.isDestroied();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            return rockFive.isDestroied();
        }
        return false;
    }

    public void destroyRock(RockObjectGameModel model) {
        if(model.getNumber() == RockObjectGameModel.RockNumber.ONE) {
            rockOne.destroy();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            rockTwo.destroy();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            rockThree.destroy();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            rockFour.destroy();
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            rockFive.destroy();
        }
    }

    public void resetModel(RockObjectGameModel model) {
        if(model.getNumber() == RockObjectGameModel.RockNumber.ONE) {
            rockOne = new RockObjectGameModel(Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, ONE);
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            rockTwo = new RockObjectGameModel(3*Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, TWO);
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            rockThree = new RockObjectGameModel(6*Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/16,0, THREE);
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            rockFour = new RockObjectGameModel(Gdx.graphics.getWidth()/16,5*Gdx.graphics.getHeight()/16,0, FOUR);
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            rockFive = new RockObjectGameModel(5*Gdx.graphics.getWidth()/16,7*Gdx.graphics.getHeight()/16,0, FIVE);
        }
        System.out.println("model not found...");
    }

}
