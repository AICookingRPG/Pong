package com.mygdx.pong.pong;

import com.badlogic.gdx.Gdx;

public class RightPaddle extends Paddle {
	private Ball ballA, ballB;
	public static float SPEED = 500;

	public RightPaddle(float x, float y, Ball ball1) {
		super(x, y);
		ballA = ball1;
	}
	public RightPaddle(float x, float y, Ball ball1, Ball ball2) {
		super(x, y);
		ballA = ball1;
		ballB = ball2;

	}

	@Override
	public void update() {
		if (ballB != null) {
			float delta = Gdx.graphics.getDeltaTime();
			float paddleY = hitBox.y + texture.getHeight() / 2;
			float x_corA = ballA.getHitBox().x;
			float x_corB = ballB.getHitBox().x;
			if (x_corA > x_corB) {
				updateCorrectBall(ballA);
			} else {
				updateCorrectBall(ballB);
			}
		} else {
			updateCorrectBall(ballA);
		}
	}
	public void updateCorrectBall(Ball ball){
		float delta = Gdx.graphics.getDeltaTime();
		float paddleY = hitBox.y + texture.getHeight() / 2;
		float ballY = ball.getHitBox().y + ball.getHitBox().getHeight() / 2;
		if (paddleY >= ballY - 10 && paddleY <= ballY + 10)
			ballY = paddleY;
		if (ballY < paddleY) {
			if (ballHitBelow())
				hitBox.y = 0;
			else
				hitBox.y = hitBox.y - SPEED * delta;
		}
		if (ballY > paddleY) {
			if (ballHitAbove())
				hitBox.y = Gdx.graphics.getHeight() - texture.getHeight();
			else
				hitBox.y = hitBox.y + SPEED * delta;
		}
	}
}
