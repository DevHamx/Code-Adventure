package com.code.adventure.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.code.adventure.game.Level;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Utils;

public class Enemy extends Item{
    public short intialIndex;
    private long standingStartTime;
    private long hitStartTime;
    public long deathStartTime;
    private int totalHit;
    private int animation=1;
    boolean startDeathAnimation =true;


    public Enemy(Vector2 position, short index,String type,int totalHit) {
        super(new Vector2(position.x,position.y+Constants.ENEMY_HEAD_POSITION.y/2), index,type);
        standingStartTime = TimeUtils.nanoTime();
        this.totalHit=totalHit;

    }
    @Override
    public void update(float delta){
        if (totalHit==0) {
            if (startDeathAnimation){
                deathStartTime=TimeUtils.nanoTime();
                startDeathAnimation =false;
            }
            animation = 2;
        }

    }

    @Override
    public boolean isStartDeathSound() {
        return super.isStartDeathSound();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region = null;
        switch (animation) {
            case 1:
                float standingTimeSeconds = Utils.secondsSince(standingStartTime);
                region = (TextureRegion) Assets.instance.enemyAssests.standingAnimation.getKeyFrame(standingTimeSeconds);
                break;
            case 2:
                float deathTimeSeconds = Utils.secondsSince(deathStartTime);
                region = (TextureRegion) Assets.instance.enemyAssests.deadAnimation.getKeyFrame(deathTimeSeconds);
                if (region==Assets.instance.enemyAssests.DeathRegion1&&startDeathSound) {
                    Assets.instance.enemyAssests.death.play();
                    startDeathSound=false;
                }
                break;
        }
        Utils.drawTextureRegion(batch,region,getPosition(),new Vector2(Constants.ENEMY_HEAD_POSITION.x/2,Constants.ENEMY_HEAD_POSITION.y));


    }

    public int getTotalHit() {
        return totalHit;
    }

    public void setTotalHit(int totalHit) {
        this.totalHit = totalHit;
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
    public short getIndex() {
        return super.getIndex();
    }

    @Override
    public void setIndex(short index) {
        super.setIndex(index);
    }



}
