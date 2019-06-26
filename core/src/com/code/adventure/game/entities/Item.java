package com.code.adventure.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.code.adventure.game.util.Utils;

public class Item {

    private Vector2 position;
    private short index;
    private String type;
    public short intialIndex;
    boolean startDeathSound =true;

    public Item (Vector2 position,short index,String type){
        this.position = position;
        this.index = index;
        this.type=type;
    }

    public Item(Vector2 position, short index) {
        this.position = position;
        this.index = index;
    }

    public boolean isStartDeathSound() {
        return startDeathSound;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    public void render(SpriteBatch batch){

    }
    public void update(float delta){

    }
}
