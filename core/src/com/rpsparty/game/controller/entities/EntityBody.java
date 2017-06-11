package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.rpsparty.game.model.entities.EntityModel;

public class EntityBody {
    final static short ROCK_BODY = 0x1;
    final static short PAPER_BODY = 0x2;
    final static short SCISSOR_BODY = 0x3;
    final Body body;

    /**
     * Constructor for the Body
     * @param world world in which the body resides in
     * @param model model of the body
     */
    EntityBody(World world, EntityModel model) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());
        bodyDef.angle = model.getRotation();

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }
    /**
     * Creates a struct with the following characteristics and annexing them to a Body.
     * @param shape structure shape
     * @param width width of the structure
     * @param height height of the structure
     * @param density density of the structure
     * @param friction Friction of the object. Lower values represent less friction; range [0, 1]
     * @param restitution Elasticity of the object; range [0, 1]
     * @param category category to distinguish in the collisions
     * @param mask group of categories in which the structure can collide with
     */
    final void createFixture(PolygonShape shape, int width, int height, float density, float friction, float restitution, short category, short mask) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;

        body.createFixture(fixtureDef);

    }

    /**
     * get x position
     * @return body's x
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * get y position
     * @return body's y
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * sets body velocity
     * @param vX X velocity
     * @param vY Y velocity
     */
    public void setLinearVelocity(float vX, float vY) {
        body.setLinearVelocity(vX, vY);
    }

    /**
     * Every body have an Object associated with it (UserData)
     * In out case, this Object is the Body's Model (e.g. RockHandBody)
     * @return Object associated with the Body
     */
    public Object getUserData() {
        return body.getUserData();
    }
}
