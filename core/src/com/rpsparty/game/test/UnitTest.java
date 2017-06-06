package com.rpsparty.game.test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.ConnectionSockets;
import com.rpsparty.game.controller.MatchController;
import com.rpsparty.game.controller.PaperGameController;
import com.rpsparty.game.controller.RockGameController;
import com.rpsparty.game.controller.ScissorGameController;
import com.rpsparty.game.controller.entities.EntityBody;
import com.rpsparty.game.controller.entities.RockHandBody;
import com.rpsparty.game.model.RockGameModel;
import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.model.entities.RockHandModel;

import org.junit.Test;
import java.util.regex.Pattern;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 */
public class UnitTest extends GameTest{
    @Test
    public void ImageLoading(){
        AssetManager handler = new AssetManager();
        assertEquals(0, handler.getLoadedAssets());
        handler.load( "Achieve.png" , Texture.class);
        assertNotNull(handler.getLoadedAssets());
    }

    @Test
    public void GetSets(){
        assertEquals(0, MatchController.getInstance().getSets().size());
        MatchController.getInstance().getSets().add(1);
        MatchController.getInstance().update(1);
        assertEquals(1, MatchController.getInstance().getSets().size());
        assertEquals(1, MatchController.getInstance().getSets().get(0).intValue());
    }

    @Test
    public void Animating(){
        ConnectionSockets.getInstance().reset();
        assertFalse(MatchController.getInstance().isAnimation());
        assertFalse(MatchController.getInstance().arePlayersReady());
        /*MatchController.getInstance().choosePaper();
        MatchController.getInstance().opponentChooseRock();
        assertEquals("paper", MatchController.getInstance().getMyChoice());
        assertEquals("rock", MatchController.getInstance().getOpponentChoice());
        MatchController.getInstance().chooseRock();
        MatchController.getInstance().opponentChoosePaper();
        assertEquals("rock", MatchController.getInstance().getMyChoice());
        assertEquals("paper", MatchController.getInstance().getOpponentChoice());
        MatchController.getInstance().chooseScissor();
        MatchController.getInstance().opponentChooseScissor();
        assertEquals("scissors", MatchController.getInstance().getMyChoice());
        assertEquals("scissors", MatchController.getInstance().getOpponentChoice());*/
        /*World world = new World(new Vector2(0, 0), true);
        RockHandModel model = new RockHandModel(0,0,0);
        EntityBody rock = new RockHandBody(world, model);
        assertEquals(rock.getAngle(),0,0.1);*/
    }

    @Test
    public void PaperGameControl(){
        PaperGameController.getInstance();
        assertEquals(0,PaperGameController.getInstance().getMyPoints(),0);
        PaperGameController.getInstance().setDistance(50);
        assertEquals(50,PaperGameController.getInstance().getMyPoints(),0);
        assertEquals(0.1f,PaperGameController.getInstance().getTimeToNextFrame(),0);
        PaperGameController.getInstance().setTimeToNextFrame(1.1f);
        assertEquals(1.1f,PaperGameController.getInstance().getTimeToNextFrame(),0);
        PaperGameController.getInstance().reset();
    }

    @Test
    public void RockGameControl(){
        RockGameController.getInstance();
        RockGameController.getInstance().touchRockOne();
        for (int i = 0 ; i < 50 ; i++ ){
            RockGameController.getInstance().touchRockOne();
            RockGameController.getInstance().touchRockTwo();
            RockGameController.getInstance().touchRockThree();
            RockGameController.getInstance().touchRockFour();
            RockGameController.getInstance().touchRockFive();
        }
        assertTrue(RockGameController.getInstance().getPoints() > 0);
        RockGameController.getInstance().update(0.1f);
        assertFalse(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockOne()));
        RockGameController.getInstance().createNewRock(RockGameModel.getInstance().getRockOne());
        assertTrue(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockOne()));
        assertFalse(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockTwo()));
        RockGameController.getInstance().createNewRock(RockGameModel.getInstance().getRockTwo());
        assertTrue(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockTwo()));
        assertFalse(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockThree()));
        RockGameController.getInstance().createNewRock(RockGameModel.getInstance().getRockThree());
        assertTrue(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockThree()));
        assertFalse(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockFour()));
        RockGameController.getInstance().createNewRock(RockGameModel.getInstance().getRockFour());
        assertTrue(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockFour()));
        assertFalse(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockFive()));
        RockGameController.getInstance().createNewRock(RockGameModel.getInstance().getRockFive());
        assertTrue(RockGameController.getInstance().isButtonToUpdate(RockGameModel.getInstance().getRockFive()));
        RockGameController.getInstance().reset();
    }

    @Test
    public void ScissorsGameControl(){
        ScissorGameController.getInstance();
        ScissorGameController.getInstance().reset();
        assertTrue(ScissorGameController.getInstance().getMyPoints() == 0);
        ScissorGameController.getInstance().setCenter(200,200);
        ScissorGameController.getInstance().setRadius(5);
        assertEquals(5,(int)ScissorGameController.getInstance().getRadius());
        ScissorGameController.getInstance().setMyPoints(150,150);
        assertTrue(ScissorGameController.getInstance().getMyPoints() > 0);
        assertTrue(ScissorGameController.getInstance().setScissorPosition(0.1f));
        assertFalse(ScissorGameController.getInstance().isTie());
        //ScissorPos[0] = 200+5*cos(0.1) ~= 204.975
        //ScissorPos[1] = 200+5*sin(0.1) ~= 200.49916
        //Vel[0] = -5*sin(0.1) ~= -0.499    Last = 0
        //Vel[1] =  5*cos(0.1) ~= 4.975          Last = 1
        //Norma ~= 1   Last = 1
        //Ang ~= 0.1
        assertEquals(204.975 ,ScissorGameController.getInstance().getScissorPosition()[0], 0.01);
        assertEquals(200.49916 ,ScissorGameController.getInstance().getScissorPosition()[1], 0.01);
        assertEquals(-0.499 ,ScissorGameController.getInstance().getScissorVel()[0], 0.01);
        assertEquals(4.975 ,ScissorGameController.getInstance().getScissorVel()[1], 0.01);
        assertEquals(0.1, ScissorGameController.getInstance().getScissorAng(), 0.01);

        System.out.println(ScissorGameController.getInstance().getScissorAng());
    }
}
