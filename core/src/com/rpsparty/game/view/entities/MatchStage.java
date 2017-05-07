package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.rpsparty.game.model.MatchModel;

import com.rpsparty.game.controller.MatchController;

public class MatchStage extends Stage {
    private final RPSParty game;
    private World world;

   /* private final RockHandActor rock;
    private final PaperHandActor paper;
    private final ScissorHandActor scissor;*/

    public MatchStage (RPSParty game) {
        this.game = game;
        world = new World(new Vector2(0,0), true);
        loadAssets(game);
        /*rock = new RockHandActor(game);
        paper = new PaperHandActor(game);
        scissor = new ScissorHandActor(game);
        rock.setPosition(Gdx.graphics.getWidth()/8,200);
        paper.setPosition(Gdx.graphics.getWidth()/4,200);
        scissor.setPosition(Gdx.graphics.getWidth()/2,200);
        rock.createSprite(game);
        paper.createSprite(game);
        scissor.createSprite(game);
        addActor(rock);
        addActor(paper);
        addActor(scissor);
        addListenersActors();*/
    }


    public void loadAssets(RPSParty game) {
        game.getAssetManager().load( "badlogic.jpg" , Texture.class);
        game.getAssetManager().load( "test.jpg" , Texture.class);
        game.getAssetManager().load( "Achieve.png" , Texture.class);
        game.getAssetManager().load( "settings.png" , Texture.class);
        game.getAssetManager().finishLoading();
    }
/*
    public void addListenersActors() {
        rock.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicou na pedra");
                MatchController.getInstance().chooseRock();
            }});
        paper.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicou no papel");
                MatchController.getInstance().choosePaper();
            }});
        scissor.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicou na tesoura");
                MatchController.getInstance().chooseScissor();
            }});
    }
*/
    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        world.step(delta, 6, 2);
        //rock.setPosition(MatchModel.getInstance().getRock().getX(), MatchModel.getInstance().getRock().getY());
        //paper.setPosition(MatchModel.getInstance().getPaper().getX(), MatchModel.getInstance().getPaper().getY());
        //scissor.setPosition(MatchModel.getInstance().getScissor().getX(), MatchModel.getInstance().getScissor().getY());
    }
}
