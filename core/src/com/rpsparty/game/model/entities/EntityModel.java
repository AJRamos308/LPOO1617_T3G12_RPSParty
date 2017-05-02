package com.rpsparty.game.model.entities;

public abstract class EntityModel {
    public enum ModelType {ROCKHAND, PAPERHAND, SCISSORHAND, ROCKMINIGAME, PAPERMINIGAME, SCISSORMINIGAME};
    /**
     * coordenada x em metros
     */
    private float x;

    /**
     * coordenada y em metros
     */
    private float y;

    /**
     * rotacao em radianos
     */
    private float rotation;

    /**
     * flag para saber se a entity tem de ser removida do Model
     */
    private boolean flaggedForRemoval = false;

    /**
     * construtor do modelo de um entidade com uma posicao (x, y) e rotacao inicial
     *
     * @param x coordenada x em metros
     * @param y coordenada y em metros
     * @param rotation rotacao em radianos
     */
    EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * @return coordenada x da entity
     */
    public float getX() {
        return x;
    }

    /**
     * @return coordenada y da entity
     */
    public float getY() {
        return y;
    }

    /**
     * @return rotacao rotation da entity
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * altera a posicao da entity
     *
     * @param x coordenada x da nova posicao
     * @param y coodenada y da nova posicao
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * altera a rotacao da entity
     *
     * @param rotation nova rotacao
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * retorna um booleano que estara a true se a entity tiver de ser removida de Model
     *
     * @return
     */
    public boolean isFlaggedToBeRemoved() {
        return flaggedForRemoval;
    }

    /**
     * altera a flag flaggedForRemoval
     */
    public void setFlaggedForRemoval(boolean flaggedForRemoval) {
        this.flaggedForRemoval = flaggedForRemoval;
    }

    public abstract ModelType getType();
}
