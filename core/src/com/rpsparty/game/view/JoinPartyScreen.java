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
import com.rpsparty.game.view.entities.HelpButton;
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
    private HelpButton helpButton;
    private TextField serverIP;
    private TextButton confirmInput;
    private boolean startGame;
    private Image background;

    public JoinPartyScreen(RPSParty game) {
        this.game = game;
        camera = createCamera();

        startGame = false;
        addButtons();
        addListenersButton();
        addTextArea();
        addTextButton();
        addListenersTextButton();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);
        //stage.addActor(helpButton);
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
        //OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio);
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

    public void goBack() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.backpressed = true;
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(true);
        }
    }

    public void addButtons() {
        background = new Image(new TextureRegion((Texture)game.getAssetManager().get("background.png")));
        background.setFillParent(true);
        helpButton = new HelpButton(game);
    }
    //TODO: Back Button
    public void addListenersButton() {
        helpButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //TODO: fazer setScreen()
                System.out.println("HELP!");
            }});
    }

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

    public void addListenersTextButton() {
        confirmInput.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                createThread();
            }});
    }

    public String getIP() {
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String ipAddress = new String("");
        for(String str:addresses)
        {
            if(!str.equals("127.0.0.1")) {//nao escrever o IP "127.0.0.1" porque e o localhost (igual para qualquer pc)
                ipAddress = str + "\n";
                return ipAddress;//envia o primeiro id valido que encontra
            }
        }
        return ipAddress;
    }

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
