package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.rpsparty.game.model.entities.PaperHandModel;

/**
 * Created by bibib on 29/04/2017.
 */

public class PaperHandBody extends EntityBody {
    public PaperHandBody(World world, PaperHandModel model) {
        super(world, model);
        float density = 0.3f, friction = 0.4f, restitution = 0.1f;
        int width = 75, height = 75;
        PolygonShape shape =new PolygonShape();
        shape.setAsBox(width/2,height/2);

        createFixture(shape, width, height, density, friction, restitution, PAPER_BODY, (short) (ROCK_BODY | PAPER_BODY | SCISSOR_BODY));
    }
}
