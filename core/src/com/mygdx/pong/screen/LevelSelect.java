package com.mygdx.pong.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelSelect implements Screen {
    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private ScrollPane scrollPane;
    private TextButton play, back;
    private Music music;
    private boolean animation = true;

    LevelSelect(boolean animation){
        this.animation = animation;
    }
    LevelSelect(){

    }

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

        table = new Table(skin);
        table.setFillParent(true);

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/levelselect.mp3"));
        music.setLooping(true);
        music.play();


        List list = new List(skin);
        list.setItems(new String[] {"LEVEL 1", "LEVEL 2", "LEVEL 3", "LEVEL 4", "LEVEL 5", "ABCDE", "FGHIJ", "LEVEL", "LOOOOOOOOOOOONG LEVEL", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});

        scrollPane = new ScrollPane(list, skin);

        play = new TextButton("PLAY", skin);
        play.padLeft(20);
        play.padRight(20);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new Game());
            }
        });

        back = new TextButton("BACK", skin, "small");
        back.padLeft(10);
        back.padRight(10);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }
                })));
            }
        });

        setupTable();

        stage.addActor(table);
        if (animation) {
            stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f)));
        }
    }
    private void setupTable(){
        table.clear();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.add("SELECT LEVEL").colspan(3).expandX().spaceBottom(50).row();
        table.add(scrollPane).uniformX().left().expandY();
        table.add(play).uniformX();
        table.add(back).uniform().bottom().right();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        music.stop();
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        music.dispose();
    }
}
