package com.rpsparty.game.model.entities;

public class PaperMiniGameModel extends EntityModel {

    /**
     * construtor do modelo para o Papel no mini jogo
     *
     * @param x coordenada x de RockHand
     * @param y coordenada y de RockHand
     * @param rotation rotacao de RockHand
     */
    public PaperMiniGameModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.PAPERMINIGAME;
    }
}
