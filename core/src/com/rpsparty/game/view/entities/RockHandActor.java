package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rpsparty.game.RPSParty;

import com.rpsparty.game.model.MatchModel;
import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.view.MainMenuScreen;

public class RockHandActor extends EntityActor {
    private Texture texture;

    public RockHandActor(RPSParty game) {
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
        texture = game.getAssetManager().get("rock.png");
        int x = (int)MatchModel.getInstance().getRock().getX();
        int y = (int)MatchModel.getInstance().getRock().getY();
        System.out.println("> Coordenadas da pedra:");
        System.out.println("x: "+x);
        System.out.println("y: "+y);
        System.out.println("largura e altura: "+Gdx.graphics.getWidth()/5);
        return new Sprite(texture, x, y, Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

}
