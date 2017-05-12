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


    EntityView(RPSParty game) {
        sprite = createSprite(game);
    }


    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }


    public abstract Sprite createSprite(RPSParty game);


    public void update(EntityModel model) {
        sprite.setCenter(model.getX(), model.getY());
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }
}
