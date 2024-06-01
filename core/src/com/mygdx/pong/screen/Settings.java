package com.mygdx.pong.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.audio.Music;
import com.mygdx.pong.PongGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Settings implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;
    private Music music;

//    public static FileHandle levelDirectory() {
//        String prefsDir = Gdx.app.getPreferences("CHESS GAME").getString("leveldirectory").trim();
//        if(prefsDir != null && !prefsDir.equals(""))
//            return Gdx.files.absolute(prefsDir);
//        else
//            return Gdx.files.absolute(Gdx.files.external("CHESS GAME" + "/levels").path());
//    }
//
    public static boolean vSync() {
        return Gdx.app.getPreferences("CHESS GAME").getBoolean("vsync");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        table.setClip(true);
        table.setSize(width, height);
        table.invalidateHierarchy();
    }

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/mainmenu.mp3"));
        music.setLooping(true);
        music.play();

        final CheckBox vSyncCheckBox = new CheckBox(" vSync", skin);
        vSyncCheckBox.setChecked(PongGame.vsync);

//        final TextField levelDirectoryInput = new TextField(levelDirectory().path(), skin);
        final TextField playerNameInput = new TextField(PongGame.player_name, skin);
        playerNameInput.setMessageText("PLAYER NAME");

        final TextButton back = new TextButton("BACK", skin, "small");
        back.pad(10);

        ClickListener buttonHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getListenerActor() == vSyncCheckBox) {
                    PongGame.vsync = vSync();
                    Gdx.graphics.setVSync(vSync());

                } else if(event.getListenerActor() == back) {
                    String playerName = playerNameInput.getText().trim().isEmpty() ? "PLAYER" : playerNameInput.getText().trim(); // shortened form of an if-statement: [boolean] ? [if true] : [else] // String#trim() removes spaces on both sides of the string
                    PongGame.player_name = playerName;
                    stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

                        @Override
                        public void run() {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                        }
                    })));
                }
            }
        };

        vSyncCheckBox.addListener(buttonHandler);

        back.addListener(buttonHandler);

        table.add(new Label("SETTINGS", skin)).spaceBottom(50).colspan(3).expandX().row();
        table.add();
        table.add("PLAYER NAME");
        table.add().row();
        table.add(vSyncCheckBox).top().expandY();
        table.add(playerNameInput).top().fillX();
        table.add(back).bottom().right();

        stage.addActor(table);

        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        music.stop();
        stage.dispose();
        skin.dispose();
        music.dispose();
    }

}