package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.ConnectionSockets;
import com.rpsparty.game.view.entities.HelpButton;
import com.badlogic.gdx.Input.Keys;
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

public class CreatePartyScreen extends ScreenAdapter {
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
    private HelpButton helpButton;
    private Label myIP;
    private String ipAddress;
    private boolean startGame;
    private Image background;

    public CreatePartyScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();

        startGame = false;
        addButtons();
        addListeners();
        addLabel();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);
       // stage.addActor(helpButton);
        stage.addActor(myIP);
        ipAddress = "";
        while(ipAddress.equals("")) {
            getIP();
        }
        System.out.println(ipAddress);
       // if (ipAddress.equals("")) System.out.println("Did not get IP");
        myIP.setText("YOUR IP:\n\n" + ipAddress);
        createThread();
        System.out.println("saiu do seu construtor de classe");
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "background.jpg" , Texture.class);
        this.game.getAssetManager().load( "badlogic.jpg" , Texture.class);
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

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        stage.act();
        game.getBatch().begin();
        stage.draw();
        game.getBatch().end();
        goBack();
        if(startGame) {
            this.dispose();
            game.setScreen(new GameScreen(game));
        }
    }

    public void goBack() {
        if (Gdx.input.isKeyPressed(Keys.BACK)) {
            game.backpressed = true;
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(true);
        }
    }

    public void addButtons() {
        background = new Image(new TextureRegion((Texture)game.getAssetManager().get("background.jpg")));
        background.setFillParent(true);
        helpButton = new HelpButton(game);
    }

    public void addLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mf I Love Glitter.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (Gdx.graphics.getHeight()/12);
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.BLACK;
        myIP = new Label("", style);
        myIP.setBounds(Gdx.graphics.getWidth()/4, 5*Gdx.graphics.getHeight()/16,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/4);
        myIP.setAlignment(center);
    }

    public void addListeners() {
        helpButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //TODO: fazer setScreen()
                System.out.println("HELP!");
            }});
    }
    /*
    *espera que um cliente se ligue ao nosso socket
    * e le o que o cliente escreve para o socket
     */
    public void createThread() {
        // Now we create a thread that will listen for incoming socket connections
        new Thread(new Runnable(){

            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                // 0 means no timeout.  Probably not the greatest idea in production!
                serverSocketHint.acceptTimeout = 0;

                // Create the socket server using TCP protocol and listening on 9021
                // Only one app can listen to a port at a time, keep in mind many ports are reserved
                // especially in the lower numbers ( like 21, 80, etc )
                ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 9021, serverSocketHint);
                System.out.println("criou um socket");
                Socket client = serverSocket.accept(null);//fica a espera que alguem se conecte
                ConnectionSockets.getInstance().setSocket(client);
                System.out.println("aceitou cliente");
                startGame = true;

            }
        }).start(); // And, start the thread running

    }

    public void getIP() {
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

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        ipAddress = new String("");
        for(String str:addresses)
        {
            if(!str.equals("127.0.0.1"))//nao escrever o IP "127.0.0.1" porque e o localhost (igual para qualquer pc)
                ipAddress = ipAddress + str + "\n";
        }
        System.out.println("meu ip: "+ipAddress);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

}
