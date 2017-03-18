package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Smoke extends Group {

    private float alpha, size;
    private Image smoke;

    public Smoke(Texture texture) {
        alpha = 1.0f;
        smoke = new Image(texture);
        smoke.setColor(1.0f, 1.0f, 1.0f, alpha);
        this.addActor(smoke);
    }

    public void setSmokeSize(float size) {
        this.size = size;
        smoke.setSize(size, size);
    }

    public float getSmokeSize() {
        return size;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        smoke.setColor(1.0f, 1.0f, 1.0f, alpha);
    }

    public float getAlpha() {
        return alpha;
    }

    public void update() {

    }

    public void dispose() {

    }
}
