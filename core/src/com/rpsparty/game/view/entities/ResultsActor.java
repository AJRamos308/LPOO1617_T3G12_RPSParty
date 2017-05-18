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

    public ResultsActor() {
        super();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        won = new Texture(Gdx.files.internal("check.png"));
        lost = new Texture(Gdx.files.internal("x.png"));
        for(Integer result : MatchController.getInstance().getSets()) {
            System.out.println("RESULTADO SO FAR: " + result);
            if(result == 1) {
                System.out.println("PRINT" + MatchController.getInstance().getCurrSet());
                batch.draw(won, MatchController.getInstance().getCurrSet() * 2 * Gdx.graphics.getWidth()/8 + Gdx.graphics.getWidth()/8, 7*Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/8, Gdx.graphics.getWidth()/8);
            } else {
                System.out.println("PRINT" + MatchController.getInstance().getCurrSet());
                batch.draw(lost, MatchController.getInstance().getCurrSet() * 2 * Gdx.graphics.getWidth()/8 + Gdx.graphics.getWidth()/8, 7*Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/8, Gdx.graphics.getWidth()/8);
            }
        }
    }
}
