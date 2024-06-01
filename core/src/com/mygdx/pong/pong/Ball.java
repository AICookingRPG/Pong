package com.mygdx.pong.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball {
	private int SPEED = 200;
	private final Texture texture;
	private final Rectangle hitBox;
	private int directionX, directionY;
	private float positionOriginalX, positionOriginalY;
	
	public Ball(float x, float y) {
		texture = new Texture(Gdx.files.internal("ball.png"));
		hitBox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
		directionX = directionY = 1;
		positionOriginalX = x;
		positionOriginalY = y;
		SPEED = (int) (100 + Math.random() * 400);
	}
	public Ball(float x, float y, boolean dir){
		texture = new Texture(Gdx.files.internal("ball.png"));
		hitBox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
		if (!dir) {
			directionX = directionY = -1;
		} else{
			directionX = directionY = 1;
		}
		positionOriginalX = x;
		positionOriginalY = y;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, hitBox.x, hitBox.y, texture.getWidth(), texture.getHeight());
	}
	
	public void update(Paddle Lpaddle, Paddle Rpaddle) {
		float delta = Gdx.graphics.getDeltaTime();
		if(borderHit())
			directionY = directionY * -1;
		if(paddleHit(Lpaddle.getHitBox(), Rpaddle.getHitBox())) {
			directionX = directionX * -1;
		}
		
		hitBox.x = hitBox.x + SPEED * delta * directionX;
		hitBox.y = hitBox.y + SPEED * delta * directionY;
	}
	
	private boolean borderHit() {
		if(hitBox.y + texture.getHeight() >= Gdx.graphics.getHeight()) {
			SPEED *= 1.02;
			SPEED += 10;
			hitBox.y = Gdx.graphics.getHeight() - texture.getHeight();
			return true;
		}
		else if(hitBox.y <= 0) {
			SPEED *= 1.02;
			SPEED += 10;
			hitBox.y = 0;
			return true;
		}
		else return false;
	}
	private boolean paddleHit(Rectangle Lpaddle, Rectangle Rpaddle) {
		if(hitBox.overlaps(Lpaddle)) {
			SPEED *= 1.02;
			SPEED += 10;
			hitBox.x = Lpaddle.x + Lpaddle.getWidth();
			return true;
		}
		else if(hitBox.overlaps(Rpaddle)) {
			SPEED *= 1.02;
			SPEED += 10;
			hitBox.x = Rpaddle.x - hitBox.getWidth();
			return true;
		}
		else return false;
	}
	
	public int checkForRespawn() {
		if(hitBox.x < 0) {
			hitBox.x = positionOriginalX;
			SPEED = (int) (100 + Math.random() * 400);
			return 0;
		} else if(hitBox.x > Gdx.graphics.getWidth()){
			hitBox.x = positionOriginalX;
			SPEED = (int) (100 + Math.random() * 400);
			return 1;
		}
		return -1;
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}
}
