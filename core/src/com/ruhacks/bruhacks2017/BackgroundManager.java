package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public class BackgroundManager extends Group {

    float unit;
    private Image item;
    private Pixmap pixmap;
    private Texture texture;
    private float[][] mountainColor;
    private ArrayList<ArrayList<Image>> mountains;

    public BackgroundManager(float unit) {
        this.unit = unit;
        mountainColor = new float[3][3];
        mountains = new ArrayList<ArrayList<Image>>();

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.fillRectangle(0, 0, 1, 1);
        texture = new Texture(pixmap);

    }

    public void setMountainColor(float[][] color) {
        for (int i = 0; i < mountainColor.length; ++i) {
            for (int j = 0; j < mountainColor[j].length; ++j) {
                mountainColor[i][j] = color[i][j];
            }
            for (int k = 0; k < mountains.get(i).size(); ++k) {
                mountains.get(i).get(k).setColor(color[i][0], color[i][1], color[i][2], 1.0f);
            }
        }

    }

    public void update() {

    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }
}
