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
import static com.badlogic.gdx.Input.Keys.T;

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
        this.game.getAssetManager().load( "paperanime.png" , Texture.class);
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
        if(MatchController.getInstance().getMyChoice() == "") {//so desenhar butoes enquanto o utilizador nao escolher opcao
            enableButtons();
            game.getBatch().begin();
            game.getBatch().setProjectionMatrix(camera.combined);
            //drawMatchResults();
            stage.draw();
            game.getBatch().end();
        }

        goBack();

        //game.setScreen(new MainMenuScreen(game));
        if(MatchController.getInstance().arePlayersReady()) {
            processChoices(delta);
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
        resultsActor = new ResultsActor(game);
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

        EntityView view1 = getPlayerView(player1Choice);

        EntityView view2 = getPlayerView(player2Choice);

        game.getBatch().begin();
        view1.draw(game.getBatch());
        view2.draw(game.getBatch());
        game.getBatch().end();
    }

    public EntityView getPlayerView (EntityModel playerChoice) {
        EntityView view = null;

        if(playerChoice instanceof RockHandModel) {
            view = new RockView(game);
        } else if(playerChoice instanceof PaperHandModel) {
            view = new PaperView(game);
        } if(playerChoice instanceof ScissorHandModel) {
            view = new ScissorView(game);
        }
        view.update(playerChoice);

        return view;
    }



    public void processChoices(float delta) {
        if (MatchController.getInstance().finalResult()) {//ainda nao deram os 3 shakes; ainda nao se pode mostrar o resultado
            startAnimation(delta);
        } else {
            game.getBatch().begin();
            game.getBatch().draw(areYouReady, graphics.getWidth() / 2, graphics.getHeight() / 2);
            game.getBatch().end();
            MatchController.getInstance().shakeUpdate(delta);
        }
    }


    public void startAnimation(float delta) {
        MatchController.getInstance().update(delta);
        if (!MatchController.getInstance().isCollision()) {
            drawAnimation();
        } else {
            if (!MatchController.getInstance().resetMatch(delta)) {//se ainda nao passaram os 3 segundos depois da colisao...
                drawAnimation();//...continua a desenhar a animacao
            } else {
                if(MatchController.getInstance().isTie()) {
                    String element = MatchController.getInstance().getMyChoice();
                    MatchController.getInstance().resetChoices();
                    playMiniGame(element);
                } else {
                    MatchController.getInstance().resetChoices();
                    System.out.println("vai criar thread");
                    MatchController.getInstance().createReadThread();
                }
            }
        }
    }

    public void playMiniGame(String element) {
        if(element.equals("rock")) {
            game.setScreen(new RockGameScreen(game));
        } else if(element.equals("paper")) {
            game.setScreen(new PaperGameScreen(game));
        } else if(element.equals("scissor")) {
            game.setScreen(new ScissorsGameScreen(game));
        }
    }
}




