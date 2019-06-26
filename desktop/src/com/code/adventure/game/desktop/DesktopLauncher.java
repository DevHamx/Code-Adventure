package com.code.adventure.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.code.adventure.game.CodeAdventureGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Code Adventure";
		config.addIcon("images/icon.png", Files.FileType.Internal);
		new LwjglApplication(new CodeAdventureGame(), config);
	}
}
