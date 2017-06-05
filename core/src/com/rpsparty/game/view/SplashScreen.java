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
 *  Represents the initial splash screen
 */

public class SplashScreen extends ScreenAdapter{
    private RPSParty game;

    /**
     *  The splash image.
     */
    private Image splashActor;

    /**
     *  The stage that is going to be drawn.
     */
    private Stage stage;

    /**
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     */
    public SplashScreen(RPSParty game){
        this.game = game;
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
        splashActor = new Image(new TextureRegion(new Texture("SplashScreen.png")));
        splashActor.setFillParent(true);
        SequenceAction actions = new SequenceAction(sequence(fadeIn(2f), delay(1.5f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        System.out.print("Beginning loading assets\n");
                        loadAssets();
                        System.out.print("Finished loading assets\n");
                    }
                }),
                fadeOut(2f),
                run(new Runnable(){
                     @Override
                    public void run(){
                        game.setScreen(new MainMenuScreen(game));
                    }
        })));

        splashActor.addAction(actions);
        stage.addActor(splashActor);
    }
    private void loadAssets() {
        //TODO: Fazer load de todas as imagens aqui.
        this.game.getAssetManager().load( "paper.png" , Texture.class);
        this.game.getAssetManager().load( "rock.png" , Texture.class);
        this.game.getAssetManager().load( "scissor.png" , Texture.class);
        this.game.getAssetManager().load( "check.png" , Texture.class);
        this.game.getAssetManager().load( "paperanime.png" , Texture.class);
        this.game.getAssetManager().load( "x.png" , Texture.class);
        this.game.getAssetManager().load( "BackGround.png" , Texture.class);
        this.game.getAssetManager().load( "bigSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "mediumSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "smallSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "extrasmallSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "scissorsAnimation.png" , Texture.class);
        this.game.getAssetManager().load( "rock.png" , Texture.class);
        this.game.getAssetManager().load( "coin.png" , Texture.class);
        this.game.getAssetManager().load( "white.jpg" , Texture.class);
        this.game.getAssetManager().load( "JoinPartyBtn.png" , Texture.class);
        this.game.getAssetManager().load( "StartPartyBtn.png" , Texture.class);
        this.game.getAssetManager().load( "infoBtn.png" , Texture.class);
        this.game.getAssetManager().load( "infoScreen.jpg" , Texture.class);
        this.game.getAssetManager().load( "loserScreen.png" , Texture.class);
        this.game.getAssetManager().load( "winnerScreen.png" , Texture.class);
        this.game.getAssetManager().load( "cursor.png" , Texture.class);
        this.game.getAssetManager().load( "Selection.png" , Texture.class);

        this.game.getAssetManager().finishLoading();
    }


}
