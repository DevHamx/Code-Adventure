package com.code.adventure.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Utils;

public class Path extends Item {
    private boolean endPath;

    public Path(Vector2 position,boolean endPath,short index) {
        super(position,index);
        this.endPath = endPath;
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

    public boolean getEndPath(){
        return endPath;
    }
    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region;
        region = endPath?Assets.instance.itemAssests.pathEnd:Assets.instance.itemAssests.path;
        Utils.drawTextureRegion(batch,region,getPosition());
    }

    @Override
    public short getIndex() {
        return super.getIndex();
    }
}
