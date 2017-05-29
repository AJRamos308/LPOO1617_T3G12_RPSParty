package com.rpsparty.game.test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
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
}
