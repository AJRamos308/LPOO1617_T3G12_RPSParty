package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;

import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.view.CreatePartyScreen;
import com.rpsparty.game.view.MainMenuScreen;

import com.rpsparty.game.controller.MatchController;

public abstract class EntityActor extends Actor {
    /**
     * The sprite representing this entity view.
     */
    Sprite sprite;
    RPSParty game;

    /**
     * Creates a view belonging to a game.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     */
    EntityActor(RPSParty game) {
        this.game = game;
        setTouchable(Touchable.enabled);
        sprite = createSprite(game);
        setWidth(sprite.getTexture().getWidth());
        setHeight(sprite.getTexture().getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setOrigin(getWidth() / 2, getHeight() / 2);
    }

    /**
     * Draws the sprite from this view using a sprite batch.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(getColor());
        sprite.draw(batch);
    }

    /**
     * Abstract method that creates the view sprite. Concrete
     * implementation should extend this method to create their
     * own sprites.
     *
     * @param game the game this view belongs to. Needed to access the
     *             asset manager to get textures.
     * @return the sprite representing this view.
     */
    public abstract Sprite createSprite(RPSParty game);

    /**
     * Updates this view based on a certain model.
     *
     * @param model the model used to update this view
     */
    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / MainMenuScreen.PIXEL_TO_METER, model.getY() / MainMenuScreen.PIXEL_TO_METER);
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }


}
