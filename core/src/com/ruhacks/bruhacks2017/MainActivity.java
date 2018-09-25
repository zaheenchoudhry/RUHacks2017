package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.Game;

import java.io.IOException;

public class MainActivity extends Game {

	private GameScreen gameScreen;
	private LeapClient leap;
	@Override
	public void create() {
		try {
			gameScreen = new GameScreen(this);
			leap = new LeapClient(gameScreen);
			leap.start();
			setScreen(gameScreen);
		} catch (Exception ex) {

		}
	}

	public void setNewGameScreen() {
		gameScreen.dispose();
		gameScreen = new GameScreen(this);
		leap.setGameScreen(gameScreen);
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
