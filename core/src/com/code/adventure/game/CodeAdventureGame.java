package com.code.adventure.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.code.adventure.game.util.Assets;

public class CodeAdventureGame extends Game {
	@Override
	public void create () {
		AssetManager am = new AssetManager();
		Assets.instance.init(am);
		setScreen(new MainMenuScreen(this));
		//setScreen(new QuizScreen(this));
	}
}
