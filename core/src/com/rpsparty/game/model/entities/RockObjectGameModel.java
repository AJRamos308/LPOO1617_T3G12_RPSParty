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
        /*float x = 0, y = 0;
        if(number == RockNumber.ONE){
            x = Gdx.graphics.getWidth()/16;
            y = Gdx.graphics.getHeight()/16;
        }
        else if(number == RockNumber.TWO) {
            x = 3*Gdx.graphics.getWidth()/16;
            y = Gdx.graphics.getHeight()/16;
        }
        else if(number == RockNumber.THREE){
            x = 6*Gdx.graphics.getWidth()/16;
            y = Gdx.graphics.getHeight()/16;
        }
        else if(number == RockNumber.FOUR) {
            x = Gdx.graphics.getWidth()/16;
            y = 5*Gdx.graphics.getHeight()/16;
        }
        else if(number == RockNumber.FIVE) {
            x = 5*Gdx.graphics.getWidth()/16;
            y = 7*Gdx.graphics.getHeight()/16;
        }*/
        super(x, y, rotation);
        this.number = number;
        Random rand = new Random();
        int  n = rand.nextInt(2);
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

