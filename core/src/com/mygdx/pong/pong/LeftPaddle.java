package com.mygdx.pong.pong;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class LeftPaddle extends Paddle {
	public static float SPEED = 400;

	public LeftPaddle(float x, float y) {
		super(x, y);
	}

	@Override
	public void update() {
		float delta = Gdx.graphics.getDeltaTime();
		if(Gdx.app.getType() == ApplicationType.Desktop)
			inputDesktop(delta);
		else if(Gdx.app.getType() == ApplicationType.Android)
			inputAndroid(delta);
	}
	
	private void inputDesktop(float delta) {
		if(Gdx.input.isKeyPressed(Keys.W)) {
			if(ballHitAbove())
				hitBox.y = Gdx.graphics.getHeight() - texture.getHeight();
			else
				hitBox.y = hitBox.y + SPEED * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			if(ballHitBelow())
				hitBox.y = 0;
			else
				hitBox.y = hitBox.y - SPEED * delta;
		}
	}
	
	private void inputAndroid(float delta) {
		float paddleY = hitBox.y + texture.getHeight() / 2;
		if(Gdx.input.isTouched()) {
			float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
			if(paddleY >= touchY - 5 && paddleY <= touchY + 5)
				touchY = paddleY;
			if(touchY < paddleY) {
				if(ballHitBelow())
					hitBox.y = 0;
				else
					hitBox.y = hitBox.y - SPEED * delta;
			}
			if(touchY > paddleY) {
				if(ballHitAbove())
					hitBox.y = Gdx.graphics.getHeight() - texture.getHeight();
				else
					hitBox.y = hitBox.y + SPEED * delta;
			}
		}
	}
}
