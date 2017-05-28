package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rpsparty.game.RPSParty;
import com.badlogic.gdx.Input.Keys;
import com.rpsparty.game.controller.MatchController;
import com.rpsparty.game.controller.PaperGameController;
import com.rpsparty.game.view.entities.PaperGameActor;

import static com.badlogic.gdx.utils.Align.center;
import static com.badlogic.gdx.utils.Align.left;

public class PaperGameScreen extends ScreenAdapter {
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
    private Actor paper;
    private float timeToPlay;
    private Texture paperTexture;
    private Sprite paperSprite;
    private Animation<TextureRegion> rolling;
    private Sprite sprite;
    private float stateTime;
    private Integer timerValue = 30;
    private Label timer;
    private Label points;

    public PaperGameScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();
        stage = new Stage();
        addActors();
        addLabels();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(paper);
        paper.setTouchable(Touchable.enabled);
        createPaperAnimation();
        timeToPlay = 30;
        stateTime = 0;
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "paperanime.png" , Texture.class);
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

    public void addLabels() {
        Label.LabelStyle style = new Label.LabelStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mf I Love Glitter.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (Gdx.graphics.getHeight()/18);
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.BLACK;
        timer = new Label(Integer.toString(timerValue), style);
        timer.setBounds(4*Gdx.graphics.getWidth()/6, 14*Gdx.graphics.getHeight()/16,Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/8);
        timer.setAlignment(center);
        points = new Label("Points: "+Float.toString(0.0f), style);
        points.setBounds(0, 13*Gdx.graphics.getHeight()/16,3*Gdx.graphics.getWidth()/4,2*Gdx.graphics.getHeight()/12);
        points.setAlignment(left);
        stage.addActor(timer);
        stage.addActor(points);
    }

    private void createPaperAnimation(){
        paperTexture = game.getAssetManager().get("paperanime.png");
        TextureRegion[][] rollPaper = TextureRegion.split(paperTexture,paperTexture.getWidth()/5,paperTexture.getHeight());

        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(rollPaper[0],0,frames,0,5);
        rolling = new Animation<TextureRegion>(0.0f,frames);
        paperSprite = new Sprite(rolling.getKeyFrame(0));

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
        stage.act(delta);
        stage.draw();
        updateSprite();
        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(camera.combined);
        sprite.draw(game.getBatch());
        game.getBatch().end();
        goBack();
        updateTime(delta);
        if (timeToPlay < 0) {
            PaperGameController.getInstance().finalResult();
            PaperGameController.getInstance().reset();
            game.setScreen(new MiniGameEndScreen(game, 1, MatchController.getInstance().getSets().get(MatchController.getInstance().getSets().size()-1)));
        }
    }

    public void updateTime(float delta) {
        timeToPlay -= delta;
        stateTime += delta;
        timerValue = Math.round(timeToPlay);
        timer.setText(Integer.toString(timerValue));
        points.setText("Points: "+PaperGameController.getInstance().getMyPoints());
    }
    public void goBack() {
        if (Gdx.input.isKeyPressed(Keys.BACK)) {
            if (!game.backpressed) {
                game.backpressed = true;
            } else if (game.backpressed) {
                game.backpressed = false;
                Gdx.app.exit();
            }
        }
    }

    public void updateSprite(){
        rolling.setFrameDuration(PaperGameController.getInstance().getTimeToNextFrame());
        sprite.setRegion(rolling.getKeyFrame(stateTime,true));
    }

    public void addActors() {
        paper = new PaperGameActor();
        sprite = new Sprite(new Texture(Gdx.files.internal("Achieve.png")));
        sprite.setBounds(2*Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/8,3*Gdx.graphics.getWidth()/8,2*Gdx.graphics.getHeight()/8);
    }
}