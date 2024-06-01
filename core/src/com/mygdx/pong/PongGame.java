package com.mygdx.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pong.screen.Splash;

public class PongGame extends Game{
	public static boolean menuInitialised = false;
	private SpriteBatch batch;
	public static int player_wins = 0;
	public static int computer_wins = 0;
	public static String player_name = "PLAYER";
	public static boolean vsync = false;

	@Override
	public void create() {
		batch = new SpriteBatch();
		Gdx.input.setCatchBackKey(true);
		setScreen(new Splash());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		super.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		menuInitialised = false;
		super.pause();
	}

	@Override
	public void resume() {
		menuInitialised = false;
		super.resume();
	}
	public SpriteBatch getBatch() {
		return batch;
	}
}