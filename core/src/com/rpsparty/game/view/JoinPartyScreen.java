package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.ConnectionSockets;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.Net.Protocol;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import static com.badlogic.gdx.utils.Align.center;

public class JoinPartyScreen extends ScreenAdapter {
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
    private Stage stage;
    private TextField serverIP;
    private TextButton confirmInput;
    private boolean startGame;
    private Image background;

    public JoinPartyScreen(RPSParty game) {
        this.game = game;
        camera = createCamera();

        startGame = false;
        addButtons();
        addTextArea();
        addTextButton();
        addListenersTextButton();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);
        stage.addActor(serverIP);
        stage.addActor(confirmInput);
    }
    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        float ratio = ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        camera.update();
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        stage.act();

        game.getBatch().begin();
        stage.draw();
        game.getBatch().end();
        goBack();
        if(startGame) {
            int bestOf = Integer.parseInt(ConnectionSockets.getInstance().receiveMessage());
            game.setBestOf(bestOf);
            game.setScreen(new GameScreen(game));
        }
    }

    /**
     * function to jump to MainMenuScreen
     */
    public void goBack() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.backpressed = true;
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(true);
        }
    }

    /**
     * add buttons to screen
     */
    public void addButtons() {
        background = new Image(new TextureRegion((Texture)game.getAssetManager().get("background.png")));
        background.setFillParent(true);
    }

    /**
     * add TextArea to insert the server IP
     */
    public void addTextArea() {
        TextFieldStyle style = new TextFieldStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (Gdx.graphics.getHeight()/13);
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.WHITE;
        Skin skin = new Skin();
        skin.add("cursor", this.game.getAssetManager().get("cursor.png"));
        skin.add("Selection", this.game.getAssetManager().get("Selection.png"));
        style.cursor = skin.newDrawable("cursor");
        style.selection = skin.newDrawable("Selection");
        serverIP = new TextField("SERVER IP", style);
        serverIP.setBounds(0, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
        serverIP.setTextFieldFilter(new TextField.TextFieldFilter() {
            // Accepts only numbers and dots
            public  boolean acceptChar(TextField textField, char c) {
                if ((c != '1') && (c != '2') && (c != '3') && (c != '4') && (c != '5') && (c != '6') && (c != '7') && (c != '8') && (c != '9') && (c != '0') && (c != '.'))
                    return false;
                return true;
            }
        });
        serverIP.setAlignment(center);
        serverIP.setMaxLength(15);
    }

    /**
     * add buttons to the screen
     */
    public void addTextButton() {
        TextButtonStyle style = new TextButtonStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (Gdx.graphics.getHeight()/13);
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.WHITE;
        confirmInput = new TextButton("JOIN PARTY", style);
        confirmInput.setY(Gdx.graphics.getHeight()/4);
        confirmInput.setX(Gdx.graphics.getWidth()/4);
        confirmInput.setWidth(Gdx.graphics.getWidth()/2);
        confirmInput.setHeight(Gdx.graphics.getHeight()/4);
    }

    /**
     * add button's listeners
     */
    public void addListenersTextButton() {
        confirmInput.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                createThread();
            }});
    }

    /**
     * create thread that will communicate with the server
     */
    public void createThread() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                System.out.println("Clicked the Button!\n");
                SocketHints socketHints = new SocketHints();
                // Socket will time our in 4 seconds
                socketHints.connectTimeout = 4000;
                int port = 9021;
                //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
                boolean connected = false;
                while(!connected) {
                    try {
                        System.out.println("o cliente vai se ligar ao IP " + serverIP.getText());
                        Socket socket = Gdx.net.newClientSocket(Protocol.TCP, serverIP.getText(), port, socketHints);
                        connected = true;
                        System.out.println("cliente esta ligado ao servidor!");
                        ConnectionSockets.getInstance().setSocket(socket);
                    } catch (GdxRuntimeException e) {
                        System.out.println("Exception");
                        e.printStackTrace();
                        port++;
                    }
                }
                startGame = true;
            }}).start();
    }


    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }



}
