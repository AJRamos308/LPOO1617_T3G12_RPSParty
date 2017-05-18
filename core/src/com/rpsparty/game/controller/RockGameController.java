package com.rpsparty.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.controller.entities.RockGameBody;
import com.rpsparty.game.model.RockGameModel;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.rpsparty.game.model.entities.RockObjectGameModel;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by bibib on 18/05/2017.
 */

public class RockGameController{
    /**
     * The singleton instance of this controller
     */
    private static RockGameController instance;
    private final World world;
    private RockGameBody rockBodyOne;
    private RockGameBody rockBodyTwo;
    private RockGameBody rockBodyThree;
    private RockGameBody rockBodyFour;
    private RockGameBody rockBodyFive;
    private final ArrayList<RockGameBody> rocks;

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    private RockGameController() {
        world = new World(new Vector2(0, 0), true);

        rockBodyOne = new RockGameBody(world, RockGameModel.getInstance().getRockOne());
        rockBodyTwo = new RockGameBody(world, RockGameModel.getInstance().getRockTwo());
        rockBodyThree = new RockGameBody(world, RockGameModel.getInstance().getRockThree());
        rockBodyFour = new RockGameBody(world, RockGameModel.getInstance().getRockFour());
        rockBodyFive = new RockGameBody(world, RockGameModel.getInstance().getRockFive());
        rocks = new ArrayList<RockGameBody>() {{add(rockBodyOne); add(rockBodyTwo); add(rockBodyThree); add(rockBodyFour); add(rockBodyFive);}};
    }

    public static RockGameController getInstance() {
        if (instance == null)
            instance = new RockGameController();
        return instance;
    }

    public void update(float delta) {
        for(int i = 0; i < 5; i++) {
            if(RockGameModel.getInstance().isDestroid((RockObjectGameModel)rocks.get(i).getUserData())) {//se pedra destruida
                if(rocks.get(i).getLatentTime() <= 0) {//pedra desaparece; é criada uma nova pedra
                    createNewRock((RockObjectGameModel)rocks.get(i).getUserData());
                } else {
                    rocks.get(i).decreaseLatentTime(delta);
                    System.out.println("tempo latente: "+rocks.get(i).getLatentTime());
                }
            }
        }
    }

    public void createNewRock(RockObjectGameModel model) {
        RockGameModel.getInstance().resetModel(model);
        if(model.getNumber() == RockObjectGameModel.RockNumber.ONE) {
            rockBodyOne = new RockGameBody(world, RockGameModel.getInstance().getRockOne());
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            rockBodyTwo = new RockGameBody(world, RockGameModel.getInstance().getRockTwo());
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            rockBodyThree = new RockGameBody(world, RockGameModel.getInstance().getRockThree());
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            rockBodyFour = new RockGameBody(world, RockGameModel.getInstance().getRockFour());
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            rockBodyFive = new RockGameBody(world, RockGameModel.getInstance().getRockFive());
        }
    }
    public World getWorld() {
        return world;
    }

    public void touchRockOne() {
        if(!RockGameModel.getInstance().isDestroid((RockObjectGameModel)rockBodyOne.getUserData())) {
            rockBodyOne.setTouchTime();
            if (rockBodyOne.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock((RockObjectGameModel)rockBodyOne.getUserData());
            }
        }
    }
    public void touchRockTwo() {
        if(!RockGameModel.getInstance().isDestroid((RockObjectGameModel)rockBodyTwo.getUserData())) {
            rockBodyTwo.setTouchTime();
            if (rockBodyTwo.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock((RockObjectGameModel)rockBodyTwo.getUserData());
            }
        }
    }
    public void touchRockThree() {
        if(!RockGameModel.getInstance().isDestroid((RockObjectGameModel)rockBodyThree.getUserData())) {
            rockBodyThree.setTouchTime();
            if (rockBodyThree.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock((RockObjectGameModel)rockBodyThree.getUserData());
            }
        }
    }
    public void touchRockFour() {
        if(!RockGameModel.getInstance().isDestroid((RockObjectGameModel)rockBodyFour.getUserData())) {
            rockBodyFour.setTouchTime();
            if (rockBodyFour.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock((RockObjectGameModel)rockBodyFour.getUserData());
            }
        }
    }
    public void touchRockFive() {
        if(!RockGameModel.getInstance().isDestroid((RockObjectGameModel)rockBodyFive.getUserData())) {
            rockBodyFive.setTouchTime();
            if (rockBodyFive.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock((RockObjectGameModel)rockBodyFive.getUserData());
            }
        }
    }




}
