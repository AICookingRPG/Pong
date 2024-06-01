package com.mygdx.pong.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.pong.tween.ActorAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;


public class MainMenu implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TweenManager tweenManager;

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

        table = new Table(skin);
        table.setFillParent(true);

        Label heading = new Label("PONG GAME", skin, "large");

        TextButton buttonPlay = new TextButton("PLAY", skin);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -(stage.getHeight()), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelSelect());
                    }
                })));
            }
        });
        buttonPlay.padLeft(20);
        buttonPlay.padRight(20);

        TextButton buttonSettings = new TextButton("SETTINGS", skin, "small");
        buttonSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -(stage.getHeight()), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
                    }
                })));
            }
        });
        buttonSettings.padLeft(20);
        buttonSettings.padRight(20);

        TextButton buttonExit = new TextButton("EXIT", skin);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Timeline.createParallel().beginParallel()
                        .push(Tween.to(table, ActorAccessor.ALPHA, 0.75f).target(0))
                        .push(Tween.to(table, ActorAccessor.Y, 0.75f).target(table.getY() - 50)
                                .setCallback(new TweenCallback() {

                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        Gdx.app.exit();
                                    }
                                }))
                        .end().start(tweenManager);
            }
        });
        buttonExit.padLeft(20);
        buttonExit.padRight(20);

        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(30);
        table.row();
        table.add(buttonSettings);
        table.getCell(buttonSettings).spaceBottom(30);
        table.row();
        table.add(buttonExit);

        stage.addActor(table);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 0, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 1, 1))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);

        Timeline.createSequence().beginSequence()
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonSettings, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAccessor.ALPHA, 0.5f).target(0))
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, 0.5f).target(1))
                .push(Tween.to(buttonSettings, ActorAccessor.ALPHA, 0.5f).target(1))
                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, 0.5f).target(1))
                .end().start(tweenManager);

        Tween.from(table, ActorAccessor.ALPHA, 0.75f).target(0).start(tweenManager);

//        if(!ChessGame.menuInitialised) {
//            Tween.from(table, ActorAccessor.Y, 1).target(Gdx.graphics.getHeight() / 8).start(tweenManager);
//            ChessGame.menuInitialised = true;
//        } else{
//            stage.addAction(sequence(moveTo(0, -(stage.getHeight())), moveTo(0, 0, 0.5f)));
//        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        table.invalidateHierarchy();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }
}
