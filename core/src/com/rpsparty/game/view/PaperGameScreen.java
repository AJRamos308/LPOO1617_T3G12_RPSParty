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

    private final RPSParty game;
    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;
    private Stage stage;
    private Actor paper;
    private float timeToPlay;
    private Texture paperTexture;
    private Animation<TextureRegion> rolling;
    private Sprite sprite;
    private float stateTime;
    private Integer timerValue = 30;
    private Label timer;
    private Label points;
    /**
     * PaperGameScreen's constructor.
     *
     * @param game
     */
    public PaperGameScreen(RPSParty game) {
        this.game = game;
        camera = createCamera();
        stage = new Stage();
        createPaperAnimation();
        addActors();
        addLabels();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(paper);
        paper.setTouchable(Touchable.enabled);
        timeToPlay = 30;
        stateTime = 0;
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
     * Creates the labels and its fonts.
     */
    public void addLabels() {
        Label.LabelStyle style = new Label.LabelStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (Gdx.graphics.getHeight()/18);
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.BLACK;
        timer = new Label(Integer.toString(timerValue), style);
        timer.setBounds(4*Gdx.graphics.getWidth()/6, 14*Gdx.graphics.getHeight()/16,Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/8);
        timer.setAlignment(center);
        points = new Label("Points: " + Float.toString(0.0f), style);
        points.setBounds(0, 13*Gdx.graphics.getHeight()/16,3*Gdx.graphics.getWidth()/4,2*Gdx.graphics.getHeight()/12);
        points.setAlignment(left);
        stage.addActor(timer);
        stage.addActor(points);
    }
    /**
     * Creates the paper rolling animation.
     */
    private void createPaperAnimation(){
        paperTexture = game.getAssetManager().get("paperanime.png");
        TextureRegion[][] rollPaper = TextureRegion.split(paperTexture,paperTexture.getWidth()/5,paperTexture.getHeight());

        TextureRegion[] frames = new TextureRegion[5];
        System.arraycopy(rollPaper[0],0,frames,0,5);
        rolling = new Animation<TextureRegion>(0.0f,frames);
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
        drawElements(delta);
        updateTime(delta);
        if (timeToPlay < 0) {
            PaperGameController.getInstance().finalResult();
            boolean tie = PaperGameController.getInstance().isTie();
            PaperGameController.getInstance().reset();
            if(tie) {
                game.setScreen(new GameScreen(game));
            } else {
                game.setScreen(new MiniGameEndScreen(game, 1, MatchController.getInstance().getLastResult()));
            }
        }
    }

    /**
     * Updates time variables and points.
     */
    public void updateTime(float delta) {
        timeToPlay -= delta;
        stateTime += delta;
        timerValue = Math.round(timeToPlay);
        timer.setText(Integer.toString(timerValue));
        points.setText("Points: "+PaperGameController.getInstance().getMyPoints());
    }

    /**
     * Draw all the screen elements
     * @param delta
     */
    public void drawElements(float delta) {
        stage.act(delta);
        stage.draw();
        updateSprite();
        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();
    }

    /**
     * Updates the rolling paper sprite.
     */
    public void updateSprite(){
        rolling.setFrameDuration(PaperGameController.getInstance().getTimeToNextFrame());
        sprite.setRegion(rolling.getKeyFrame(stateTime,true));
    }

    /**
     * Adds screen actors (timer, points and rolling paper).
     */
    public void addActors() {
        paper = new PaperGameActor();
        float width = paperTexture.getWidth()/5;
        float height = paperTexture.getHeight();
        sprite = new Sprite();
        sprite.setBounds(27*Gdx.graphics.getWidth()/64,Gdx.graphics.getHeight()/128,15*Gdx.graphics.getWidth()/32,3*Gdx.graphics.getHeight()/4);
    }
}