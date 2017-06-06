package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.rpsparty.game.model.entities.ScissorHandModel;

public class ScissorHandBody extends EntityBody {
    public ScissorHandBody(World world, ScissorHandModel model) {
        super(world, model);
        float density = 0.7f, friction = 0.4f, restitution = 0.3f;
        int width = Gdx.graphics.getWidth()/9;
        PolygonShape shape =new PolygonShape();
        shape.setAsBox(width,width);

        createFixture(shape, width, width, density, friction, restitution, SCISSOR_BODY, (short) (ROCK_BODY | PAPER_BODY | SCISSOR_BODY));
    }
}
