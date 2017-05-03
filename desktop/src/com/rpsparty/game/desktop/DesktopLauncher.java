package com.rpsparty.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rpsparty.game.RPSParty;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.title = "hello-world";
		//config.useGL20 = false;
		config.width = 576;
		config.height = 1024;
		new LwjglApplication(new RPSParty(), config);
	}
}
