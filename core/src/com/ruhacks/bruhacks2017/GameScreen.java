package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends AbstractScreen  {

    private boolean dead;
    private Player player;
    private BackgroundManager backgroundManager;
    private float[] backgroundColor;
    private float targetX, targetY;
    private ArrayList<Image> smokeList;
    private ArrayList<Float> smokeAlpha;
    private float smokeCounter;
    private Pixmap squarePixmap, starPixmap, moonPixmap, explosionPixmap;
    private Texture squareTexture, starTexture, moonTexture, moonGlowTexture, explosionTexture;
    private Texture enemyTexture, flameTexture;
    private int lighteningCounter;
    private Image lighteningImage;
    private float lighteningAlpha;
    private RainManager rainManager;
    private ArrayList<Image> stars;
    private Image moon, moonGlow, explosion;
    private ArrayList<Enemy> enemies;
    private int enemySpawnCount, spawnCountResetVal, scoreCount;
    private ArrayList<Float[]> previousPlayerPositions;
    private Label score;
    private BitmapFont font;
    private float addX;
    private float addY;
    private Sound thunder;

    public GameScreen(final MainActivity game) {
        super(game);

        thunder = Gdx.audio.newSound(Gdx.files.internal("thunder.mp3"));
        Sound rain = Gdx.audio.newSound(Gdx.files.internal("rain.wav"));
        rain.setLooping(rain.play(0.6f), true);
        dead = false;

        scoreCount = 0;
        enemySpawnCount = 300;
        spawnCountResetVal = 280;
        lighteningAlpha = 0;
        lighteningCounter = 10;
        smokeCounter = 0;
        player = new Player(UNIT_X, UNIT_Y);
        backgroundColor = new float[3];
        addX = 0;
        addY = 0;

        rainManager = new RainManager(UNIT_X, UNIT_Y);
        backgroundManager = new BackgroundManager(UNIT_X, UNIT_Y);
        Themes theme = Themes.values()[(int)(Math.random() * Themes.values().length)];
        backgroundManager.setMountainColor(theme.getColors());
        setBackgroundColor(theme);

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

        this.explosionPixmap = new Pixmap((int)(UNIT_Y * 150f), (int)(UNIT_Y * 150f), Pixmap.Format.RGBA8888);
        explosionPixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        explosionPixmap.fillCircle((int) (UNIT_Y * 75f), (int) (UNIT_Y * 75f), (int) (UNIT_Y * 75f));
        this.explosionTexture = new Texture(explosionPixmap);
        explosion = new Image(explosionTexture);
        explosion.setScale(0, 0);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Math.round(10f * UNIT);
        this.font = generator.generateFont(parameter);
        generator.dispose();

        Color textColor = new Color();
        textColor.set(1.0f, 0.8f, 0f, 1.0f);
        score = new Label("00", new Label.LabelStyle(font, textColor));
        score.setX(UNIT_X * 93f);
        score.setY(UNIT_Y * 87f);

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

        enemyTexture = new Texture("Enemy.png");
        flameTexture = new Texture("Triangle.png");

        //this.addActor(platformObject);
        this.addActor(lighteningImage);
        this.addActor(moonGlow);
        this.addActor(moon);
        this.addActor(backgroundManager);
        this.addActor(player);
        this.addActor(rainManager);
        this.addActor(score);

        Cursor cursor = Gdx.graphics.newCursor(squarePixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
    }

    public void setXY(float x, float y) {
        /*if (y > 1) {
            if (y <= 80 && y > 1) {
                addY = 80;
            } else {
                addX = x;
                addY = y;
            }
        }*/
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
        if (dead) {
            explosion.setScale(explosion.getScaleX() + 0.15f, explosion.getScaleX() + 0.15f);
            explosion.setX(player.getX() + player.getWidth() / 2f - explosion.getWidth() / 2f * explosion.getScaleX());
            explosion.setY(player.getY() + player.getHeight() / 2f - explosion.getWidth() / 2f * explosion.getScaleX());
            if (explosion.getScaleX() > 1) {
                explosion.setColor(1.0f, 1.0f, 1.0f, 1.0f - explosion.getScaleX() / 3);
                game.setNewGameScreen();
            }
            return;
        }
        enemySpawnCount--;

        previousPlayerPositions.remove(0);
        previousPlayerPositions.add(new Float[]{player.getX(), player.getY()});

        if (enemySpawnCount == 0) {
            enemySpawnCount = spawnCountResetVal;
            spawnCountResetVal = Math.max(80, spawnCountResetVal - 15);
            Enemy enemy = new Enemy(UNIT_X, UNIT_Y, enemyTexture, flameTexture);
            enemy.setX(-50f * UNIT_X + (Math.random() > 0.5 ? SCREEN_WIDTH * 2f : 0));
            enemy.setY((float)Math.random() * SCREEN_HEIGHT);
            enemies.add(enemy);
            this.addActor(enemy);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update(previousPlayerPositions.get(0));
            if (!enemies.get(i).isDead() && Math.abs(player.getX() + player.getWidth() / 2f - enemies.get(i).getX() - enemies.get(i).getWidth() / 2f) < player.getWidth() / 2f &&
                    Math.abs(player.getY() + player.getHeight() / 2f - enemies.get(i).getY() - enemies.get(i).getHeight() / 2f) < player.getHeight() / 2f) {
                dead = true;
                explosion.setX(player.getX() + player.getWidth() / 2f);
                explosion.setY(player.getY() + player.getHeight() / 2f);
                this.addActor(explosion);
            }
            if (enemies.get(i).isDead() && enemies.get(i).getY() + enemies.get(i).getHeight() < 0) {
                scoreCount++;
                score.setText((scoreCount < 10 ? "0" : "") + scoreCount);
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
            lighteningCounter = (int)(Math.random() * 200 + 280);
            thunder.play(1f);

        }

        if (lighteningAlpha > 0) {
            lighteningAlpha -= 0.05f;
            if (lighteningAlpha < 0) {
                lighteningAlpha = 0;
            }
            lighteningImage.setColor(1.0f, 1.0f, 1.0f, lighteningAlpha);
        }

        /*if (smokeCounter <= 0) {
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
        smokeCounter -= speed;*/

        float xTarget = (addX + 200) / 500 * SCREEN_WIDTH;
        float yTarget = (addY - 80) / 600 * SCREEN_HEIGHT;
        float xMove = (xTarget - player.getX()) / 15f;
        float yMove = (yTarget - player.getY()) / 15f;
        float speed = (float)Math.sqrt(xMove * xMove + yMove * yMove);

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
        //super.dispose();
        player.dispose();
        backgroundManager.dispose();
        squareTexture.dispose();
        starTexture.dispose();
        moonTexture.dispose();
        moonGlowTexture.dispose();
        explosionTexture.dispose();
        enemyTexture.dispose();
        flameTexture.dispose();
        rainManager.dispose();
    }
}