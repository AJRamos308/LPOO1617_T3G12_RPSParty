package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.ConnectionSockets;
import com.rpsparty.game.controller.MatchController;
import com.rpsparty.game.model.MatchModel;
import com.rpsparty.game.model.entities.EntityModel;
import com.rpsparty.game.model.entities.PaperHandModel;
import com.rpsparty.game.model.entities.RockHandModel;
import com.rpsparty.game.model.entities.ScissorHandModel;
import com.rpsparty.game.view.entities.EntityView;
import com.rpsparty.game.view.entities.PaperButton;
import com.rpsparty.game.view.entities.PaperView;
import com.rpsparty.game.view.entities.ResultsActor;
import com.rpsparty.game.view.entities.RockButton;
import com.rpsparty.game.view.entities.RockView;
import com.rpsparty.game.view.entities.ScissorView;
import com.rpsparty.game.view.entities.ScissorsButton;

import static com.badlogic.gdx.Gdx.graphics;

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
    private ResultsActor resultsActor;
    private Texture areYouReady;

    public GameScreen(RPSParty game) {
        System.out.println("\nmudou para GameScreen\n");
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
        stage.addActor(resultsActor);
        areYouReady = new Texture(Gdx.files.internal("areuready.png"));
        MatchController.getInstance().createReadThread();

    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "areuready.png" , Texture.class);
        this.game.getAssetManager().load( "paper.png" , Texture.class);
        this.game.getAssetManager().load( "rock.png" , Texture.class);
        this.game.getAssetManager().load( "scissor.png" , Texture.class);
        this.game.getAssetManager().load( "check.png" , Texture.class);
        this.game.getAssetManager().load( "x.png" , Texture.class);
        this.game.getAssetManager().finishLoading();
    }
    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        float ratio = ((float) graphics.getHeight() / (float) graphics.getWidth());
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
        if(MatchController.getInstance().getMyChoice() == "") {
            enableButtons();
            game.getBatch().begin();
            game.getBatch().setProjectionMatrix(camera.combined);
            //drawMatchResults();
            stage.draw();
            game.getBatch().end();
        }

        goBack();

        //game.setScreen(new MainMenuScreen(game));
        if(MatchController.getInstance().getMyChoice() != "" && MatchController.getInstance().getOpponentChoice() != "") {
            if (MatchController.getInstance().finalResult()) {
                MatchController.getInstance().update(delta);
                if (!MatchController.getInstance().isCollision()) {
                    game.getBatch().begin();
                    drawAnimation();
                    game.getBatch().end();
                } else {
                    if (!MatchController.getInstance().resetMatch(delta)) {//se ainda nao passaram os 3 segundos depois da colisao...
                        game.getBatch().begin();
                        drawAnimation();//...continua a desenhar a animacao
                        game.getBatch().end();
                    } else {

                        MatchController.getInstance().createReadThread();
                    }
                }
            } else {
                game.getBatch().begin();
                game.getBatch().draw(areYouReady, graphics.getWidth() / 2, graphics.getHeight() / 2);
                game.getBatch().end();
                MatchController.getInstance().shakeUpdate(delta);
            }
        }

    }

    public void goBack() {
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
        scissorsButton = new ScissorsButton(game);
        paperButton = new PaperButton(game);
        resultsActor = new ResultsActor();
    }

    public void disableButtons() {
        rockButton.setTouchable(Touchable.disabled);
        scissorsButton.setTouchable(Touchable.disabled);
        paperButton.setTouchable(Touchable.disabled);
    }

    public void enableButtons() {
        rockButton.setTouchable(Touchable.enabled);
        scissorsButton.setTouchable(Touchable.enabled);
        paperButton.setTouchable(Touchable.enabled);
    }

    public void addListeners() {
        rockButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
               // paperButton.setBounds(6* graphics.getWidth()/16, graphics.getWidth()/16, graphics.getWidth()/4, graphics.getWidth()/4);
               // scissorsButton.setBounds(11* graphics.getWidth()/16, graphics.getWidth()/16, graphics.getWidth()/4, graphics.getWidth()/4);
                System.out.println("ROCK");

                ConnectionSockets.getInstance().sendMessage("rock" + ("\n"));
                MatchController.getInstance().setMyChoice("rock");
                MatchController.getInstance().chooseRock();
              //  rockButton.setBounds(graphics.getWidth()/8, 4* graphics.getWidth()/8, 6* graphics.getWidth()/8, 6* graphics.getWidth()/8);

                disableButtons();
            }});
        scissorsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
              //  paperButton.setBounds(6* graphics.getWidth()/16, graphics.getWidth()/16, graphics.getWidth()/4, graphics.getWidth()/4);
              //  rockButton.setBounds(graphics.getWidth()/16, graphics.getWidth()/16, graphics.getWidth()/4, graphics.getWidth()/4);
                System.out.println("SCISSORS");

                ConnectionSockets.getInstance().sendMessage("scissor" + ("\n"));
                MatchController.getInstance().setMyChoice("scissor");
                MatchController.getInstance().chooseScissor();
              //  scissorsButton.setBounds(graphics.getWidth()/8, 4* graphics.getWidth()/8, 6* graphics.getWidth()/8, 6* graphics.getWidth()/8);

                disableButtons();
            }});
        paperButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
              //  scissorsButton.setBounds(11* graphics.getWidth()/16, graphics.getWidth()/16, graphics.getWidth()/4, graphics.getWidth()/4);
              //  rockButton.setBounds(graphics.getWidth()/16, graphics.getWidth()/16, graphics.getWidth()/4, graphics.getWidth()/4);
                System.out.println("PAPER");

                ConnectionSockets.getInstance().sendMessage("paper" + ("\n"));
                MatchController.getInstance().setMyChoice("paper");
                MatchController.getInstance().choosePaper();
              //  paperButton.setBounds(graphics.getWidth()/8, 4* graphics.getWidth()/8, 6* graphics.getWidth()/8, 6* graphics.getWidth()/8);

                disableButtons();
            }});
    }

    public void drawAnimation() {
        EntityModel player1Choice = MatchModel.getInstance().getPlayer1Entity();
        EntityModel player2Choice = MatchModel.getInstance().getPlayer2Entity();

        EntityView view1 = null;

        if(player1Choice instanceof RockHandModel) {
            view1 = new RockView(game);
        } else if(player1Choice instanceof PaperHandModel) {
            view1 = new PaperView(game);
        } if(player1Choice instanceof ScissorHandModel) {
            view1 = new ScissorView(game);
        }
        view1.update(player1Choice);
        view1.draw(game.getBatch());

        EntityView view2 = null;

        if(player2Choice instanceof RockHandModel) {
            view2 = new RockView(game);
        } else if(player2Choice instanceof PaperHandModel) {
            view2 = new PaperView(game);
        } if(player2Choice instanceof ScissorHandModel) {
            view2 = new ScissorView(game);
        }
        view2.update(player2Choice);
        view2.draw(game.getBatch());
    }
}




