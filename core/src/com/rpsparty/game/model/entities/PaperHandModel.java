package com.rpsparty.game.model.entities;

public class PaperHandModel extends EntityModel {

    /**
     * construtor do modelo para PaperHand
     *
     * @param x coordenada x
     * @param y coordenada y
     * @param rotation rotacao
     */
    public PaperHandModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.PAPERHAND;
    }
}
