package com.rpsparty.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsparty.game.RPSParty;
import com.rpsparty.game.controller.ConnectionSockets;
import com.rpsparty.game.view.entities.HelpButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.Net.Protocol;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.T;
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
    private static final float VIEWPORT_WIDTH = 20;
    private Stage stage;
    private HelpButton helpButton;
    private TextField serverIP;
    private TextButton confirmInput;
    private boolean startGame;

    public JoinPartyScreen(RPSParty game) {
        this.game = game;
        loadAssets();
        camera = createCamera();

        startGame = false;
        addButtons();
        addListenersButton();
        addTextArea();
        addTextButton();
        addListenersTextButton();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(helpButton);
        stage.addActor(serverIP);
        stage.addActor(confirmInput);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.backpressed = true;
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(true);
        }
    }
    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "badlogic.jpg" , Texture.class);
        this.game.getAssetManager().load( "cursor.png" , Texture.class);
        this.game.getAssetManager().load( "Selection.png" , Texture.class);
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
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        camera.update();
        stage.act();
        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(camera.combined);
        stage.draw();
        game.getBatch().end();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.backpressed = true;
            game.setScreen(new MainMenuScreen(game));
            Gdx.input.setCatchBackKey(true);
        }
        if(startGame) {
            game.setScreen(new GameScreen(game));
        }
    }

    public void addButtons() {
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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Bad Skizoff.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 150;
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        //style.font.getData().setScale(4f);
        style.fontColor = Color.BLACK;
        Skin skin = new Skin();
        skin.add("cursor", this.game.getAssetManager().get("cursor.png"));
        skin.add("Selection", this.game.getAssetManager().get("Selection.png"));
        style.cursor = skin.newDrawable("cursor");
        style.selection = skin.newDrawable("Selection");
        serverIP = new TextField("Server IP", style);
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

    public void addListenersTextArea() {

    }

    public void addTextButton() {
        TextButtonStyle style = new TextButtonStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Bad Skizoff.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 150;
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        style.font = font;
        style.fontColor = Color.BLACK;
        confirmInput = new TextButton("Join Party", style);
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
            if(!str.equals("127.0.0.1"))//nao escrever o IP "127.0.0.1" porque e o localhost (igual para qualquer pc)
                ipAddress = ipAddress + str + "\n";
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
                //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
                try {
                    System.out.println("o cliente vai se ligar ao IP "+serverIP.getText());
                    Socket socket = Gdx.net.newClientSocket(Protocol.TCP, serverIP.getText(), 9021, socketHints);
                    System.out.println("cliente esta ligado ao servidor!");
                    ConnectionSockets.getInstance().setWriteSocket(socket);
                    createServerSocket(socket);
                    System.out.println("cliente criou um socket e servidor ligou-se a ele!");
                    startGame = true;
                } catch (GdxRuntimeException e) {
                    System.out.println("Exception");
                    e.printStackTrace();
                }
            }}).start();
    }

    public boolean sendIP (Socket socket, String ip) {
        System.out.println("o nosso IP e "+ip);
        try {
            // write our entered message to the stream
            socket.getOutputStream().write(ip.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createServerSocket(Socket clientSocket) {
        System.out.println("o cliente vai criar tambem um socket");
        ServerSocketHints serverSocketHint = new ServerSocketHints();
        // 0 means no timeout.  Probably not the greatest idea in production!
        serverSocketHint.acceptTimeout = 0;

        ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 9022, serverSocketHint);
        while(!sendIP(clientSocket, getIP()));//enviar o nosso ip para criar outro socket (mas agora de forma escondida)
        System.out.println("o cliente enviou o seu IP para o servidor");
        Socket socket = serverSocket.accept(null);//fica a espera que alguem se conecte
        System.out.println("o servidor ligou-se ao cliente");
        ConnectionSockets.getInstance().setReadSocket(socket);
    }
}
