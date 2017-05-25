package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.rpsparty.game.RPSParty;
import com.badlogic.gdx.Input.Keys;
import com.rpsparty.game.controller.PaperGameController;
import com.rpsparty.game.view.entities.PaperGameActor;

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

    public PaperGameScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();
        addActors();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(paper);
        paper.setTouchable(Touchable.enabled);
        createPaperAnimation();
        timeToPlay = 120;
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
        timeToPlay -= delta;
        stateTime += delta;
        if (timeToPlay < 0) {
            PaperGameController.getInstance().finalResult();
            PaperGameController.getInstance().reset();
            game.setScreen(new GameScreen(game));
        }
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
