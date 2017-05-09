package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.MatchController;
import com.rpsparty.game.view.entities.AchievementsButton;
import com.rpsparty.game.view.entities.CreatePartyButton;
import com.rpsparty.game.view.entities.HelpButton;
import com.rpsparty.game.view.entities.JoinPartyButton;
import com.rpsparty.game.view.entities.MatchStage;
import com.rpsparty.game.view.entities.PaperButton;
import com.rpsparty.game.view.entities.RockButton;
import com.rpsparty.game.view.entities.ScissorsButton;
import com.rpsparty.game.view.entities.SettingsButton;

public class GameScreen extends ScreenAdapter {
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
    private PaperButton paperButton;
    private ScissorsButton scissorsButton;
    private RockButton rockButton;

    public GameScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();
        addButtons();
        addListeners();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(paperButton);
        stage.addActor(scissorsButton);
        stage.addActor(rockButton);
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "scissors.png" , Texture.class);
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
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            if (!game.backpressed) {
                game.backpressed = true;
            } else if (game.backpressed) {
                game.backpressed = false;
                Gdx.app.exit();
            }
        }
    }

    public void addButtons() {
        rockButton = new RockButton(game);
        scissorsButton = new ScissorsButton(game);
        paperButton = new PaperButton(game);
    }

    public void addListeners() {
        rockButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                paperButton.setBounds(6*Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                scissorsButton.setBounds(11*Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                System.out.println("ROCK");
                rockButton.setBounds(Gdx.graphics.getWidth()/8, 4*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8);
            }});
        scissorsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                paperButton.setBounds(6*Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                rockButton.setBounds(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                System.out.println("SCISSORS");
                scissorsButton.setBounds(Gdx.graphics.getWidth()/8, 4*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8);
            }});
        paperButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                scissorsButton.setBounds(11*Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                rockButton.setBounds(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                System.out.println("PAPER");
                paperButton.setBounds(Gdx.graphics.getWidth()/8, 4*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8);
            }});
    }
/*
    public final static float PIXEL_TO_METER = 0.04f;

    private final RPSParty game;
    private MatchStage matchStage;
    private Stage stage;
    /**
     * The camera used to show the viewport.
     */
  /*  private final OrthographicCamera camera;
    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
  /*  private static final float VIEWPORT_WIDTH = 20;
    private HelpButton helpButton;



    public GameScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();
        addButtons();
        addListeners();
        matchStage = new MatchStage(game);
        Gdx.input.setInputProcessor(matchStage);
        matchStage.addActor(helpButton);
    }
    /**
     * Loads the assets needed by this screen.
     */
  /*  private void loadAssets() {
        this.game.getAssetManager().load( "badlogic.jpg" , Texture.class);
        this.game.getAssetManager().finishLoading();
    }
    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
/*    private OrthographicCamera createCamera() {
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
 /*   @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        matchStage.act(delta);
        game.getBatch().begin();
        matchStage.draw();
        game.getBatch().end();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.backpressed = true;
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(true);
        }
    }

    public void addButtons() {
        helpButton = new HelpButton(game);
    }
    public void addListeners() {
        helpButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //TODO: fazer setScreen()
                System.out.println("HELP!");
            }});
    }*/

}

