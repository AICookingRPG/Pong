package com.mygdx.pong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pong.PongGame;
import com.mygdx.pong.pong.Ball;
import com.mygdx.pong.pong.LeftPaddle;
import com.mygdx.pong.pong.Paddle;
import com.mygdx.pong.pong.RightPaddle;

public class Game implements Screen {
	private SpriteBatch batch;
	private Texture texture;
	private Paddle Lpaddle, Rpaddle;
	private Ball ball1, ball2;
	private BitmapFont font, large_font;
	private int playerScore = 0, computerScore = 0;
	private Music music;


	@Override
	public void show() {
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("field.png"));
		Texture ballTexture = new Texture(Gdx.files.internal("ball.png"));
		ball1 = new Ball(Gdx.graphics.getWidth() / 2 - ballTexture.getWidth() / 2, Gdx.graphics.getHeight() / 2 - ballTexture.getHeight() / 2);
		ball2 = new Ball(Gdx.graphics.getWidth() / 2 - ballTexture.getWidth() / 2, Gdx.graphics.getHeight() / 2 - ballTexture.getHeight() / 2, false);
		Texture paddleTexture = new Texture(Gdx.files.internal("paddle.png"));
		Lpaddle = new LeftPaddle(80, Gdx.graphics.getHeight() / 2 - paddleTexture.getHeight() / 2);
		Rpaddle = new RightPaddle(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() / 2 - paddleTexture.getHeight() / 2, ball1, ball2);
		font = new BitmapFont(Gdx.files.internal("font/ubuntu.fnt"), Gdx.files.internal("font/ubuntu.png"), false);
		font.setColor(Color.ORANGE);
		large_font = new BitmapFont(Gdx.files.internal("font/white128.fnt"), Gdx.files.internal("font/white128_0.png"), false);
		large_font.setColor(Color.ORANGE);
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/snowfall.mp3"));
		music.setLooping(true);
		music.play();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateScore();

		if(playerScore > 6 || computerScore > 6){
			if(playerScore > 6){
				PongGame.player_wins += 1;
				((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new EndSplash(true));
			} else{
				PongGame.computer_wins += 1;
				((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new EndSplash(false));
			}
		}
		
		Lpaddle.update();
		Rpaddle.update();
		ball1.update(Lpaddle, Rpaddle);
		ball2.update(Lpaddle, Rpaddle);

		batch.begin();
		batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ball1.draw(batch);
		ball2.draw(batch);
		Lpaddle.draw(batch);
		Rpaddle.draw(batch);
		font.draw(batch, "SCORE " + Integer.toString(computerScore) + "  WINS " + Integer.toString(PongGame.computer_wins), Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 3 / 8, Gdx.graphics.getHeight() - 5);
		font.draw(batch, "SCORE " + Integer.toString(playerScore) + "  WINS " + Integer.toString(PongGame.player_wins), Gdx.graphics.getWidth() / 4 , Gdx.graphics.getHeight() - 5);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	private void updateScore() {
		int result1 = ball1.checkForRespawn();

		switch (result1) {
			case 0:
				computerScore += 1;
				break;
			case 1:
				playerScore += 1;
				break;
			default:
				break;
		}
		int result2 = ball2.checkForRespawn();
		switch (result2) {
			case 0:
				computerScore += 1;
				break;
			case 1:
				playerScore += 1;
				break;
			default:
				break;
		}
	}
	
	@Override
	public void hide() {
		music.stop();
		font.dispose();
		texture.dispose();
		music.dispose();
	}
	
	@Override
	public void dispose() {
		font.dispose();
		texture.dispose();
		music.dispose();
	}
}
