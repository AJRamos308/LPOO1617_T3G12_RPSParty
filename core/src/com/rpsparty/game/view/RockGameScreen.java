package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;
import com.badlogic.gdx.Input.Keys;
import com.rpsparty.game.view.entities.RockGameButton;

import java.util.Random;

public class RockGameScreen extends ScreenAdapter {
    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.04f;

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
    private Stage stage;
    private RockGameButton Rock1;
    private RockGameButton Rock2;
    private RockGameButton Rock3;
    private RockGameButton Rock4;
    private RockGameButton Rock5;
    private int nCoins;
    Random random = new Random();

    public RockGameScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();
        addButtons();
        addListeners();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(Rock1);
        stage.addActor(Rock2);
        stage.addActor(Rock3);
        stage.addActor(Rock4);
        stage.addActor(Rock5);
        nCoins = 0;
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        //this.game.getAssetManager().load( "badlogic.jpg" , Texture.class);
        this.game.getAssetManager().finishLoading();
    }
    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        float ratio = ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio);

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
        //Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        game.backpressed = false;
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.update();
        stage.act();
        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(camera.combined);
        stage.draw();
        game.getBatch().end();
        if (Gdx.input.isKeyPressed(Keys.BACK)) {
            if (!game.backpressed) {
                game.backpressed = true;
            } else if (game.backpressed) {
                game.backpressed = false;
                Gdx.app.exit();
            }
        }

    }

    public void addButtons() {
        Rock1 = new RockGameButton(game);
        Rock1.editBounds(game,Gdx.graphics.getWidth()/6,Gdx.graphics.getWidth()/6, 5*Gdx.graphics.getWidth()/6,5*Gdx.graphics.getWidth()/6);
        Rock1.editStyle(game, "scissors.png"); //Serves as demonstration
        Rock2 = new RockGameButton(game);
        Rock3 = new RockGameButton(game);
        Rock4 = new RockGameButton(game);
        Rock5 = new RockGameButton(game);

    }

    public void addListeners() {
        Rock1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int odd = random.nextInt(10) + 1;
                if (odd == 1)
                    nCoins++;
            }});
        Rock2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int odd = random.nextInt(10) + 1;
                if (odd == 1)
                    nCoins++;
            }});
        Rock3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int odd = random.nextInt(10) + 1;
                if (odd == 1)
                    nCoins++;
            }});
        Rock4.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int odd = random.nextInt(10) + 1;
                if (odd == 1)
                    nCoins++;
            }});
        Rock5.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int odd = random.nextInt(10) + 1;
                if (odd == 1)
                    nCoins++;
            }});
    }
}