package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class AbstractScreen extends Stage implements Screen {

    public static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final float UNIT = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT) / 100;
    public static final float UNIT_X = SCREEN_WIDTH / 100;
    public static final float UNIT_Y = SCREEN_HEIGHT / 100;

    protected MainActivity game;
    protected OrthographicCamera camera;

    public AbstractScreen(MainActivity game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setViewport(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera));
    }

    public abstract void update();
}
