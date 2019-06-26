package com.code.adventure.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.code.adventure.game.GameplayScreen;
import com.code.adventure.game.Level;
import com.code.adventure.game.entities.Adventurer;
import com.code.adventure.game.entities.Enemy;
import com.code.adventure.game.entities.Key;
import com.code.adventure.game.entities.Platform;

import java.util.HashMap;
import java.util.Iterator;


public class LevelLoader {
    static Level level;
    public static OrthographicCamera camera;
    static TiledMap tiledMap;
    static TiledMapRenderer tiledMapRenderer;
    static MapLayer layer;
    static MapObject object;
    static MapObjects objects;
    static Vector2 mapSize;
    public static Level load(String path, GameplayScreen gameplayScreen){
        level = new Level(gameplayScreen);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        tiledMap = new TmxMapLoader().load(path);
        MapProperties prop = tiledMap.getProperties();
        mapSize = new Vector2(prop.get("width",Integer.class)*prop.get("tilewidth",Integer.class),
                prop.get("height",Integer.class)*prop.get("tileheight",Integer.class));
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Constants.WORLD_SIZE,Constants.WORLD_SIZE);
        camera.position.set(level.viewport.getCamera().position);
        camera.update();
        tiledMapRenderer = new TextureMapObjectRenderer(tiledMap);
        Iterator IteratorLayers =  tiledMap.getLayers().iterator();
        HashMap<String,Polygon> polygons = level.getPolygons();
        while (IteratorLayers.hasNext()){
            layer = (MapLayer) IteratorLayers.next();
            objects =layer.getObjects();
            Iterator iteratorObjects = objects.iterator();
            int j = 1;
            while (iteratorObjects.hasNext()) {
                object = (MapObject) iteratorObjects.next();
                String objectName;
                float objectWidth;
                objectName  = object.getName()==null?"":object.getName();
                objectWidth=Float.parseFloat(object.getProperties().get("width").toString()==null?"":
                        object.getProperties().get("width").toString()
                );
                    float objectX = Float.parseFloat(object.getProperties().get("x").toString());
                    float objectY = Float.parseFloat(object.getProperties().get("y").toString());
                    Vector2 objectPosition = new Vector2(objectX,objectY);
                    //if the object is a polygon add him to the polygons's hashMap
                    isPolygon(object,objectName,polygons);
                    //get and set adventurer
                    if (objectName.equals(Constants.STANDING_RIGHT0)) {
                        final Vector2 adventurerPosition = new Vector2(objectPosition.x+Constants.ADVENTURER_HEAD_POSITION.x,
                                objectPosition.y+Constants.ADVENTURER_HEAD_POSITION.y);
                        level.setAdventurer(new Adventurer(adventurerPosition,level));
                    }
                    //get platforms
                    else if (objectName.equals(Constants.PLATFORM_SPRITE)){
                        loadPlatforms(objectPosition,objectWidth);
                    }
                    //get awards
                    else if (objectName.equals(Constants.KEY_SPRITE)){
                        final Vector2 keyPosition = new Vector2(objectPosition.x+Constants.TILE_SIZE/2,
                                objectPosition.y+Constants.TILE_SIZE/2);
                        Level.getItems().add(new Key(keyPosition, (short) 0,Constants.KEY_SPRITE));
                    }
                    //get enemies
                    else if (objectName.equals(Constants.ENEMY_SPRITE)){
                        int totalHit = Integer.parseInt(object.getProperties().get("TotalHit").toString());
                        final Vector2 keyPosition = new Vector2(objectPosition.x+Constants.TILE_SIZE/2,
                                objectPosition.y+Constants.TILE_SIZE/2);
                        Level.getItems().add(new Enemy(keyPosition, (short) 0,Constants.ENEMY_SPRITE,totalHit));
                        if (keyPosition.x<Utils.firstEnemyPosition.x) Utils.firstEnemyPosition.set(keyPosition);
                    }
                }
            }
         level.mergePlatforms();
         return level;
    }
    public static void render(){
        LevelLoader.camera.position.set(level.viewport.getCamera().position);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public static Vector2 getMapSize(){
        return mapSize;
    }

    public static void isPolygon(MapObject object,String objectName,HashMap<String,Polygon> polygons){
        if (object instanceof PolygonMapObject){
            PolygonMapObject polygonObject = (PolygonMapObject) object;
            polygons.put(objectName,polygonObject.getPolygon());
        }
    }
    public static void loadPlatforms(Vector2 objectPosition,float objectWidth){
            DelayedRemovalArray<Platform> platforms = level.getPlatforms();
            if (platforms.size==0) {
                level.getPlatforms().add(new Platform(objectPosition));
            }
            else {
                for (int i = 0 ; i < platforms.size;i++) {
                    if (platforms.get(i).bottom == objectPosition.y&&(platforms.get(i).right-objectPosition.x==0||platforms.get(i).left==objectPosition.x+objectWidth)){
                        if (objectPosition.x - platforms.get(i).left <= platforms.get(i).getWidth()&&objectPosition.x - platforms.get(i).left>=0){
                            platforms.get(i).widthPlus(objectWidth);
                        }
                        else if (objectPosition.x - platforms.get(i).left >= platforms.get(i).getWidth()*-1&&objectPosition.x - platforms.get(i).left<=0){
                            platforms.get(i).setPosition(objectPosition);
                            platforms.get(i).widthPlus(objectWidth);
                        }
                        break;
                    }
                    else if (i == platforms.size-1){
                        level.getPlatforms().add(new Platform(objectPosition));
                        break;
                    }
                }
            }
        }
}
