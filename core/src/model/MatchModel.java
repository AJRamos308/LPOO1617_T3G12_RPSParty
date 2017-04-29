package model;

import controller.MatchController;
import model.entities.EntityModel;
import model.entities.PaperHandModel;
import model.entities.RockHandModel;
import model.entities.ScissorHandModel;

/**
 * Created by bibib on 28/04/2017.
 */

public class MatchModel {
    /**
     * The singleton instance of the MatchGame model
     */
    private static MatchModel instance;

    private RockHandModel rock;
    private PaperHandModel paper;
    private ScissorHandModel scissor;

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

    }

    public RockHandModel getRock() { return rock; }
    public PaperHandModel getPaper() { return paper; }
    public ScissorHandModel getScissor() { return scissor; }
}
