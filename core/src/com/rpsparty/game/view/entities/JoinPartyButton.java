package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

public class JoinPartyButton extends EntityButton {

    public JoinPartyButton(RPSParty game) {
        super(game);
    }

    public void editStyle(RPSParty game) {
        skin.add("createPartyImage", game.getAssetManager().get("JoinPartyBtn.png"));//a imagem e primeiro posta numa skin porque Skin consegue converter o seu conteudo em diferentes tipo como Drawable que e o que nos precisamos para atribuir uma imagem ao butao
        style = new ButtonStyle();
        style.up = skin.getDrawable("createPartyImage");

    }

    public void editBounds(RPSParty game){
        bounds = new Rectangle(Gdx.graphics.getWidth()/12, 8*Gdx.graphics.getHeight()/20, 5*Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/8);
    }
}
