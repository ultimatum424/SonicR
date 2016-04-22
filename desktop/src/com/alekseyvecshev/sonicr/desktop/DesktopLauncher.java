package com.alekseyvecshev.sonicr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alekseyvecshev.sonicr.SonicRGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = SonicRGame.HEIGHT;
		config.width = SonicRGame.WIDTH;
		config.title = SonicRGame.TITLE;
		new LwjglApplication(new SonicRGame(), config);
	}
}
