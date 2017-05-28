package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;
import com.badlogic.gdx.Input.Keys;
import com.rpsparty.game.controller.RockGameController;
import com.rpsparty.game.model.RockGameModel;
import com.rpsparty.game.model.entities.RockObjectGameModel;
import com.rpsparty.game.view.entities.RockGameButton;

import java.util.Random;

import static com.badlogic.gdx.utils.Align.center;
import static com.badlogic.gdx.utils.Align.left;

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
    private float timeToPlay = 30; //mini-jogo de 30 segundos
    private Integer timerValue = 30;
    private Label timer;
    private Label points;
    public RockGameScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();
        stage = new Stage();
        addButtons();
        addListeners();
        addLabel();
        Gdx.input.setInputProcessor(stage);
        addButtonsToStage();
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "paper.png" , Texture.class);
        this.game.getAssetManager().load( "rock.png" , Texture.class);
        this.game.getAssetManager().load( "scissor.png" , Texture.class);
        this.game.getAssetManager().finishLoading();
    }
    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        float ratio = ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        //OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio);
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    public void addButtonsToStage() {
        stage.addActor(Rock1);
        stage.addActor(Rock2);
        stage.addActor(Rock3);
        stage.addActor(Rock4);
        stage.addActor(Rock5);
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
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        RockGameController.getInstance().update(delta);

        changeButtonsStyle();
        updateButtons();
        stage.act();

        updateTime(delta);
        game.getBatch().begin();
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

        if(timeToPlay <= 0) {
            //fim do mini-jogo
            RockGameController.getInstance().finalResult();
            RockGameController.getInstance().reset();
            this.dispose();
            game.setScreen(new GameScreen(game));
        }

    }
    public void updateTime(float delta) {
        timeToPlay -= delta;
        timerValue = Math.round(timeToPlay);
        timer.setText(Integer.toString(timerValue));
        points.setText(Integer.toString(RockGameController.getInstance().getPoints()));
    }
    public void addLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mf I Love Glitter.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (Gdx.graphics.getHeight()/15);
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.BLACK;
        timer = new Label(Integer.toString(timerValue), style);
        timer.setBounds(4*Gdx.graphics.getWidth()/6, 14*Gdx.graphics.getHeight()/16,Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/8);
        timer.setAlignment(center);
        points = new Label("Points: "+Integer.toString(0),style);
        points.setBounds(0, 13*Gdx.graphics.getHeight()/16,3*Gdx.graphics.getWidth()/4,2*Gdx.graphics.getHeight()/12);
        points.setAlignment(left);
        stage.addActor(timer);
        stage.addActor(points);
    }

    public void addButtons() {
        Rock1 = new RockGameButton(game, RockGameModel.getInstance().getRockOne());
        Rock2 = new RockGameButton(game, RockGameModel.getInstance().getRockTwo());
        Rock3 = new RockGameButton(game, RockGameModel.getInstance().getRockThree());
        Rock4 = new RockGameButton(game, RockGameModel.getInstance().getRockFour());
        Rock5 = new RockGameButton(game, RockGameModel.getInstance().getRockFive());
    }

    public void addListeners() {
        rock1Listener();
        rock2Listener();
        rock3Listener();
        rock4Listener();
        rock5Listener();
    }
    public void rock1Listener() {
        Rock1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                RockGameController.getInstance().touchRockOne();
                System.out.println("TOQUE");
            }});
    }
    public void rock2Listener() {
        Rock2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                RockGameController.getInstance().touchRockTwo();
            }});
    }
    public void rock3Listener() {
        Rock3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                RockGameController.getInstance().touchRockThree();
            }});
    }
    public void rock4Listener() {
        Rock4.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                RockGameController.getInstance().touchRockFour();
            }});
    }
    public void rock5Listener() {
        Rock5.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                RockGameController.getInstance().touchRockFive();
            }});
    }
    public void changeButtonStyle (RockGameButton btn) {
        if(RockGameModel.getInstance().isDestroid(btn.getModel())) {
            btn.changeStyle();
        }
    }

    public void changeButtonsStyle () {
        changeButtonStyle(Rock1);
        changeButtonStyle(Rock2);
        changeButtonStyle(Rock3);
        changeButtonStyle(Rock4);
        changeButtonStyle(Rock5);

    }

    public void updateButton(RockGameButton btn) {
        if(RockGameController.getInstance().isButtonToUpdate(btn.getModel())) {
            btn.remove();

            if(btn.getModel().getNumber() == RockObjectGameModel.RockNumber.ONE) {
                Rock1 = new RockGameButton(game, RockGameModel.getInstance().getRockOne());
                rock1Listener();
                stage.addActor(Rock1);
            } else if(btn.getModel().getNumber() == RockObjectGameModel.RockNumber.TWO) {
                Rock2 = new RockGameButton(game, RockGameModel.getInstance().getRockTwo());
                rock2Listener();
                stage.addActor(Rock2);
            } else if(btn.getModel().getNumber() == RockObjectGameModel.RockNumber.THREE) {
                Rock3 = new RockGameButton(game, RockGameModel.getInstance().getRockThree());
                rock3Listener();
                stage.addActor(Rock3);
            } else if(btn.getModel().getNumber() == RockObjectGameModel.RockNumber.FOUR) {
                Rock4 = new RockGameButton(game, RockGameModel.getInstance().getRockFour());
                rock4Listener();
                stage.addActor(Rock4);
            } else if(btn.getModel().getNumber() == RockObjectGameModel.RockNumber.FIVE) {
                Rock5 = new RockGameButton(game, RockGameModel.getInstance().getRockFive());
                rock5Listener();
                stage.addActor(Rock5);
            }
        }

    }

    public void updateButtons() {
        for(Actor btn : stage.getActors()) {
            if(!(btn instanceof Label))
            updateButton((RockGameButton)btn);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
}