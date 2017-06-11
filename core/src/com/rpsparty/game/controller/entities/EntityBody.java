package com.rpsparty.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.rpsparty.game.model.entities.EntityModel;

public class EntityBody {
    final static short ROCK_BODY = 0x1;//categorias de colisao
    final static short PAPER_BODY = 0x2;
    final static short SCISSOR_BODY = 0x3;
    final Body body;

    EntityBody(World world, EntityModel model) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());
        bodyDef.angle = model.getRotation();

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }
    /**
     * criar uma estrutura com caracteristicas especificas (densidade, interacao entre objetos, etc)
     * e anexa-la ao Body
     * @param shape a forma da estrutura
     * @param width largura da estrutura
     * @param height altura da estrutura
     * @param density densidade da estrutura
     * @param friction grau de friccao do objeto; quanto menor, mais o objeto desliza; range [0, 1]
     * @param restitution grau de elasticidade do objeto (uma bola pinchona tera uma alta restitution); range [0, 1]
     * @param category cada estrutura tem uma categoria para de detinguirem nas colisoes
     * @param mask conjunto de categorias com as quais esta estrutura pode colidir(haver interacao)
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
     * @return
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * get y position
     * @return
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * sets body velocity
     * @param vX
     * @param vY
     */
    public void setLinearVelocity(float vX, float vY) {
        body.setLinearVelocity(vX, vY);
    }

    /**
     * Todos os Bodies tem um Object associado a eles (o UserData).
     * No nosso caso esse Object e o modelo do Body (e.g RockHandBody)
     * @return Object associado a Body
     */
    public Object getUserData() {
        return body.getUserData();
    }
}
