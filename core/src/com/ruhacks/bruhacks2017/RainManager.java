package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public class RainManager extends Group {

    float unitX, unitY;
    private Pixmap squarePixmap;
    private Texture squareTexture;
    private ArrayList<Image> rainList;
    private int rainCounter;
    private ArrayList<Float> rainSpeeds;

    public RainManager(float unitX, float unitY) {
        this.unitX = unitX;
        this.unitY = unitY;
        rainCounter = 0;
        rainList = new ArrayList<Image>();
        rainSpeeds = new ArrayList<Float>();

        squarePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        squarePixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        squarePixmap.fillRectangle(0, 0, 1, 1);
        squareTexture = new Texture(squarePixmap);


    }

    public void update() {
        if (rainCounter == 0) {
            Image raindrop = new Image(squareTexture);
            raindrop.setWidth(unitX * 0.2f);
            raindrop.setHeight(unitY * 8f + (float)(Math.random() * 8f * unitY));
            raindrop.setRotation(-30f);
            raindrop.setColor(1.0f, 1.0f, 1.0f, 0.07f + (float)Math.random() * 0.2f);
            raindrop.setX((float)(Math.random() * unitX * 125f));
            raindrop.setY(unitY * 100f + 10f);
            rainList.add(raindrop);
            this.addActor(raindrop);

            rainSpeeds.add(20f + (float)Math.random() * 15f);
        }

        for (int i = 0; i < rainList.size(); ++i) {
            rainList.get(i).setX(rainList.get(i).getX() - rainSpeeds.get(i) * (float)Math.sin(Math.toRadians(30f)));
            rainList.get(i).setY(rainList.get(i).getY() - rainSpeeds.get(i) * (float)Math.cos(Math.toRadians(30f)));

            if (rainList.get(i).getX() + rainList.get(i).getHeight() < 0 || rainList.get(i).getY() + rainList.get(i).getHeight() < 0) {
                rainList.get(i).remove();
                rainList.remove(i);
                rainSpeeds.remove(i);
                i--;
            }
        }
    }

    public void dispose() {
        squareTexture.dispose();
    }
}
