package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.ConnectionSockets;
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

import static javax.swing.text.html.HTML.Tag.HEAD;

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
    private String myChoice;
    private String opponentChoice;
    private Texture areYouReady;

    private float lastUpdate =0.0f;
    private float lastX = 0.0f;
    private int shakeStage = 0; // 0 = X do telemovel esta a aumentar ; 1 = X do telemovel esta a diminuir
    private int updateTime = 1; // 1 = primeiro update de inicio de um shake ; 2 = segundo update de inicio de um shake
    private int flagDirection = 2; // 0 = shake aumenta no  sentido positivo de X ; 1 = shake aumenta no  sentido negativo de X ; 2 = ainda não se sabe o sentido
    private int count = 3;//numero de shakes a dar


    public GameScreen(RPSParty game) {
        System.out.println("\nmudou para p GameScreen\n");
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
        areYouReady = new Texture(Gdx.files.internal("areuready.png"));
        myChoice = "";
        opponentChoice = "";
        createReadThread();

    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "scissors.png" , Texture.class);
        this.game.getAssetManager().load( "areuready.png" , Texture.class);
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
        if(myChoice == "") {
            game.getBatch().begin();
            game.getBatch().setProjectionMatrix(camera.combined);
            stage.draw();
            game.getBatch().end();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            if (!game.backpressed) {
                game.backpressed = true;
            } else if (game.backpressed) {
                game.backpressed = false;
                Gdx.app.exit();
            }
        }
        if(myChoice != "" && opponentChoice != "") {
            //game.setScreen(new MainMenuScreen(game));
            if(count == 0) {
                /*
                *mostrar o resultado deste match
                 */
            }
            game.getBatch().begin();
            game.getBatch().draw(areYouReady,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
            game.getBatch().end();
            lastUpdate += delta;
            if(lastUpdate > 0.25f) {
                if(Math.abs(lastX - Gdx.input.getAccelerometerX()) > 3) {
                    if (verifyDirectionShake()) {
                        if (shake()) {
                            System.out.println("SHAKE!");
                            count--;
                        }
                    }
                }
                lastUpdate = 0;
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

                ConnectionSockets.getInstance().sendMessage("rock" + ("\n"));
                myChoice = "rock";
                rockButton.setBounds(Gdx.graphics.getWidth()/8, 4*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8);
                rockButton.setDisabled(true);
                rockButton.setTouchable(Touchable.disabled);
            }});
        scissorsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                paperButton.setBounds(6*Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                rockButton.setBounds(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                System.out.println("SCISSORS");

                ConnectionSockets.getInstance().sendMessage("scissor" + ("\n"));
                myChoice = "scissor";
                scissorsButton.setBounds(Gdx.graphics.getWidth()/8, 4*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8);
                scissorsButton.setDisabled(true);
                scissorsButton.setTouchable(Touchable.disabled);
            }});
        paperButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                scissorsButton.setBounds(11*Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                rockButton.setBounds(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
                System.out.println("PAPER");

                ConnectionSockets.getInstance().sendMessage("paper" + ("\n"));
                myChoice = "paper";
                paperButton.setBounds(Gdx.graphics.getWidth()/8, 4*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8, 6*Gdx.graphics.getWidth()/8);
                paperButton.setDisabled(true);
                paperButton.setTouchable(Touchable.disabled);
            }});
    }

    public boolean waitForOpponent() {
        System.out.println("\nESTA NO WAIT FOR OPPONENT|\n");
        opponentChoice = ConnectionSockets.getInstance().receiveMessage();
        System.out.println("consegui ler?...");
        System.out.println(opponentChoice);
        if(opponentChoice != "") {
            return true;
        }
        return false;
    }

    public void createReadThread() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                while(!waitForOpponent());
                while(myChoice == "");

            }
        }).start();
    }

    public boolean shake() {
        float currX = Gdx.input.getAccelerometerX();
        System.out.println("\n\nX: "+Gdx.input.getAccelerometerX()+"\n\n");
        if(shakeStage == 0) {//primeira parte do shake
            System.out.println("no shake stage 0");
            if(((flagDirection == 0) && (currX < lastX)) || ((flagDirection == 1) && (currX > lastX))) {//um shake
                System.out.println("transicao da direcao");
                lastX = currX;
                shakeStage = 1;
                return true;
            }
        } else {
            System.out.println("no shake stage 1");
            if(((flagDirection == 0) && (currX > lastX)) || ((flagDirection == 1) && (currX < lastX))) {
                lastX = updateLastX();
                shakeStage = 0;
                return false;
            }
        }
        lastX = updateLastX();
        return false;
    }

    public boolean verifyDirectionShake() {
        if(updateTime > 2) {
            return true;
        }
        if(flagDirection == 2) {
            if(updateTime == 1) {//primeira vez no render
                System.out.println("primeira vez a verificar a direcao");
                lastX = Gdx.input.getAccelerometerX();
                updateTime++;
            } else {//ja da para concluir a direcao do shake
                System.out.println("vamos concluir a direcao...");
                if(Gdx.input.getAccelerometerX() > lastX) {
                    System.out.println("aumenta no sentido positivo");
                    flagDirection = 0;
                } else {
                    System.out.println("aumenta no sentido negativo");
                    flagDirection = 1;
                }
                updateTime++;
                return true;
            }
        }
        return false;
    }

    public float updateLastX() {
        float currX = Gdx.input.getAccelerometerX();
        if(Math.abs(lastX - currX) > 3) {
            return currX;
        }
        return lastX;
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

