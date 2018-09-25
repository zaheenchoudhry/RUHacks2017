package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public class BackgroundManager extends Group {

    float unitX, unitY;
    private Pixmap squarePixmap;
    private Texture mountainTexture, squareTexture;
    private float[][] mountainColor;
    private Group[] mountainGroup;
    private ArrayList<ArrayList<Image>> mountainList;
    private Image[] squares;

    public BackgroundManager(float unitX, float unitY) {
        this.unitX = unitX;
        this.unitY = unitY;
        mountainColor = new float[3][3];
        mountainList = new ArrayList<ArrayList<Image>>();
        squares = new Image[3];
        mountainGroup = new Group[3];

        squarePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        squarePixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        squarePixmap.fillRectangle(0, 0, 1, 1);
        squareTexture = new Texture(squarePixmap);

        mountainTexture = new Texture("Triangle.png");

        generateMountains();
    }

    public void generateMountains() {
        float startDisplacement = 0.2f;
        float endDisplacement = 0.5f;
        float widthRange[][] = new float[3][2];
        float heightRange[][] = new float[3][2];

        widthRange[0][0] = 25f;
        widthRange[0][1] = 35f;
        widthRange[1][0] = 35f;
        widthRange[1][1] = 45f;
        widthRange[2][0] = 50f;
        widthRange[2][1] = 70f;

        heightRange[0][0] = 20f;
        heightRange[0][1] = 40f;
        heightRange[1][0] = 25f;
        heightRange[1][1] = 35f;
        heightRange[2][0] = 35f;
        heightRange[2][1] = 50f;

        for (int i = 2; i >= 0; --i) {
            float previousPosition = -50f * unitX;
            float previousWidth = 0;
            ArrayList<Image> mountains = new ArrayList<Image>();
            mountainGroup[i] = new Group();
            while (previousPosition <= unitX * 130f) {
                float mountainWidth = (float) Math.random() * ((widthRange[i][1] - widthRange[i][0]) * unitX) + (widthRange[i][0] * unitX);
                float mountainHeight = (float) Math.random() * ((heightRange[i][1] - heightRange[i][0]) * unitY) + (heightRange[i][0] * unitY);
                float mountainDisplacement = (float) Math.random() * ((endDisplacement - startDisplacement) * previousWidth) + (startDisplacement * previousWidth);
                previousWidth = mountainWidth;
                Image mountain = new Image(mountainTexture);
                mountain.setSize(mountainWidth, mountainHeight);
                mountain.setX(previousPosition);
                mountain.setY(10f * unitY * (i));
                mountain.setColor(0.2f + (0.15f * (float)i), 0.2f + (0.15f * (float)i), 0.2f + (0.15f * (float)i), 1.0f);
                previousPosition += mountainDisplacement;
                mountains.add(mountain);
                mountainGroup[i].addActor(mountain);
            }
            this.addActor(mountainGroup[i]);
            mountainList.add(mountains);
            squares[i] = new Image(squareTexture);
            squares[i].setWidth(200f * unitX);
            squares[i].setHeight((30f + 10f * (i)) * unitY);
            squares[i].setX(-50f * unitX);
            squares[i].setY(-30f * unitY);
            squares[i].setColor(0.2f + (0.15f * (float)i), 0.2f + (0.15f * (float)i), 0.2f + (0.15f * (float)i), 1.0f);
            mountainGroup[i].addActor(squares[i]);
        }
    }

    public void setMountainColor(float[][] color) {
        for (int i = 0; i < mountainColor.length; ++i) {
            for (int j = 0; j < mountainColor[i].length; ++j) {
                mountainColor[i][j] = color[i][j];
            }
            for (int k = 0; k < mountainList.get(2 - i).size(); ++k) {
                mountainList.get(2 - i).get(k).setColor(color[i][0], color[i][1], color[i][2], 1.0f);
                squares[i].setColor(color[i][0], color[i][1], color[i][2], 1.0f);
            }
        }

    }

    public void update(float xOffset, float yOffset) {
        for (int i = 0; i < 3; ++i) {
            mountainGroup[i].setX(10f * unitX * (3 - i) * -xOffset);
            mountainGroup[i].setY(10f * unitY * (3 - i) * -yOffset);
        }
    }

    public void dispose() {
        mountainTexture.dispose();
        squareTexture.dispose();
    }
}
