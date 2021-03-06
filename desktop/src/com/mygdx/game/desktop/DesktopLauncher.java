package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Incident;

class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1366;
		config.height = 768;
		config.samples = 8;
		config.vSyncEnabled = true;
		config.resizable = false;
		//config.fullscreen = true;
		new LwjglApplication(new Incident(), config);
	}
}
