package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rpsparty.game.RPSParty;

public class RockView extends EntityView {
    public RockView(RPSParty game) {
        super(game);
    }


    public Sprite createSprite(RPSParty game) {
        Texture texture = game.getAssetManager().get("rock.png");

        return new Sprite(texture, Gdx.graphics.getWidth()/4 , Gdx.graphics.getWidth()/4);
    }
}
