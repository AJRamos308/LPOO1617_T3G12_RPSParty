package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

public class SettingsButton extends EntityButton {

    public SettingsButton(RPSParty game) {
        super(game);
    }

    public void editStyle(RPSParty game) {
        skin.add("Settings", game.getAssetManager().get("settings.png"));//a imagem e primeiro posta numa skin porque Skin consegue converter o seu conteudo em diferentes tipo como Drawable que e o que nos precisamos para atribuir uma imagem ao butao
        style = new ButtonStyle();
        style.up = skin.getDrawable("Settings");

    }

    public void editBounds(RPSParty game){
        bounds = new Rectangle(7*Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/3, Gdx.graphics.getWidth()/3);
    }
}
