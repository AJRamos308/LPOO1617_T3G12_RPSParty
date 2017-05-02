package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

public class HelpButton extends EntityButton{

    public HelpButton(RPSParty game) {
        super(game);
    }

    public void editStyle(RPSParty game) {
        skin.add("helpImage", game.getAssetManager().get("badlogic.jpg"));//a imagem e primeiro posta numa skin porque Skin consegue converter o seu conteudo em diferentes tipo como Drawable que e o que nos precisamos para atribuir uma imagem ao butao
        style = new ButtonStyle();
        style.up = skin.getDrawable("helpImage");

    }

    public void editBounds(RPSParty game){
        bounds = new Rectangle(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4, 100, 100);
    }
}
