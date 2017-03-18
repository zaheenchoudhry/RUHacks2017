package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends AbstractScreen  {

    private Player player;
    private PlatformObject platformObject;
    private BackgroundManager backgroundManager;
    private float[] backgroundColor;
    private float targetX, targetY;

    public GameScreen(final MainActivity game) {
        super(game);

        player = new Player(UNIT_X, UNIT_Y);
        platformObject = new PlatformObject(UNIT);
        backgroundColor = new float[3];

        backgroundManager = new BackgroundManager(UNIT_X, UNIT_Y);
        backgroundManager.setMountainColor(Themes.BLUE_NIGHT.getColors());
        setBackgroundColor(Themes.BLUE_NIGHT);

        //this.addActor(platformObject);
        this.addActor(backgroundManager);
        this.addActor(player);
    }

    public void setBackgroundColor(Themes theme) {
        float[] color = theme.getBackgroundColor();
        backgroundColor[0] = color[0];
        backgroundColor[1] = color[1];
        backgroundColor[2] = color[2];
    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void resize(int width, int height) {
        // set initial sizes and positions
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        targetX = screenX;
        targetY = SCREEN_HEIGHT - screenY;
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public void update() {
        float xMove = (targetX - player.getX()) / 15f;
        float yMove = (targetY - player.getY()) / 15f;

        backgroundManager.update((player.getX() - SCREEN_WIDTH / 2f) / SCREEN_WIDTH, (player.getY() - SCREEN_HEIGHT / 2f) / SCREEN_HEIGHT);

        if (xMove < 0 && player.getScaleX() > 0) {
            player.setScaleX(-1);
            player.setX(player.getX() + player.getWidth() / 2f);
        } else if (xMove > 0 && player.getScaleX() < 0) {
            player.setScaleX(1);
            player.setX(player.getX() - player.getWidth() / 2f);
        }

        float rotateAmount = Math.min(1f, (Math.abs(xMove) + Math.abs(yMove)) / 2f);
        float rotation = (float) Math.toDegrees(Math.PI / -2f + Math.atan(yMove / (xMove))) + ((xMove < 0) ? 180f : 0);
        player.rotateBy((rotation - player.getRotation()) / (30f - (29f * rotateAmount)));

        player.setX(player.getX() + xMove);
        player.setY(player.getY() + yMove);

        player.update();

        /*
        if (player.getY() <= platformObject.getY() + platformObject.getHeight() &&
                player.getX() + player.getWidth() > platformObject.getX() &&
                player.getX() < platformObject.getX() + platformObject.getWidth()) {
            player.setY(platformObject.getY() + platformObject.getHeight());
            player.setSpeed(-0.8f * player.getSpeed());
            if (Math.abs(player.getSpeed()) < 0.11f * UNIT) {
                player.stopFall();
            }
        }
        */
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.act(delta);
        this.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        super.dispose();
        player.dispose();
        platformObject.dispose();
        backgroundManager.dispose();
    }
}
