package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.rpsparty.game.RPSParty;

/**
 * Created by afonso on 07/05/2017.
 */

public class RockButton extends EntityButton {
    public RockButton(RPSParty game) {
        super(game);
    }

    public void editStyle(RPSParty game) {
        skin.add("Rock", game.getAssetManager().get("rock.png"));//a imagem e primeiro posta numa skin porque Skin consegue converter o seu conteudo em diferentes tipo como Drawable que e o que nos precisamos para atribuir uma imagem ao butao
        style = new Button.ButtonStyle();
        style.up = skin.getDrawable("Rock");
    }

    public void editBounds(RPSParty game){
        bounds = new Rectangle(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
    }
}
