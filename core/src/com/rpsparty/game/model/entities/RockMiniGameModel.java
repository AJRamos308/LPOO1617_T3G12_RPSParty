package com.rpsparty.game.model.entities;

public class RockMiniGameModel extends EntityModel {

    /**
     * construtor do modelo da pedra para o mini jogo
     *
     * @param x coordenada x
     * @param y coordenada y
     * @param rotation rotacao
     */
    public RockMiniGameModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.ROCKMINIGAME;
    }
}
