package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen  {

    private Player player;
    private BackgroundManager backgroundManager;
    private float[] backgroundColor;
    private float targetX, targetY;
    private ArrayList<Image> smokeList;
    private ArrayList<Float> smokeAlpha;
    private float smokeCounter;
    private Pixmap squarePixmap, starPixmap, moonPixmap;
    private Texture squareTexture, starTexture, moonTexture, moonGlowTexture;
    private int lighteningCounter;
    private Image lighteningImage;
    private float lighteningAlpha;
    private RainManager rainManager;
    private ArrayList<Image> stars;
    private Image moon, moonGlow;
    private ArrayList<Enemy> enemies;
    private int enemySpawnCount;
    private ArrayList<Float[]> previousPlayerPositions;

    public GameScreen(final MainActivity game) {
        super(game);

        enemySpawnCount = 250;
        lighteningAlpha = 0;
        lighteningCounter = 10;
        smokeCounter = 0;
        player = new Player(UNIT_X, UNIT_Y);
        backgroundColor = new float[3];

        rainManager = new RainManager(UNIT_X, UNIT_Y);
        backgroundManager = new BackgroundManager(UNIT_X, UNIT_Y);
        backgroundManager.setMountainColor(Themes.PURPLE_NIGHT.getColors());
        setBackgroundColor(Themes.PURPLE_NIGHT);

        this.starPixmap = new Pixmap((int)(UNIT_Y * 0.5f), (int)(UNIT_Y * 0.5f), Pixmap.Format.RGBA8888);
        starPixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        starPixmap.fillCircle((int) (UNIT_Y * 0.25f), (int) (UNIT_Y * 0.25f), (int) (UNIT_Y * 0.25f));
        this.starTexture = new Texture(starPixmap);

        float moonSize = (float)Math.random() * 15f + 25f;
        this.moonPixmap = new Pixmap((int)(UNIT_Y * moonSize), (int)(UNIT_Y * moonSize), Pixmap.Format.RGBA8888);
        moonPixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        moonPixmap.fillCircle((int) (UNIT_Y * moonSize / 2f), (int) (UNIT_Y * moonSize / 2f), (int) (UNIT_Y * moonSize / 2f));
        this.moonTexture = new Texture(moonPixmap);
        moon = new Image(moonTexture);
        moon.setX(SCREEN_WIDTH * (0.1f + 0.8f * (float)Math.random()));
        moon.setY(SCREEN_HEIGHT * (0.5f + 0.1f * (float)Math.random()));
        moonGlowTexture = new Texture("Glow3.png");
        moonGlow = new Image(moonGlowTexture);
        moonGlow.setSize(moonSize * UNIT_Y * 1.2f, moonSize * UNIT_Y * 1.2f);
        moonGlow.setX(moon.getX() - moonSize * UNIT_Y * 0.1f);
        moonGlow.setY(moon.getY() - moonSize * UNIT_Y * 0.1f);
        moonGlow.setColor(1.0f, 1.0f, 1.0f, 0.7f);

        stars = new ArrayList<Image>();
        for (int j = 0; j < 5; ++j) {
            for (int i = 0; i < 8; ++i) {
                Image star = new Image(starTexture);
                star.setColor(1.0f, 1.0f, 1.0f, 0.4f + 0.6f * (float) Math.random());
                star.setX(SCREEN_WIDTH * 0.2f * (float)j + SCREEN_WIDTH * 0.2f * (float) Math.random());
                star.setY(SCREEN_HEIGHT - 0.7f * SCREEN_HEIGHT * (float) Math.random());
                this.addActor(star);
            }
        }

        squarePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        squarePixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        squarePixmap.fillRectangle(0, 0, 1, 1);
        squareTexture = new Texture(squarePixmap);
        smokeList = new ArrayList<Image>();
        smokeAlpha = new ArrayList<Float>();

        lighteningImage = new Image(squareTexture);
        lighteningImage.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        lighteningImage.setColor(1.0f, 1.0f, 1.0f, lighteningAlpha);

        enemies = new ArrayList<Enemy>();

        previousPlayerPositions = new ArrayList<Float[]>();
        for (int i = 0; i < 10; ++i) {
            previousPlayerPositions.add(new Float[]{0f, 0f});
        }

        //this.addActor(platformObject);
        this.addActor(lighteningImage);
        this.addActor(moonGlow);
        this.addActor(moon);
        this.addActor(backgroundManager);
        this.addActor(player);
        this.addActor(rainManager);

        Cursor cursor = Gdx.graphics.newCursor(squarePixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
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
        targetX = screenX - player.getWidth() / 2f;
        targetY = SCREEN_HEIGHT - screenY - player.getHeight() / 2f;
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public void update() {
        enemySpawnCount--;

        previousPlayerPositions.remove(0);
        previousPlayerPositions.add(new Float[]{player.getX(), player.getY()});

        if (enemySpawnCount == 0) {
            enemySpawnCount = 250;
            Enemy enemy = new Enemy(UNIT_X, UNIT_Y);
            enemy.setX(-50f * UNIT_X + (Math.random() > 0.5 ? SCREEN_WIDTH * 2f : 0));
            enemy.setY((float)Math.random() * SCREEN_HEIGHT);
            enemies.add(enemy);
            this.addActor(enemy);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update(previousPlayerPositions.get(0));
            if (enemies.get(i).isDead() && enemies.get(i).getY() + enemies.get(i).getHeight() < 0) {
                enemies.get(i).remove();
                enemies.remove(enemies.get(i));
                i--;
            }
        }

        rainManager.update();
        lighteningCounter--;
        if (lighteningCounter == 10) {
            lighteningImage.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            lighteningAlpha = 1.0f;
        } else if (lighteningCounter == 0) {
            lighteningImage.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            lighteningAlpha = 1.0f;
            lighteningCounter = (int)(Math.random() * 100 + 150);
        }

        if (lighteningAlpha > 0) {
            lighteningAlpha -= 0.05f;
            if (lighteningAlpha < 0) {
                lighteningAlpha = 0;
            }
            lighteningImage.setColor(1.0f, 1.0f, 1.0f, lighteningAlpha);
        }

        if (smokeCounter <= 0) {
            smokeCounter = 20;
            Image smoke = new Image(squareTexture);
            smoke.setSize(20f, 20f);
            if (player.getScaleX() > 0) {
                smoke.setX(player.getX() + player.getWidth() / 2f - (player.getWidth() / 2f + 9f - 20f / 2f) * (float) Math.cos(Math.toRadians(player.getRotation())) + (player.getHeight() / 2f + 20f) * (float) Math.sin(Math.toRadians(player.getRotation())));
            } else {
                smoke.setX(player.getX() + player.getWidth() / 2f + ((player.getWidth() / 2f + 9f - 20f / 2f) * (float) Math.cos(Math.toRadians(player.getRotation())) + (player.getHeight() / 2f + 20f) * (float) Math.sin(Math.toRadians(player.getRotation()))));
            }
            smoke.setY(player.getY() + player.getHeight() / 2f - (player.getHeight() / 2f + 20f) * (float)Math.cos(Math.toRadians(player.getRotation())) + (player.getWidth() / 2f + 9f - 20f / 2f) * (float)Math.sin(Math.toRadians(player.getRotation())));
            smokeList.add(smoke);
            this.addActor(smoke);
            smokeAlpha.add(0f);
        }

        for (int i = 0; i < smokeAlpha.size(); ++i) {
            Float a = smokeAlpha.get(i) - 0.05f;
            smokeAlpha.remove(i);
            smokeAlpha.add(i, a);
            smokeList.get(i).setColor(0.2f, 0.2f, 0.2f, a);
        }

        for (Image smoke : smokeList) {
            float size = smoke.getWidth() - 0.75f;
            smoke.setSize(size, size);
            smoke.setOrigin(size / 2f, size / 2f);
            smoke.setX(smoke.getX() + 0.005f);
            smoke.setY(smoke.getY() + 0.005f);
            smoke.rotateBy((float)Math.toDegrees(Math.PI / 2f + Math.PI / 2f * Math.random()));
        }

        if (!smokeList.isEmpty() && smokeList.get(0).getWidth() < 0.51f) {
            smokeList.get(0).remove();
            smokeList.remove(0);
            smokeAlpha.remove(0);
        }

        float xMove = (targetX - player.getX()) / 15f;
        float yMove = (targetY - player.getY()) / 15f;
        float speed = (float)Math.sqrt(xMove * xMove + yMove * yMove);
        smokeCounter -= speed;

        backgroundManager.update((player.getX() - SCREEN_WIDTH / 2f) / SCREEN_WIDTH, (player.getY() - SCREEN_HEIGHT / 2f) / SCREEN_HEIGHT);

        if (xMove < 0 && player.getScaleX() > 0) {
            player.setScaleX(-1);
        } else if (xMove > 0 && player.getScaleX() < 0) {
            player.setScaleX(1);
        }

        float rotateAmount = Math.min(1f, (Math.abs(xMove) + Math.abs(yMove)) / 45f);
        float rotation = (float) Math.toDegrees(Math.PI / -2f + Math.atan(yMove / (xMove))) + ((xMove < 0) ? 180f : 0);
        player.rotateBy((rotation - player.getRotation()) / (1f / rotateAmount));

        player.setX(player.getX() + xMove);
        player.setY(player.getY() + yMove);

        player.update(speed);

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
        backgroundManager.dispose();
        squareTexture.dispose();
        squarePixmap.dispose();
    }
}
