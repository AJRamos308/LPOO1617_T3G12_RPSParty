package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

/**
 * Created by afonso on 25/05/2017.
 */

public class WinOrLoseView extends EntityView {
    Texture loser;
    Texture winner;

    public WinOrLoseView(RPSParty game) {
        super(game);
    }

    public Sprite createSprite(RPSParty game) {
        loser = game.getAssetManager().get("scissor.png");
        winner = game.getAssetManager().get("rock.png");

        return new Sprite(loser, Gdx.graphics.getWidth()/5 , Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5 , Gdx.graphics.getWidth()/5 );
    }

    /*public Sprite setWinner(RPSParty game,boolean win){

        if (win)
            return new Sprite(winner, Gdx.graphics.getWidth()/3 , Gdx.graphics.getWidth()/3, Gdx.graphics.getWidth()/3, Gdx.graphics.getWidth()/3);
        else
            return new Sprite(loser, Gdx.graphics.getWidth()/3 , Gdx.graphics.getWidth()/3, Gdx.graphics.getWidth()/3, Gdx.graphics.getWidth()/3);
    }*/
}
