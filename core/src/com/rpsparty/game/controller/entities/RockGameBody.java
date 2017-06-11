package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rpsparty.game.model.entities.RockObjectGameModel;

public class RockGameBody {
    private int touchTime = 0;
    private float latentTime;
    private RockObjectGameModel model;

    /**
     * Constructor for the Body
     * @param model model of the body
     */
    public RockGameBody(RockObjectGameModel model) {
        latentTime = 2; //2 segundos visivel depois de destruido
        this.model = model;
    }

    /**
     * Function that returns touch time
     * @return Time the body has been touched for
     */
    public int getTouchTime() { return touchTime; }

    /**
     * Increments the touch time
     */
    public void setTouchTime() { touchTime++;}

    /**
     * Decreases the latent time
     * @param delta amount of time to be decreased
     */
    public void decreaseLatentTime(float delta) {
        latentTime -= delta;
    }

    /**
     * Returns the latent time
     * @return latent time
     */
    public float getLatentTime() {
        return latentTime;
    }

    /**
     * Returns object's model
     * @return model
     */
    public RockObjectGameModel getModel() { return model; }

}
