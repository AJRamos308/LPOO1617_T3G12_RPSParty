package com.rpsparty.game.view.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.math.Rectangle;
import com.rpsparty.game.RPSParty;

public abstract class EntityButton extends Button {
    Skin skin;
    ButtonStyle style;
    Rectangle bounds;

    /**
     * button constructor
     * @param game
     */
    EntityButton(RPSParty game) {
        skin = new Skin();
        editStyle(game);
        editBounds(game);
        setStyle(style);
        setWidth(bounds.width);
        setHeight(bounds.height);
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /**
     * set button's style
     * @param game
     */
    public abstract void editStyle(RPSParty game);
    /**
     * edit button's bounds
     * @param game
     */
    public abstract void editBounds(RPSParty game);
}
