package com.mygdx.pong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LevelTest implements Screen {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private final float TIMESTEP = 1/60f;
    private final int VELOCITYITERATIONS = 10, POSITIONITERATIONS = 5;

    private Vector3 bottomLeft, bottomRight;

//    private final float pixelsToMeters = 32;

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyDef.BodyType.DynamicBody;
        ballDef.position.set(0, 1);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0.8f;

        Body ball = world.createBody(ballDef);
        ball.createFixture(fixtureDef);

        circleShape.dispose();

        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0);

        ChainShape groundShape = new ChainShape();
        bottomLeft = new Vector3(0, Gdx.graphics.getHeight(), 0);
        bottomRight = new Vector3(Gdx.graphics.getWidth(), bottomLeft.y, 0);
        camera.unproject(bottomLeft);
        camera.unproject(bottomRight);
        groundShape.createChain(new float[] {bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y});

        fixtureDef.shape = groundShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        Body ground = world.createBody(groundDef);
        ground.createFixture(fixtureDef);

        groundShape.dispose();

        BodyDef obstableDef = new BodyDef();
        obstableDef.type = BodyDef.BodyType.DynamicBody;
        obstableDef.position.set(2.25f, 10);

        PolygonShape obstacleShape = new PolygonShape();
        obstacleShape.setAsBox(0.5f, 1);

        fixtureDef.shape = obstacleShape;
        fixtureDef.friction = 0.75f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.density = 5;

        Body box = world.createBody(obstableDef);
        box.createFixture(fixtureDef);

        obstacleShape.dispose();

        box.applyAngularImpulse(5, true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, camera.combined);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/25;
        camera.viewportHeight = height/25;
        camera.update();
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
        world.dispose();
        debugRenderer.dispose();
    }
}
