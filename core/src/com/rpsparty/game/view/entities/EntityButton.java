package com.rpsparty.game.view.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rpsparty.game.RPSParty;

/**
 * Created by bibib on 30/04/2017.
 */

public abstract class EntityButton extends Button {
    Skin skin;
    ButtonStyle style;
    Rectangle bounds;

    EntityButton(RPSParty game) {
        skin = new Skin();
        editStyle(game);
        editBounds(game);
        setStyle(style);
        setWidth(bounds.width);
        setHeight(bounds.height);
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public abstract void editStyle(RPSParty game);
    public abstract void editBounds(RPSParty game);
}
