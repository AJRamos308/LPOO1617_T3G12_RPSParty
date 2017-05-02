package com.rpsparty.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rpsparty.game.RPSParty;

import com.rpsparty.game.model.entities.EntityModel;

import static com.rpsparty.game.view.MainMenuScreen.PIXEL_TO_METER;

public class ScissorHandActor extends EntityActor {
    private Texture texture;

    public ScissorHandActor(RPSParty game) {
        super(game);
    }
    public void update(EntityModel model) {

        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }
    /**
     * Creates a sprite representing this space ship.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the sprite representing this space ship
     */
    @Override
    public Sprite createSprite(RPSParty game) {
        texture = game.getAssetManager().get("badlogic.jpg");
        return new Sprite(texture);
    }
}
