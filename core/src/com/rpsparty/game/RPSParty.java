package com.rpsparty.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.rpsparty.game.view.SplashScreen;

public class RPSParty extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;
	public boolean backpressed = false;
	private int bestOf;
    private ServerSocket server;
	/**
	 * Creates the game. Initializes the sprite batch and asset manager.
	 * Also starts the game until we have a main menu.
	 */
	@Override
	public void create () {
        server = null;
        bestOf = 0;
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		startGame();
	}

	/**
	 * Starts the game.
	 */
	private void startGame() {
		setScreen(new SplashScreen(this));
	}

	/**
	 * Disposes of all assets.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	/**
	 * Returns the asset manager used to load all textures and sounds.
	 *
	 * @return the asset manager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Returns the sprite batch used to improve drawing performance.
	 *
	 * @return the sprite batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * changes the bestOf value (numer of game sets)
	 * @param value
     */
	public void setBestOf(int value) { bestOf = value; }

	/**
	 * get the bestOf value (number of game sets)
	 * @return
     */
	public int getBestOf() { return bestOf; }
    /**
     * saves de server socket
     * @param socket
     */
    public void setServer(ServerSocket socket) { server = socket; }

    public void resetServer() {
        if(server != null) {
            server.dispose();
            server = null;
        }
    }
}