package com.rpsparty.game;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rpsparty.game.view.CreatePartyScreen;
import com.rpsparty.game.view.JoinPartyScreen;
import com.rpsparty.game.view.MainMenuScreen;

public class AndroidLauncher extends AndroidApplication {
	public RPSParty game;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		game = new RPSParty();
		initialize(game, config);
	}
	@Override
	public void onBackPressed() {
		game.setScreen(new MainMenuScreen(game));
		return;
	}
}
