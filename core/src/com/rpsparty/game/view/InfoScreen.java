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

/**
 * Created by bibib on 02/06/2017.
 */

public class InfoScreen extends ScreenAdapter {
    private final RPSParty game;
    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;
    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_WIDTH = 20;
    private Image background;
    private Stage stage;

    public InfoScreen(RPSParty game) {
        this.game = game;
        camera = createCamera();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createBackground();
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

    private void createBackground() {
        background = new Image(new TextureRegion((Texture)game.getAssetManager().get("infoScreen.jpg")));
        background.setFillParent(true);
        stage.addActor(background);
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        game.backpressed = false;
        camera.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        game.getBatch().begin();
        stage.draw();
        game.getBatch().end();
        goBack();

    }

    public void goBack() {
        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

}
