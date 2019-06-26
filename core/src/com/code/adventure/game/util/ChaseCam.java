package com.code.adventure.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.code.adventure.game.entities.Adventurer;

public class ChaseCam {
    public Camera camera;
    public Adventurer target;
    public Vector2 mapSize;
    public ChaseCam(Camera camera, Adventurer target) {
        this.camera = camera;
        this.target = target;
    }
    public void update(){
        if (target.getPosition().x>=camera.viewportWidth/2&&(mapSize.x-target.getPosition().x)>camera.viewportWidth/2){
            camera.position.x = target.getPosition().x;
        }

        //camera.position.y = target.getPosition().y;
    }
}
