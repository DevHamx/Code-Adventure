package com.code.adventure.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Utils;

public class Platform {
    public float top;
    public float bottom;
    public float left;
    public float right;
    public Vector2 position;
    public float width;
    public Platform(Vector2 position){
        this.position = position;
        left = position.x;
        width=Constants.TILE_SIZE;
        right=left+width;
        bottom = position.y;
        top = bottom + Constants.PLATFORM_HEIGHT;
    }

    public Vector2 getPosition() {
        return position;
    }
    public void widthPlus(float tileWidthSize){
        width+=tileWidthSize;
        right=left+width;
    }

    public float getWidth() {
        return width;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        left=position.x;
        bottom=position.y;
    }
}
