package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rpsparty.game.model.entities.RockObjectGameModel;



/**
 * Created by bibib on 18/05/2017.
 */

public class RockGameBody extends EntityBody {
    private int touchTime = 0;
    private float latentTime;


    public RockGameBody(World world, RockObjectGameModel model) {
        super(world, model);
        float density = 1.2f, friction = 0.8f, restitution = 0.2f;
        int width = 190, height = 190;
        PolygonShape shape =new PolygonShape();
        shape.setAsBox(width/2,height/2);

        createFixture(shape, width, height, density, friction, restitution, ROCK_BODY, (short) (ROCK_BODY | PAPER_BODY | SCISSOR_BODY));
        latentTime = 2; //2 segundos visivel depois de destruido

    }

    public int getTouchTime() { return touchTime; }

    public void setTouchTime() { touchTime++;}

    public void decreaseLatentTime(float delta) {
        latentTime -= delta;
    }

    public float getLatentTime() {
        return latentTime;
    }

}
