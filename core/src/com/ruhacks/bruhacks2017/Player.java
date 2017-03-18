package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Group {

    float unit;
    private Image item;
    private Pixmap pixmap;
    private Texture texture;
    private float speedConstant, currentSpeed;
    private float width, height;

    public Player(float unit) {
        this.unit = unit;
        speedConstant = unit * 0.1f;
        currentSpeed = 0;
        width = 10f * unit;
        height = 10f * unit;

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1.0f);
        pixmap.fillRectangle(0, 0, 1, 1);
        texture = new Texture(pixmap);
        item = new Image(texture);
        item.setSize(width, height);

        this.setX(unit * 50f);
        this.setY(unit * 90f);

        this.addActor(item);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return currentSpeed;
    }

    public void setSpeed(float speed) {
        currentSpeed = speed;
    }

    public void update() {
        currentSpeed += speedConstant;
        this.setY(this.getY() - currentSpeed);
    }

    public void stopFall() {
        currentSpeed = 0;
        speedConstant = 0;
    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }
}
