package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

public class AchievementsButton extends EntityButton {

    /**
     * class constructor
     * @param game
     */
    public AchievementsButton(RPSParty game) {
        super(game);
    }

    /**
     * set button's style
     * @param game
     */
    public void editStyle(RPSParty game) {
        skin.add("Achievements", game.getAssetManager().get("Achieve.png"));//a imagem e primeiro posta numa skin porque Skin consegue converter o seu conteudo em diferentes tipo como Drawable que e o que nos precisamos para atribuir uma imagem ao butao
        style = new ButtonStyle();
        style.up = skin.getDrawable("Achievements");

    }

    /**
     * edit button's bounds
     * @param game
     */
    public void editBounds(RPSParty game){
        bounds = new Rectangle(1*Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth()/3, Gdx.graphics.getWidth()/3);
    }
}
