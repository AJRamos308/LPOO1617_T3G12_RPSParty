package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.MatchController;
import com.rpsparty.game.controller.PaperGameController;
import com.rpsparty.game.controller.RockGameController;
import com.rpsparty.game.controller.ScissorGameController;
import com.rpsparty.game.model.MatchModel;
import com.rpsparty.game.model.RockGameModel;
import com.rpsparty.game.view.entities.WinOrLoseView;

/**
 * Created by afonso on 24/05/2017.
 */

public class EndGameScreen extends ScreenAdapter {

    private final RPSParty game;
    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;
    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private float displayTime = 7; //Time that it takes to change back to MainMenuScreen if user doesn't click
    private WinOrLoseView winner;
    private Image background;
    private Stage stage;

    public EndGameScreen(RPSParty game){
        this.game = game;
        camera = createCamera();
        winner = new WinOrLoseView(game);
        stage = new Stage();
        background = new Image(new TextureRegion((Texture)winner.getFinalTexture()));
        background.setFillParent(true);
        stage.addActor(background);
    }

    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }
    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        camera.update();
        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(camera.combined);
        stage.draw();
        game.getBatch().end();
        displayTime -= delta;
        if (displayTime < 0){
            resetAllControllerInstances();
            resetAllModelInstances();
            game.resetServer();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    public void resetAllControllerInstances() {
        PaperGameController.getInstance().reset();
        RockGameController.getInstance().reset();
        ScissorGameController.getInstance().reset();
        MatchController.getInstance().clearSets();
        MatchController.getInstance().resetAll();
    }

    public void resetAllModelInstances() {
        MatchModel.getInstance().reset();
        RockGameModel.getInstance().reset();
    }
}
