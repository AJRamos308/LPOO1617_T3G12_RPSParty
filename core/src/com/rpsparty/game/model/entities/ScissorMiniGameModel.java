package com.rpsparty.game.model.entities;

public class ScissorMiniGameModel extends EntityModel {

    /**
     * construtor do modelo para a tesoura no mini jogo
     *
     * @param x coordenada x
     * @param y coordenada y
     * @param rotation rotacao
     */
    public ScissorMiniGameModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.SCISSORMINIGAME;
    }
}
