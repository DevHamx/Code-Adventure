package com.code.adventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.code.adventure.game.entities.Adventurer;
import com.code.adventure.game.entities.Enemy;
import com.code.adventure.game.entities.Item;
import com.code.adventure.game.entities.Key;
import com.code.adventure.game.entities.Path;
import com.code.adventure.game.entities.Platform;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Enums;
import com.code.adventure.game.util.LevelLoader;
import com.code.adventure.game.util.Utils;

import java.util.HashMap;

public class Level {

    public static final String TAG = Level.class.getName();
    public Viewport viewport;
    static private Adventurer adventurer;
    private DelayedRemovalArray<Platform> platforms;
    private static HashMap<String,Polygon> polygons;
    private static DelayedRemovalArray<Path> paths;
    private static DelayedRemovalArray<Item> items;
    public boolean victory;
    public BitmapFont font;
    public boolean resizeCam;
    public GameplayScreen gameplayScreen;

    public Level(GameplayScreen gameplayScreen){
        this.gameplayScreen = gameplayScreen;
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        adventurer = new Adventurer(new Vector2(50,50),this);
        platforms = new DelayedRemovalArray<Platform>();
        polygons = new HashMap<String, Polygon>();
        paths = new DelayedRemovalArray<Path>();
        items = new DelayedRemovalArray<Item>();
        victory=false;
        font =new BitmapFont(Gdx.files.internal("skins/skin/font.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(Constants.FONT_SCALE);
        font.setColor(Color.BLACK);
    }



    public void render(SpriteBatch batch, ShapeRenderer renderer,float delta){
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        LevelLoader.render();
        batch.begin();
        if (!victory) {
            adventurer.update(delta, platforms, items);
            adventurer.render(batch);
        }
        for (Path path:paths) {
            path.render(batch);
            font.draw(batch,path.getIndex()+"",path.getPosition().x,path.getPosition().y+20);
        }
        for (Item item:items) {
            if (item.getType().equals(Constants.KEY_SPRITE)){
                float keyIndex = (item.getPosition().x - adventurer.getPosition().x) / Constants.ADVENTURER_MOVE_PER_COUNT;
                keyIndex *=keyIndex<0?-1:1;

                item.setIndex((short)keyIndex);
                item.render(batch);
                font.draw(batch,item.getIndex()+"",item.getPosition().x,item.getPosition().y+Constants.TILE_SIZE*2);
            }
            else if (item.getType().equals(Constants.ENEMY_SPRITE)&&!QuizScreen.currentLevel.equals("for")){
                float enemyIndex = (item.getPosition().x - adventurer.getPosition().x) / Constants.ADVENTURER_MOVE_PER_COUNT;
                enemyIndex *=enemyIndex<0?-1:1;

                item.setIndex((short) (enemyIndex-1));
                item.update(delta);
                item.render(batch);
                if (item.isStartDeathSound())
                font.draw(batch,item.getIndex()+"",item.getPosition().x, (float) (item.getPosition().y+Constants.ENEMY_HEAD_POSITION.y*1.5));

            }
        }
        batch.setColor(Color.WHITE);
        batch.end();
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
/*
        for (Polygon polygon:polygons.values()) {
            renderer.polygon(polygon.getTransformedVertices());

        }

        for (Platform platform:platforms) {
            renderer.rect(platform.left,platform.bottom,platform.width,Constants.PLATFORM_HEIGHT);
        }
*/
        renderer.end();
    }

    public static DelayedRemovalArray<Path> getPaths(){
        return paths;
    }

    public static void setPaths(DelayedRemovalArray<Path> paths) {
        Level.paths = paths;
    }

    public static void setItems(DelayedRemovalArray<Item> items) {
        Level.items = items;
    }

    public static DelayedRemovalArray<Item> getItems() {
        return items;
    }

    public Viewport getViewport() {
        return viewport;
    }
    static public Adventurer getAdventurer(){return adventurer;}
    public void setAdventurer(Adventurer adventurer) {this.adventurer = adventurer;}
    public DelayedRemovalArray<Platform> getPlatforms() {return platforms;}
    public HashMap<String,Polygon> getPolygons(){
        return polygons;
    }
    public void mergePlatforms(){
        for (int i = 0 ; i < platforms.size ; i++){
            for (int j = i ; j < platforms.size; j++){
                if (platforms.get(i).bottom==platforms.get(j).bottom) {
                    if (platforms.get(i).right == platforms.get(j).left) {
                        platforms.get(i).widthPlus(platforms.get(j).width);
                        platforms.removeIndex(j);
                    } else if (platforms.get(i).left == platforms.get(j).right) {
                        platforms.get(i).setPosition(platforms.get(j).position);
                        platforms.get(i).widthPlus(platforms.get(j).width);
                        platforms.removeIndex(j);
                    }
                }
            }
        }
    }

}
