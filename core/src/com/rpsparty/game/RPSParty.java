package com.rpsparty.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.rpsparty.game.view.GameScreen;
import com.rpsparty.game.view.MainMenuScreen;

import com.badlogic.gdx.net.Socket;
import com.rpsparty.game.view.RockGameScreen;
import com.rpsparty.game.view.SplashScreen;

public class RPSParty extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;
	public boolean backpressed = false;

	/**
	 * Creates the game. Initializes the sprite batch and asset manager.
	 * Also starts the game until we have a main menu.
	 */
	@Override
	public void create () {
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

}