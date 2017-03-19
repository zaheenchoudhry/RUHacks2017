package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.Game;

import java.io.IOException;

public class MainActivity extends Game {

	private GameScreen gameScreen;
	
	@Override
	public void create () {
		try {
			gameScreen = new GameScreen(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
		gameScreen.dispose();
	}
}
