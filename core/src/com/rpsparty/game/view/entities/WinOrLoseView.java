package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.MatchController;

/**
 * Created by afonso on 25/05/2017.
 */

public class WinOrLoseView extends EntityView {
    Texture loser;
    Texture winner;
    private int wins;
    private int losses;

    public WinOrLoseView(RPSParty game) {
        super(game);
    }

    public Sprite createSprite(RPSParty game) {
        loser = game.getAssetManager().get("scissor.png");
        winner = game.getAssetManager().get("rock.png");

        if (MatchController.getInstance().getSets().size() > 0) {
            for (int i = 0; i < MatchController.getInstance().getSets().size(); i++) {
                if (MatchController.getInstance().getSets().get(i) == 1)
                    wins += 1;
                else
                    losses += 1;
            }
            if (wins > losses)
                return new Sprite(winner, loser.getWidth(), loser.getHeight(), 200, 200);
            else
                return new Sprite(loser, loser.getWidth(), loser.getHeight(), 200, 200);
        }
        return new Sprite(loser, loser.getWidth(), loser.getHeight());
    }
}
