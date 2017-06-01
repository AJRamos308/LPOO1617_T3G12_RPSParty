package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rpsparty.game.model.entities.RockObjectGameModel;

public class RockGameBody {
    private int touchTime = 0;
    private float latentTime;
    private RockObjectGameModel model;

    public RockGameBody(RockObjectGameModel model) {
        latentTime = 2; //2 segundos visivel depois de destruido
        this.model = model;
    }

    public int getTouchTime() { return touchTime; }

    public void setTouchTime() { touchTime++;}

    public void decreaseLatentTime(float delta) {
        latentTime -= delta;
    }

    public float getLatentTime() {
        return latentTime;
    }

    public RockObjectGameModel getModel() { return model; }

}
