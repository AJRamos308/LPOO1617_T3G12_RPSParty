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
    private EntityModel currentOption;

    private EntityModel player1Entity;
    private EntityModel player2Entity;

    public static MatchModel getInstance() {
        if (instance == null)
            instance = new MatchModel();
        return instance;
    }

    private MatchModel(){
        rock = new RockHandModel(Gdx.graphics.getWidth()/8,100,0);
        paper = new PaperHandModel(Gdx.graphics.getWidth()/4, 100, 0);
        scissor = new ScissorHandModel(Gdx.graphics.getWidth()/2, 100, 0);
        currentOption = null;
        player1Entity = null;
        player2Entity = null;
    }

   /* public void setCurrentOption(String option) {
        if(option.equals("null")) {
            currentOption = null;
        } else if(option.equals("rock")) {
            currentOption = new RockHandModel(50, 50, 0);
        } else if(option.equals("paper")) {
            currentOption = new PaperHandModel(50, 50, 0);
        } else if(option.equals("scissor")) {
            currentOption = new ScissorHandModel(50, 50, 0);
        }
    }*/

    public EntityModel setMyChoice(String s) {
        float width;
        width = Gdx.graphics.getWidth();
        if (s.equals("rock")) {
            player1Entity = new RockHandModel(width/4,0,0);
        } else if (s.equals("paper")) {
            player1Entity = new PaperHandModel(width/4,0,0);
        } else if (s.equals("scissor")) {
            player1Entity = new ScissorHandModel(width/4,0,0);
        }
        return player1Entity;
    }

    public EntityModel setOpponentChoice(String s) {
        float width, height;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        if (s.equals("rock")) {
            player2Entity = new RockHandModel(width/4,height-height/4,0);
        } else if (s.equals("paper")) {
            player2Entity = new PaperHandModel(width/4,height-height/4,0);
        } else if (s.equals("scissor")) {
            player2Entity = new ScissorHandModel(width/4,height-height/4,0);
        }
        return player2Entity;
    }

    public void reset() {
        player1Entity = null;
        player2Entity = null;
    }
    public RockHandModel getRock() { return rock; }
    public PaperHandModel getPaper() { return paper; }
    public ScissorHandModel getScissor() { return scissor; }

    public EntityModel getPlayer1Entity() {
        return player1Entity;
    }
    public EntityModel getPlayer2Entity() {
        return player2Entity;
    }
}
