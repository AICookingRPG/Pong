package com.mygdx.pong.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Paddle {
	
	protected Texture texture;
	protected Rectangle hitBox;

	public Paddle(float x, float y) {
		texture = new Texture(Gdx.files.internal("paddle.png"));
		hitBox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, hitBox.x, hitBox.y, texture.getWidth(), texture.getHeight());
	}
	
	public abstract void update();

	public Rectangle getHitBox() { // Observador del rectangulo.
		return hitBox;
	}
	
	protected boolean ballHitAbove() {
		return hitBox.y + texture.getHeight() >= Gdx.graphics.getHeight();
	}
	protected boolean ballHitBelow() { // Método que detecta si la pala se ha chocado con el borde de abajo
		return hitBox.y <= 0;
	}
}
