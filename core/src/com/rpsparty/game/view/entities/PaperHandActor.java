package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

import com.rpsparty.game.model.MatchModel;
import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.view.MainMenuScreen;

public class PaperHandActor extends EntityActor {
    private Texture texture;

    public PaperHandActor(RPSParty game) {
        super(game);
    }
    public void update(EntityModel model) {

        sprite.setCenter(model.getX() / MainMenuScreen.PIXEL_TO_METER, model.getY() / MainMenuScreen.PIXEL_TO_METER);
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
        int x = (int)MatchModel.getInstance().getPaper().getX();
        int y = (int)MatchModel.getInstance().getPaper().getY();
        System.out.println("> Coordenadas do papel:");
        System.out.println("x: "+x);
        System.out.println("y: "+y);
        System.out.println("largura e altura: "+Gdx.graphics.getWidth()/6);
        return new Sprite(texture, x, y, Gdx.graphics.getWidth()/6, Gdx.graphics.getWidth()/6);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
}
