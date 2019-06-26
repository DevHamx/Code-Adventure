package com.code.adventure.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Utils;

public class Key extends Item{
    public short intialIndex;
    public Key(Vector2 position,short index,String type) {
        super(new Vector2(position.x,position.y+Constants.TILE_SIZE),index,type);
    }

    @Override
    public Vector2 getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Vector2 position) {
        super.setPosition(position);
    }

    @Override
    public void setIndex(short index) {
        super.setIndex(index);
    }
    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region;
        region = Assets.instance.itemAssests.key;
        Utils.drawTextureRegion(batch,region,getPosition(),new Vector2(Constants.TILE_SIZE/2,Constants.TILE_SIZE/2));
    }

    @Override
    public short getIndex() {
        return super.getIndex();
    }
}
