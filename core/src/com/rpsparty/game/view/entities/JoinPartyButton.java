package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

/**
 * Created by bibib on 30/04/2017.
 */

public class JoinPartyButton extends EntityButton {
    public JoinPartyButton(RPSParty game) {
        super(game);
    }

    public void editStyle(RPSParty game) {
        skin.add("createPartyImage", game.getAssetManager().get("badlogic.jpg"));//a imagem e primeiro posta numa skin porque Skin consegue converter o seu conteudo em diferentes tipo como Drawable que e o que nos precisamos para atribuir uma imagem ao butao
        style = new ButtonStyle();
        style.up = skin.getDrawable("createPartyImage");

    }

    public void editBounds(RPSParty game){
        bounds = new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2-200, 100, 100);
    }
}