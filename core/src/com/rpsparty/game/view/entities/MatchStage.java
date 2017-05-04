package com.rpsparty.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsparty.game.RPSParty;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MatchStage extends Stage {
    private World world;

    private final RockHandActor rock;
    private final PaperHandActor paper;
    private final ScissorHandActor scissor;

    MatchStage (RPSParty game) {
        world = new World(new Vector2(0,0), true);
        rock = new RockHandActor(game);
        paper = new PaperHandActor(game);
        scissor = new ScissorHandActor(game);
        rock.setPosition(50,0);
        paper.setPosition(100,0);
        scissor.setPosition(200,0);
        addListenersActors();
        addActor(rock);
        addActor(paper);
        addActor(scissor);
    }


    public void loadAssets(RPSParty game) {
        game.getAssetManager().load( "badlogic.jpg" , Texture.class);
        game.getAssetManager().load( "test.jpg" , Texture.class);
        game.getAssetManager().load( "Achieve.png" , Texture.class);
        game.getAssetManager().load( "settings.png" , Texture.class);
        game.getAssetManager().finishLoading();
    }

    public void addListenersActors() {
        rock.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                (
                return true;
            }
        });
    }
}
