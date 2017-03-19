package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Enemy extends Group {

    boolean dead;
    float unitX, unitY;
    private Image enemy, flame;
    private Texture enemyTexture, flameTexture;
    private float width, height, speed;
    private boolean[] visitiedQuadrants;
    private int initialQuadrant;

    public Enemy(float unitX, float unitY) {
        dead = false;
        this.unitX = unitX;
        this.unitY = unitY;
        this.speed = (0.3f + (float)Math.random() * 0.2f) * unitX;
        height = 10f * unitY;
        width = height * (131f / 200f);
        initialQuadrant = -1;
        visitiedQuadrants = new boolean[]{false, false, false, false, false, false, false, false};

        enemyTexture = new Texture("Enemy.png");
        enemy = new Image(enemyTexture);
        enemy.setSize(width, height);

        flameTexture = new Texture("Triangle.png");
        flame = new Image(flameTexture);
        flame.setColor(1.0f, 0.7f, 0, 1.0f);

        this.setOrigin(width / 2f, height / 2f);

        this.addActor(flame);
        this.addActor(enemy);
    }

    public boolean isDead() {
        return dead;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void update(Float[] playerPosition) {
        if (!dead) {
            float moveHorizontal = playerPosition[0] - this.getX();
            float moveVertical = playerPosition[1] - this.getY();
            float xMove = speed * (moveHorizontal == 0 ? 0 : moveHorizontal / Math.abs(moveHorizontal));
            float yMove = speed * (moveVertical == 0 ? 0 : moveVertical / Math.abs(moveVertical));
            if (Math.abs(moveHorizontal) < speed) {
                xMove = moveHorizontal;
            }
            if (Math.abs(moveVertical) < speed) {
                yMove = moveVertical;
            }

            int quadrant;
            if (moveHorizontal < 0 && moveVertical < 0) {
                quadrant = 0;
            } else if (moveHorizontal > 0 && moveVertical < 0) {
                quadrant = 2;
            } else if (moveHorizontal > 0 && moveVertical > 0) {
                quadrant = 4;
            } else {
                quadrant = 6;
            }
            if (Math.abs(moveVertical / moveHorizontal) >= 1) {
                quadrant += 1;
            }
            if (initialQuadrant == -1) {
                initialQuadrant = quadrant;
            }
            visitiedQuadrants[quadrant] = true;

            if (quadrant == initialQuadrant && visitiedQuadrants[0] && visitiedQuadrants[1] && visitiedQuadrants[2] &&
                    visitiedQuadrants[3] && visitiedQuadrants[4] && visitiedQuadrants[5] && visitiedQuadrants[6] && visitiedQuadrants[7]) {
                this.dead = true;
            }

            if (xMove > 0 && this.getScaleX() > 0) {
                this.setScaleX(-1);
            } else if (xMove < 0 && this.getScaleX() < 0) {
                this.setScaleX(1);
            }

            this.setX(this.getX() + xMove);
            this.setY(this.getY() + yMove);

            float rotateAmount = Math.min(1f, (Math.abs(xMove) + Math.abs(yMove)) / 90f);
            float rotation = (float) Math.toDegrees(Math.PI / -2f + Math.atan(yMove / (xMove))) + ((xMove < 0) ? 180f : 0);
            this.rotateBy((rotation - this.getRotation()) / (1f / rotateAmount));
            this.rotateBy(-this.getRotation() / 8f);

            float flameWidth = (float) Math.random() * 10f + 9f;
            float flameHeight = (float) Math.random() * 10f + 15f;
            flame.setSize(flameWidth, flameHeight);
            flame.setOrigin(flameWidth / 2f, flameHeight / 2f);
            flame.setRotation((float) Math.toDegrees(Math.PI) * 0.97f);
            flame.setX(this.getWidth() - 9f - flameWidth / 2f);
            flame.setY(20f - flameHeight);
        } else {
            this.setY(this.getY() - 10f);
            this.rotateBy((float)Math.PI * 2);
            flame.setHeight(flame.getHeight() / 2f);
        }
    }

    public void dispose() {
        enemyTexture.dispose();
        flameTexture.dispose();
    }
}
