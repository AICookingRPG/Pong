package com.mygdx.pong.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pong.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class EndSplash implements Screen{
    private SpriteBatch batch;
    private Sprite splash;
    private TweenManager tweenManager;
    private BitmapFont large_font;
    private boolean playerWin = false;
    private Music music;

    public EndSplash(boolean win){
        playerWin = win;
        if (win){
            music = Gdx.audio.newMusic(Gdx.files.internal("sound/win.mp3"));
        } else{
            music = Gdx.audio.newMusic(Gdx.files.internal("sound/lose.mp3"));
        }
    }

    @Override
    public void show() {
        large_font = new BitmapFont(Gdx.files.internal("font/white128.fnt"), Gdx.files.internal("font/white128_0.png"), false);
        large_font.setColor(Color.ORANGE);

        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Texture splashTexture = new Texture(Gdx.files.internal("field.png"));
        music.play();
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.begin();
//        if(playerWin){
//				large_font.draw(batch, "PLAYER WINS" + Integer.toString(playerScore) + ":" + Integer.toString(computerScore), Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2);
//			} else{
//				large_font.draw(batch, "COMPUTER WINS" + Integer.toString(computerScore) + ":" + Integer.toString(playerScore), Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2);
//			}

        Tween.set(splash, SpriteAccessor.ALPHA).target(1).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(0)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelect(false));
                    }
                }).start(tweenManager);
        batch.end();
    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(Color.CLEAR);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        splash.draw(batch);

        batch.end();

        tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        splash.setSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        splash.getTexture().dispose();
    }
}
