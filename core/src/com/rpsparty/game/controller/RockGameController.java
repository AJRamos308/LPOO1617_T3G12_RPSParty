package com.rpsparty.game.controller;


import com.rpsparty.game.controller.entities.RockGameBody;
import com.rpsparty.game.model.RockGameModel;
import com.rpsparty.game.model.entities.RockObjectGameModel;

import java.util.ArrayList;

/**
 * Created by bibib on 18/05/2017.
 */

public class RockGameController{
    /**
     * The singleton instance of this controller
     */
    private static RockGameController instance;
    private RockGameBody rockBodyOne;
    private RockGameBody rockBodyTwo;
    private RockGameBody rockBodyThree;
    private RockGameBody rockBodyFour;
    private RockGameBody rockBodyFive;
    private final ArrayList<RockGameBody> rocks;
    private boolean update1 = false, update2 = false, update3 = false, update4 = false, update5 = false;
    private int points = 0;
    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    private RockGameController() {

        rockBodyOne = new RockGameBody(RockGameModel.getInstance().getRockOne());
        rockBodyTwo = new RockGameBody(RockGameModel.getInstance().getRockTwo());
        rockBodyThree = new RockGameBody(RockGameModel.getInstance().getRockThree());
        rockBodyFour = new RockGameBody(RockGameModel.getInstance().getRockFour());
        rockBodyFive = new RockGameBody(RockGameModel.getInstance().getRockFive());
        rocks = new ArrayList<RockGameBody>() {{add(rockBodyOne); add(rockBodyTwo); add(rockBodyThree); add(rockBodyFour); add(rockBodyFive);}};
    }

    public static RockGameController getInstance() {
        if (instance == null)
            instance = new RockGameController();
        return instance;
    }

    public void update(float delta) {
        for(int i = 0; i < rocks.size(); i++) {
            if(RockGameModel.getInstance().isDestroid(rocks.get(i).getModel())) {//se pedra destruida
                if(rocks.get(i).getLatentTime() <= 0) {//pedra desaparece; é criada uma nova pedra
                    rocks.add(i+1, createNewRock(rocks.get(i).getModel()));
                    rocks.remove(i);
                } else {
                    rocks.get(i).decreaseLatentTime(delta);
                    System.out.println("tempo latente: "+rocks.get(i).getLatentTime());
                }
            }
        }
    }

    public RockGameBody createNewRock(RockObjectGameModel model) {
        RockGameModel.getInstance().resetModel(model);
        if(model.getNumber() == RockObjectGameModel.RockNumber.ONE) {
            update1 = true;
            rockBodyOne = new RockGameBody(RockGameModel.getInstance().getRockOne());
            return rockBodyOne;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            update2 = true;
            rockBodyTwo = new RockGameBody(RockGameModel.getInstance().getRockTwo());
            return rockBodyTwo;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            update3 = true;
            rockBodyThree = new RockGameBody(RockGameModel.getInstance().getRockThree());
            return rockBodyThree;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            update4 = true;
            rockBodyFour = new RockGameBody(RockGameModel.getInstance().getRockFour());
            return rockBodyFour;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            update5 = true;
            rockBodyFive = new RockGameBody(RockGameModel.getInstance().getRockFive());
            return rockBodyFive;
        }
        System.out.println("MAL!");
        return null;

    }

    public void touchRockOne() {
        if(!RockGameModel.getInstance().isDestroid(rockBodyOne.getModel())) {
            rockBodyOne.setTouchTime();
            if (rockBodyOne.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock(rockBodyOne.getModel());
                if(RockGameModel.getInstance().hasCoin(rockBodyOne.getModel())) {
                    points++;
                }
            }
        }
    }
    public void touchRockTwo() {
        if(!RockGameModel.getInstance().isDestroid(rockBodyTwo.getModel())) {
            rockBodyTwo.setTouchTime();
            if (rockBodyTwo.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock(rockBodyTwo.getModel());
                if(RockGameModel.getInstance().hasCoin(rockBodyTwo.getModel())) {
                    points++;
                }
            }
        }
    }
    public void touchRockThree() {
        if(!RockGameModel.getInstance().isDestroid(rockBodyThree.getModel())) {
            rockBodyThree.setTouchTime();
            if (rockBodyThree.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock(rockBodyThree.getModel());
                if(RockGameModel.getInstance().hasCoin(rockBodyThree.getModel())) {
                    points++;
                }
            }
        }
    }
    public void touchRockFour() {
        if(!RockGameModel.getInstance().isDestroid(rockBodyFour.getModel())) {
            rockBodyFour.setTouchTime();
            if (rockBodyFour.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock(rockBodyFour.getModel());
                if(RockGameModel.getInstance().hasCoin(rockBodyFour.getModel())) {
                    points++;
                }
            }
        }
    }
    public void touchRockFive() {
        if(!RockGameModel.getInstance().isDestroid(rockBodyFive.getModel())) {
            rockBodyFive.setTouchTime();
            if (rockBodyFive.getTouchTime() >= 20) {
                RockGameModel.getInstance().destroyRock(rockBodyFive.getModel());
                if(RockGameModel.getInstance().hasCoin(rockBodyFive.getModel())) {
                    points++;
                }
            }
        }
    }

    public boolean isButtonToUpdate(RockObjectGameModel model) {
        boolean result = false;
        if(model.getNumber() == RockObjectGameModel.RockNumber.ONE) {
            result = update1;
            update1 = false;
            return result;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.TWO) {
            result = update2;
            update2 = false;
            return result;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.THREE) {
            result = update3;
            update3 = false;
            return result;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FOUR) {
            result = update4;
            update4 = false;
            return result;
        } else if(model.getNumber() == RockObjectGameModel.RockNumber.FIVE) {
            result = update5;
            update5 = false;
            return result;
        }
        return result;
    }

    public int getPoints() { return points; }

}
