package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Group {

    float unitX, unitY;
    private Image player;
    private Texture texture;
    //private float speedConstant, currentSpeed;
    private float width, height;

    public Player(float unitX, float unitY) {
        this.unitX = unitX;
        this.unitY = unitY;
        //speedConstant = unit * 0.1f;
        //currentSpeed = 0;
        height = 15f * unitY;
        width = height * (117f / 200f);

        texture = new Texture("Player.png");
        player = new Image(texture);
        player.setSize(width, height);

        this.setX(unitX * 25f);
        this.setY(unitY * 30f);

        this.addActor(player);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    //public float getSpeed() {
        //return currentSpeed;
    //}

    public void setSpeed(float speed) {
        //currentSpeed = speed;
    }

    public void update() {
        //currentSpeed += speedConstant;
        //this.setY(this.getY() - currentSpeed);
    }

    public void stopFall() {
        //currentSpeed = 0;
        //speedConstant = 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
