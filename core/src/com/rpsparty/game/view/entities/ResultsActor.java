package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.MatchController;

/**
 * Created by afonso on 18/05/2017.
 */

public class ResultsActor extends Actor{
    private Texture won, lost;

    public ResultsActor(RPSParty game) {
        super();
        won = game.getAssetManager().get("check.png");
        lost = game.getAssetManager().get("x.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        int nResult = 0;
        for(Integer result : MatchController.getInstance().getSets()) {

            if(result == 1) {
                batch.draw(won, 2 * nResult * Gdx.graphics.getWidth()/16, 7*Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/8, Gdx.graphics.getWidth()/8);
            } else {
                batch.draw(lost, 2 * nResult * Gdx.graphics.getWidth()/16, 7*Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/8, Gdx.graphics.getWidth()/8);
            }
            nResult++;
        }
    }
}
