package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.rpsparty.game.model.entities.PaperHandModel;

public class PaperHandBody extends EntityBody {
    /**
     * Constructor for the Body
     * @param world world in which the body resides in
     * @param model model of the body
     */
    public PaperHandBody(World world, PaperHandModel model) {
        super(world, model);
        float density = 0.3f, friction = 0.4f, restitution = 0.1f;
        int width = Gdx.graphics.getWidth()/9;
        PolygonShape shape =new PolygonShape();
        shape.setAsBox(width,width);

        createFixture(shape, width, width, density, friction, restitution, PAPER_BODY, (short) (ROCK_BODY | PAPER_BODY | SCISSOR_BODY));
    }
}
