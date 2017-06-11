package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.ConnectionSockets;
import com.rpsparty.game.view.entities.AchievementsButton;
import com.rpsparty.game.view.entities.CreatePartyButton;
import com.rpsparty.game.view.entities.HelpButton;
import com.rpsparty.game.view.entities.JoinPartyButton;
import com.rpsparty.game.view.entities.SettingsButton;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenuScreen extends ScreenAdapter {
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
    private Image background;
    private Stage stage;
    private CreatePartyButton createPartyButton;
    private JoinPartyButton joinPartyButton;
    private HelpButton helpButton;


    public MainMenuScreen(RPSParty game) {
        this.game = game;
        camera = createCamera();
        createButtons();
        addListeners();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        addButtons();
        resetSockets();
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
        game.backpressed = false;
        camera.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act();
        game.getBatch().begin();
        stage.draw();                                   game.getBatch().end();
        if (Gdx.input.isKeyPressed(Keys.BACK)) {
            if (!game.backpressed) {        game.backpressed = true;
            } else if (game.backpressed) {  game.backpressed = false;
                Gdx.app.exit();}}
    }

    /**
     * create screen buttons
     */
    public void createButtons() {
        createPartyButton = new CreatePartyButton(game);
        joinPartyButton = new JoinPartyButton(game);
        helpButton = new HelpButton(game);
        background = new Image(new TextureRegion((Texture)game.getAssetManager().get("background.png")));
        background.setFillParent(true);
    }

    /**
     * add buttons to the screen
     */
    public void addButtons() {
        stage.addActor(background);
        stage.addActor(createPartyButton);
        stage.addActor(joinPartyButton);
        stage.addActor(helpButton);
    }

    /**
     * add screen components listeners
     */
    public void addListeners() {
        createPartyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CreatePartyScreen(game));
                System.out.println("create party!");
            }});
        joinPartyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new JoinPartyScreen(game));
                System.out.println("join party!");
            }});
        helpButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("HELP!");
                game.setScreen(new InfoScreen(game));
            }});

    }

    /**
     * reset the previous sockets
     */
    public void resetSockets() {
        ConnectionSockets.getInstance().reset();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
}
