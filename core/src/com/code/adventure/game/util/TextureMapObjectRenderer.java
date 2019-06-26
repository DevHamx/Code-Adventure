package com.code.adventure.game.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.code.adventure.game.Level;

public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer{


    public TextureMapObjectRenderer(TiledMap map) {
        super(map);
    }

    public TextureMapObjectRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public TextureMapObjectRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public TextureMapObjectRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    @Override
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            batch.draw(
                    textureObject.getTextureRegion(),
                    textureObject.getX(),
                    textureObject.getY(),
                    0,
                    0,
                    Float.parseFloat(object.getProperties().get("width").toString()),
                    Float.parseFloat(object.getProperties().get("height").toString()),
                    1,
                    1,
                    textureObject.getRotation());
        }
        }
}
