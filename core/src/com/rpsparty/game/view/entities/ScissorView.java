package com.rpsparty.game.view.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rpsparty.game.RPSParty;

public class ScissorView extends EntityView {
    public ScissorView(RPSParty game) {
        super(game);
    }

    public Sprite createSprite(RPSParty game) {
        Texture texture = game.getAssetManager().get("scissor.png");

        return new Sprite(texture, Gdx.graphics.getWidth()/5 , Gdx.graphics.getWidth()/5);
    }
}
