package com.ruhacks.bruhacks2017;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Group {

    float unitX, unitY;
    private Image player, flame;
    private Texture playerTexture, flameTexture;
    //private float speedConstant, currentSpeed;
    private float width, height;

    public Player(float unitX, float unitY) {
        this.unitX = unitX;
        this.unitY = unitY;
        //speedConstant = unit * 0.1f;
        //currentSpeed = 0;
        height = 15f * unitY;
        width = height * (117f / 200f);

        playerTexture = new Texture("Player.png");
        player = new Image(playerTexture);
        player.setSize(width, height);

        flameTexture = new Texture("Triangle.png");
        flame = new Image(flameTexture);
        flame.setColor(1.0f, 0.7f, 0, 1.0f);

        this.setX(unitX * 25f);
        this.setY(unitY * 30f);

        this.setOrigin(width / 2f, height / 2f);

        this.addActor(flame);
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

    public void update(float playerSpeed) {
        this.rotateBy(-this.getRotation() / 8f);

        float flameWidth = (float)Math.random() * 10f + 9f;
        float flameHeight = (float)Math.random() * 10f + 15f + 40f * (playerSpeed / 40f);
        flame.setSize(flameWidth, flameHeight);
        flame.setOrigin(flameWidth / 2f, flameHeight / 2f);
        flame.setRotation((float)Math.toDegrees(Math.PI) * 0.97f);
        flame.setX(9f - flameWidth / 2f);
        flame.setY(20f - flameHeight);
        //currentSpeed += speedConstant;
        //this.setY(this.getY() - currentSpeed);
    }

    public void stopFall() {
        //currentSpeed = 0;
        //speedConstant = 0;
    }

    public void dispose() {
        playerTexture.dispose();
        flameTexture.dispose();
    }
}
