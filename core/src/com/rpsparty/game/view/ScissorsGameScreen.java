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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rpsparty.game.RPSParty;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.rpsparty.game.controller.MatchController;
import com.rpsparty.game.controller.ScissorGameController;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Vector;

import static com.badlogic.gdx.utils.Align.center;
import static com.badlogic.gdx.utils.Align.left;


public class ScissorsGameScreen extends ScreenAdapter{
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
    /**
     * The animation
     */
    private Animation<TextureRegion> animation;
    private Texture animationTexture;
    private Sprite sprite;
    private Texture semiCircle;
    private ShapeRenderer quad = new ShapeRenderer();

    private Map<Integer,Vector<Integer>> positions =new HashMap<Integer,Vector<Integer>>();
    private LinkedHashSet<Integer> hashSet = new LinkedHashSet<Integer>();
    private int circleSize;
    private float timeToPlay = 5;//5 segundos para o mini-jogo
    private float stateTime = 0;
    private Integer timerValue = 5;
    private Label timer;
    private Stage stage;

    public ScissorsGameScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();
        addLabel();
        circleSize = ScissorGameController.getInstance().getCircleSize();
        setSemiCircleTexture();

        createAnimation();
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "bigSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "mediumSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "smallSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "extrasmallSemiCirc.png" , Texture.class);
        this.game.getAssetManager().load( "scissorsAnimation.png" , Texture.class);
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

    public void addLabel() {
        stage = new Stage();
        Label.LabelStyle style = new Label.LabelStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mf I Love Glitter.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (Gdx.graphics.getHeight()/15);
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.BLACK;
        timer = new Label(Integer.toString(timerValue), style);
        timer.setBounds(4*Gdx.graphics.getWidth()/6, 12*Gdx.graphics.getHeight()/16,Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/8);
        timer.setAlignment(center);
        stage.addActor(timer);
    }

    public void createAnimation() {
        animationTexture = game.getAssetManager().get("scissorsAnimation.png");
        // Split the texture into frame
        TextureRegion[][] thrustRegion = TextureRegion.split(animationTexture, animationTexture.getWidth()/2, animationTexture.getHeight());

        // Put the frames into a uni-dimensional array
        TextureRegion[] frames = new TextureRegion[2];
        System.arraycopy(thrustRegion[0], 0, frames, 0, 2);
        // Create the animation
        animation = new Animation<TextureRegion>(.25f, frames);
        sprite = new Sprite(animation.getKeyFrame(0));

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
        game.getBatch().setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        updateTime(delta);

        if(timeToPlay <= 0) {
            //fim do mini-jogo
            if(!playAnimation(delta)) {//a animacao ja foi reproduzida
                ScissorGameController.getInstance().finalResult();
                boolean tie = ScissorGameController.getInstance().isTie();
                ScissorGameController.getInstance().reset();
                this.dispose();
                if(tie) {
                    game.setScreen(new GameScreen(game));
                } else {
                    game.setScreen(new MiniGameEndScreen(game, 2, MatchController.getInstance().getLastResult()));
                }
                return;
            }
            drawLine();
            game.getBatch().begin();
            game.getBatch().draw(semiCircle, Gdx.graphics.getWidth()/2 - semiCircle.getWidth(), Gdx.graphics.getHeight()/2-semiCircle.getHeight()/2);
            //sprite.draw(game.getBatch());
            game.getBatch().end();

            game.getBatch().setProjectionMatrix(camera.combined);
            game.getBatch().begin();
            sprite.draw(game.getBatch());
            game.getBatch().end();
        } else {
            stage.draw();
            saveFingerPosition();
            drawLine();
            game.getBatch().begin();
            game.getBatch().draw(semiCircle, Gdx.graphics.getWidth()/2 - semiCircle.getWidth(), Gdx.graphics.getHeight()/2-semiCircle.getHeight()/2);
            game.getBatch().end();

        }

        goBack();
    }

    public void updateTime(float delta) {
        stateTime += delta;
        timeToPlay -= delta;
        timerValue = Math.round(timeToPlay);
        timer.setText(Integer.toString(timerValue));
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

    public boolean playAnimation(float delta) {

        if(ScissorGameController.getInstance().setScissorPosition(delta)) {
            sprite.setRegion(animation.getKeyFrame(stateTime, true));
            float spriteOriginX = sprite.getOriginX();
            float spriteOriginY = sprite.getOriginY();

            sprite.setPosition((float)ScissorGameController.getInstance().getScissorPosition()[0]-spriteOriginX,(float)ScissorGameController.getInstance().getScissorPosition()[1]-spriteOriginY);
            sprite.rotate((float)(ScissorGameController.getInstance().getScissorAng()*180/Math.PI));
            return true;
        }
        return false;

    }

    public void saveFingerPosition() {
        if(Gdx.input.getX() < Gdx.graphics.getWidth()/2)//so desenhar de metade do ecra para a direita
            return;
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        Integer x = Math.round(touchPos.x);
        Integer y = Math.round(touchPos.y);

        if (hashSet.add(y)) {//primeira vez que o dedo passa no ecra na altura y
            if(positions.get(x) != null) {//x ja existe no map
                positions.get(x).add(y);
            } else {
                Vector<Integer> v =new Vector<Integer>();
                v.add(y);
                positions.put(x, v);
            }
            ScissorGameController.getInstance().setMyPoints(Gdx.input.getX(), Gdx.input.getY());
        }

    }

    public void drawLine() {
        camera.update();
        quad.setProjectionMatrix(camera.combined);
        quad.begin(ShapeType.Filled);
        quad.setColor(0, 0, 0, 1);

        for(Map.Entry<Integer, Vector<Integer>> entry : positions.entrySet()) {
            for(Integer y : entry.getValue()) {
                quad.rect(entry.getKey(), y, 3, 3);
            }
        }
        quad.end();
    }

    public void setSemiCircleTexture() {
        switch (circleSize) {
            case 0:
                semiCircle = game.getAssetManager().get("bigSemiCirc.png");
                ScissorGameController.getInstance().setRadius(semiCircle.getWidth());
                break;
            case 1:
                semiCircle = game.getAssetManager().get("mediumSemiCirc.png");
                ScissorGameController.getInstance().setRadius(semiCircle.getWidth());
                break;
            case 2:
                semiCircle = game.getAssetManager().get("smallSemiCirc.png");
                ScissorGameController.getInstance().setRadius(semiCircle.getWidth());
                break;
            case 3:
                semiCircle = game.getAssetManager().get("extrasmallSemiCirc.png");
                ScissorGameController.getInstance().setRadius(semiCircle.getWidth());
                break;
            default:
                break;
        }
    }


}