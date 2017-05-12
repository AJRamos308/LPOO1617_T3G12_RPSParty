package com.rpsparty.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rpsparty.game.RPSParty;


/**
 * Created by bibib on 12/05/2017.
 */

public class RockView extends EntityView {
    public RockView(RPSParty game) {
        super(game);
    }


    public Sprite createSprite(RPSParty game) {
        Texture texture = game.getAssetManager().get("rock.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
