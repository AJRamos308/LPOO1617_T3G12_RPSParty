package com.rpsparty.game.model;

import com.badlogic.gdx.Gdx;
import com.rpsparty.game.controller.MatchController;
import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.model.entities.RockHandModel;
import com.rpsparty.game.model.entities.PaperHandModel;
import com.rpsparty.game.model.entities.ScissorHandModel;

public class MatchModel {
    /**
     * The singleton instance of the MatchGame model
     */
    private static MatchModel instance;

    private RockHandModel rock;
    private PaperHandModel paper;
    private ScissorHandModel scissor;

    private EntityModel player1Entity;
    private EntityModel player2Entity;

    public static MatchModel getInstance() {
        if (instance == null)
            instance = new MatchModel();
        return instance;
    }

    /**
     * class constructor
     */
    private MatchModel(){
        rock = new RockHandModel(Gdx.graphics.getWidth()/8,100,0);
        paper = new PaperHandModel(Gdx.graphics.getWidth()/4, 100, 0);
        scissor = new ScissorHandModel(Gdx.graphics.getWidth()/2, 100, 0);
        player1Entity = null;
        player2Entity = null;
    }

    /**
     * saves player's choice
     * @param s
     * @return EntityModel depending on player's choice
     */
    public EntityModel setMyChoice(String s) {
        float width;
        width = Gdx.graphics.getWidth();
        if (s.equals("rock")) {
            player1Entity = new RockHandModel(width/2,0,0);
        } else if (s.equals("paper")) {
            player1Entity = new PaperHandModel(width/2,0,0);
        } else if (s.equals("scissor")) {
            player1Entity = new ScissorHandModel(width/2,0,0);
        }
        return player1Entity;
    }

    /**
     * saves opponent's choice
     * @param s
     * @return EntityModel depending on player's choice
     */
    public EntityModel setOpponentChoice(String s) {
        float width, height;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        if (s.equals("rock")) {
            player2Entity = new RockHandModel(width/2,height-height/4,0);
        } else if (s.equals("paper")) {
            player2Entity = new PaperHandModel(width/2,height-height/4,0);
        } else if (s.equals("scissor")) {
            player2Entity = new ScissorHandModel(width/2,height-height/4,0);
        }
        return player2Entity;
    }

    /**
     * reset model
     */
    public void reset() {
        instance = null;
    }

    /**
     * get rock's model
     * @return rock's model
     */
    public RockHandModel getRock() { return rock; }

    /**
     *
     * @return paper's model
     */
    public PaperHandModel getPaper() { return paper; }

    /**
     *
     * @return scissors' model
     */
    public ScissorHandModel getScissor() { return scissor; }

    /**
     *
     * @return player's choice model
     */
    public EntityModel getPlayer1Entity() {
        return player1Entity;
    }

    /**
     *
     * @return opponent's choice model
     */
    public EntityModel getPlayer2Entity() {
        return player2Entity;
    }
}
