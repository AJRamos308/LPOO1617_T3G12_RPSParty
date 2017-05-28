package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rpsparty.game.RPSParty;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 *  Represents the win screen
 */

public class MiniGameEndScreen extends ScreenAdapter{
    private RPSParty game;

    /**
     *  The splash image.
     */
    private Image splashActor;

    /**
     *  The stage that is going to be drawn.
     */
    private Stage stage;
    private static final int ROCKGAME = 0;
    private static final int PAPERGAME = 1;
    private static final int SCISSORSGAME = 2;

    /**
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     * @param miniGame which game it was
     * @param win If it was a win or not
     */
    public MiniGameEndScreen(RPSParty game, int miniGame, int win){
        this.game = game;
        setActor(miniGame, win);
    }


    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        stage.act();
        stage.draw();
    }


    @Override
    public void show(){

        stage = new Stage();
        splashActor.setFillParent(true);
        SequenceAction actions = new SequenceAction(sequence(fadeIn(2f), delay(1.5f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        System.out.print("MiniGameEndScreen\n");
                    }
                }),
                fadeOut(2f),
                run(new Runnable(){
                    @Override
                    public void run(){
                        game.setScreen(new GameScreen(game));
                    }
                })));

        splashActor.addAction(actions);
        stage.addActor(splashActor);
    }

    private void setActor(int miniGame,int win){
        if (miniGame == ROCKGAME){
            if (win == 1)
                splashActor = new Image(new TextureRegion(new Texture("scissorsWin.jpg")));
            else
                splashActor = new Image(new TextureRegion(new Texture("scissorsWin.jpg")));
        }
        else if (miniGame == PAPERGAME){
            if (win == 1)
                splashActor = new Image(new TextureRegion(new Texture("scissorsWin.jpg")));
            else
                splashActor = new Image(new TextureRegion(new Texture("scissorsWin.jpg")));
        }
        else if (miniGame == SCISSORSGAME){
            if (win == 1)
                splashActor = new Image(new TextureRegion(new Texture("scissorsWin.jpg")));
            else
                splashActor = new Image(new TextureRegion(new Texture("scissorsWin.jpg")));
        }
    }

}
