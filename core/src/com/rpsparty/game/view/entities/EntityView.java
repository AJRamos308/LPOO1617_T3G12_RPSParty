package com.rpsparty.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.model.entities.EntityModel;

/**
 * Created by bibib on 12/05/2017.
 */

public abstract class EntityView {
    Sprite sprite;

    /**
     * class constructor
     * @param game
     */
    EntityView(RPSParty game) {
        sprite = createSprite(game);
    }


    /**
     * draw view's sprite
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }


    /**
     * create view's sprite
     * @param game
     * @return view's sprite
     */
    public abstract Sprite createSprite(RPSParty game);

    /**
     * update button's model
     * @param model
     */
    public void update(EntityModel model) {
        sprite.setCenter(model.getX(), model.getY());
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }
}
