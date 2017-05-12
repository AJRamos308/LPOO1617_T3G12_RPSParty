package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.rpsparty.game.model.entities.RockHandModel;

public class RockHandBody extends EntityBody {
    public RockHandBody(World world, RockHandModel model) {
        super(world, model);
        float density = 1.2f, friction = 0.8f, restitution = 0.2f;
        int width = 190, height = 190;
        PolygonShape shape =new PolygonShape();
        shape.setAsBox(width/2,height/2);

        createFixture(shape, width, height, density, friction, restitution, ROCK_BODY, (short) (ROCK_BODY | PAPER_BODY | SCISSOR_BODY));
    }
}

