package com.code.adventure.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.code.adventure.game.entities.Item;
import com.code.adventure.game.overlays.DoWhileLoop;
import com.code.adventure.game.overlays.GamePlayHud;
import com.code.adventure.game.overlays.OnscreenControls;
import com.code.adventure.game.overlays.ForLoop;
import com.code.adventure.game.overlays.WhileLoop;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.ChaseCam;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.LevelLoader;

public class GameplayScreen  extends ScreenAdapter implements InputProcessor {
    public CodeAdventureGame game;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private Level level;
    public static ForLoop forLoop;
    public static WhileLoop whileLoop;
    public static DoWhileLoop doWhileLoop;
    public static boolean whileLoopActive=false;
    public static boolean doWhileLoopActive=false;
    private OnscreenControls onscreenControls;
    private GamePlayHud hud;
    private ChaseCam chaseCam;
    private Array<String> levelNames = new Array<String>();
    public short levelIndex;
    private InputMultiplexer multiplexer;
    private InputProcessor whileLoopInputProcessor;
    private InputProcessor doWhileLoopInputProcessor;
    public GameplayScreen(CodeAdventureGame game,short levelIndex){
        levelNames.addAll("Map-for.tmx","Map-while.tmx","Map-do-while.tmx","Map-4.tmx");
        this.game=game;
        this.levelIndex = levelIndex;
    }
    @Override
    public void show() {
        renderer=new ShapeRenderer();
        level = new Level(this);
        forLoop = new ForLoop();
        whileLoop = new WhileLoop();
        doWhileLoop = new DoWhileLoop();
        batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);
        hud = new GamePlayHud();
        onscreenControls = new OnscreenControls();
        startNewLevel(levelIndex);
        multiplexer = new InputMultiplexer(forLoop,Level.getAdventurer().stage,this);
        if(whileLoopActive==true) {
            multiplexer.addProcessor(0, whileLoop);
            whileLoopInputProcessor = multiplexer.getProcessors().get(0);
        }
        else if (doWhileLoopActive==true){
            multiplexer.addProcessor(0, doWhileLoop);
            doWhileLoopInputProcessor = multiplexer.getProcessors().get(0);
        }
        if (onMobile())
        multiplexer.addProcessor(0,onscreenControls);
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render(float delta) {
        chaseCam.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.render(batch,renderer,delta);
       forLoop.render();
       if (whileLoopActive){
           multiplexer.addProcessor(0,whileLoop);
           whileLoopInputProcessor = multiplexer.getProcessors().get(0);
           whileLoop.render();
       }
       else if (whileLoopInputProcessor!=null)
           multiplexer.removeProcessor(whileLoopInputProcessor);
        if (doWhileLoopActive){
            multiplexer.addProcessor(0,doWhileLoop);
            doWhileLoopInputProcessor = multiplexer.getProcessors().get(0);
            doWhileLoop.render();
        }
        else if (doWhileLoopInputProcessor!=null)
            multiplexer.removeProcessor(doWhileLoopInputProcessor);
        int remainKeys=0;
        for (Item item:Level.getItems()
             ) {
            if (item.getType().equals(Constants.KEY_SPRITE)){
                remainKeys=1;
                break;
            }
        }
       if (level.victory||remainKeys==0) {
           Level.getAdventurer().walkingFlag=false;
           if (levelIndex< levelNames.size-1) {
               levelIndex = (short) (levelIndex + 1);
               game.setScreen(new QuizScreen(game));
           }
       }
        if (level.resizeCam){
           resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
           level.resizeCam=false;
        }
        int keysSize=0;
        for (Item item:Level.getItems())keysSize+=item.getType().equals(Constants.KEY_SPRITE)?1:0;
       hud.render(batch,keysSize);
       if (onMobile())
       onscreenControls.render(batch);
    }

    @Override
    public void resize(int width, int height) {

        if (width > height){
            level.viewport.update(width,width,true);
        }
        else {
            level.viewport.update(height,height,true);
        }

        hud.viewport.update(width,height,true);
        forLoop.getViewport().update(width,height,true);
        whileLoop.getViewport().update(width,height,true);
        doWhileLoop.getViewport().update(width,height,true);
        level.viewport.getCamera().position.x = chaseCam.camera.position.x;
        LevelLoader.camera.position.set(level.viewport.getCamera().position);
        LevelLoader.camera.update();

        onscreenControls.viewport.update(width, height, true);
        onscreenControls.recalculateButtonPositions();

    }
    private boolean onMobile(){
        if (Gdx.app.getType()==Application.ApplicationType.Android||Gdx.app.getType()==Application.ApplicationType.iOS)
            return true;
        else return false;
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        forLoop.dispose();
        whileLoop.dispose();
        doWhileLoop.dispose();
        batch.dispose();
        renderer.dispose();
        Assets.instance.dispose();
        level.font.dispose();
        Level.getAdventurer().skin.dispose();

    }
    public void startNewLevel(int levelIndex) {
        Assets.instance.gameAssests.backgroundMusicNature.setVolume(0.1f);
        level = LevelLoader.load("levels/"+levelNames.get(levelIndex),this);
        Level.getAdventurer().stage= forLoop;
        Level.getAdventurer().stage.addActor(Level.getAdventurer().message);
        Level.getAdventurer().message.hide();
        chaseCam = new ChaseCam(level.viewport.getCamera(), Level.getAdventurer());
        chaseCam.mapSize=LevelLoader.getMapSize();
        onscreenControls.adventurer=Level.getAdventurer();
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        if (forLoop.screenHeight!=0.0f) {
            forLoop.getTable().addAction(Actions.moveBy(0, -forLoop.screenHeight, .4f));
            forLoop.screenHeight=0.0f;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
