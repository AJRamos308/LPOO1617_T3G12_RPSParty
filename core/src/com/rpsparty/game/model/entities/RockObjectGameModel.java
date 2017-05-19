package com.rpsparty.game.model.entities;


import java.util.Random;

public class RockObjectGameModel extends EntityModel {
    public enum RockNumber {ONE, TWO, THREE, FOUR, FIVE}
    private RockNumber number;
    private boolean destroied = false;
    private boolean coin;

    /**
     * construtor do modelo para PaperHand
     *
     * @param rotation rotacao
     */
    public RockObjectGameModel(float x, float y, float rotation, RockNumber number) {
        super(x, y, rotation);
        this.number = number;
        Random rand = new Random();
        int  n = rand.nextInt(3);
        coin = n == 0 ? true : false;

    }

    public RockNumber getNumber() { return number; }


    @Override
    public ModelType getType() {
        if(number == RockNumber.ONE)
            return ModelType.ROCKGAMEONE;
        if(number == RockNumber.TWO)
            return ModelType.ROCKGAMETWO;
        if(number == RockNumber.THREE)
            return ModelType.ROCKGAMETHREE;
        if(number == RockNumber.FOUR)
            return ModelType.ROCKGAMEFOUR;
        if(number == RockNumber.FIVE)
            return ModelType.ROCKGAMEFIVE;
        return null;
    }

    public void destroy() {
        destroied = true;
    }
    public boolean isDestroied() {
        return destroied;
    }

    public boolean hasCoin() { return coin; }
}

