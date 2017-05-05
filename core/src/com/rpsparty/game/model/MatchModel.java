package com.rpsparty.game.model;

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

   // private EntityModel player1Entity;
   // private EntityModel player2Entity;

    public static MatchModel getInstance() {
        if (instance == null)
            instance = new MatchModel();
        return instance;
    }

    private MatchModel(){
        rock = new RockHandModel(MatchController.ARENA_WIDTH / 2, MatchController.ARENA_HEIGHT / 2, 0);
        paper = new PaperHandModel(MatchController.ARENA_WIDTH / 2, MatchController.ARENA_HEIGHT / 2, 0);
        scissor = new ScissorHandModel(MatchController.ARENA_WIDTH / 2, MatchController.ARENA_HEIGHT / 2, 0);
        currentOption = null;
    }

    public void setCurrentOption(String option) {
        if(option.equals("null")) {
            currentOption = null;
        } else if(option.equals("rock")) {
            currentOption = new RockHandModel(50, 50, 0);
        } else if(option.equals("paper")) {
            currentOption = new PaperHandModel(50, 50, 0);
        } else if(option.equals("scissor")) {
            currentOption = new ScissorHandModel(50, 50, 0);
        }
    }
    public RockHandModel getRock() { return rock; }
    public PaperHandModel getPaper() { return paper; }
    public ScissorHandModel getScissor() { return scissor; }
}
