package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PlatformObject extends Group {

    float unit;
    private Image item;
    private Pixmap pixmap;
    private Texture texture;
    private float width, height;

    public PlatformObject(float unit) {
        this.unit = unit;
        width = 50f * unit;
        height = 10f * unit;

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.4f, 0.4f, 0.4f, 1.0f);
        pixmap.fillRectangle(0, 0, 1, 1);
        texture = new Texture(pixmap);
        item = new Image(texture);
        item.setSize(width, height);

        this.setX(unit * 30);
        this.setY(unit * 10);


        this.addActor(item);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void update() {

    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }
}
