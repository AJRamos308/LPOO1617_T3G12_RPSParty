package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.model.entities.RockObjectGameModel;


public class RockGameButton extends Button {
    private RockObjectGameModel model;
    Skin skin;
    ButtonStyle style1, style2;
    Rectangle bounds;

    /**
     * class constructor
     * @param game
     * @param model
     */
    public RockGameButton(RPSParty game, RockObjectGameModel model) {
        this.model = model;
        skin = new Skin();
        editStyle(game);
        editBounds(game);
        setStyle(style1);
        setWidth(bounds.width);
        setHeight(bounds.height);
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /**
     * sets button's style
     * @param game
     */
    public void editStyle(RPSParty game) {
        skin.add("Rock", game.getAssetManager().get("rock1.png"));//a imagem e primeiro posta numa skin porque Skin consegue converter o seu conteudo em diferentes tipo como Drawable que e o que nos precisamos para atribuir uma imagem ao butao
        if(model.hasCoin()) {
            skin.add("Coin", game.getAssetManager().get("coin.png"));
            style2 = new Button.ButtonStyle();
            style2.up = skin.getDrawable("Coin");
        } else {
            skin.add("Empty", game.getAssetManager().get("white.jpg"));
            style2 = new Button.ButtonStyle();
            style2.up = skin.getDrawable("Empty");
        }

        style1 = new Button.ButtonStyle();
        style1.up = skin.getDrawable("Rock");


    }

    /**
     * sets button's bounds
     * @param game
     */
    public void editBounds(RPSParty game){
        bounds = new Rectangle(model.getX(), model.getY(), Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
    }

    /**
     * change button's style
     */
    public void changeStyle () {
        setStyle(style2);
    }

    /**
     * get button's model
     * @return
     */
    public RockObjectGameModel getModel() {
        return model;
    }


}
