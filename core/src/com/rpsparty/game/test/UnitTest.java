package com.rpsparty.game.test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.rpsparty.game.controller.ConnectionSockets;
import com.rpsparty.game.controller.MatchController;

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
        MatchController.getInstance().choosePaper(); //TODO: Ele tenta enviar uma mensagem, mas como é obvio, falha
        MatchController.getInstance().opponentChooseRock();
        assertEquals("paper", MatchController.getInstance().getMyChoice());
        assertEquals("rock", MatchController.getInstance().getMyChoice());
        MatchController.getInstance().chooseRock();
        MatchController.getInstance().opponentChoosePaper();
        assertEquals("rock", MatchController.getInstance().getMyChoice());
        assertEquals("paper", MatchController.getInstance().getMyChoice());
        MatchController.getInstance().chooseScissor();
        MatchController.getInstance().opponentChooseScissor();
        assertEquals("scissors", MatchController.getInstance().getMyChoice());
        assertEquals("scissors", MatchController.getInstance().getMyChoice());
    }
}
