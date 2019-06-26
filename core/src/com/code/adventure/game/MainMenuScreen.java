package com.code.adventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;

public class MainMenuScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private Table root;
    private TextButton startGame;
    private TextButton quitGame;
    private CodeAdventureGame game;
    public MainMenuScreen(CodeAdventureGame game){
        this.game = game;
    }
    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(Constants.TESTO_SIZE,Constants.TESTO_SIZE));
        skin = new Skin(Gdx.files.internal("skins/skin/craftacular-ui.json"));
        startGame = new TextButton("Demarrer",skin);
        quitGame = new TextButton("Quitter",skin);
        Gdx.input.setInputProcessor(stage);
        root = new Table(skin);
        root.setFillParent(true);
        root.setBackground(skin.getTiledDrawable("dirt"));
        root.align(Align.top|Align.center);
        stage.addActor(root);
        root.add(new Label("CODE =DVENTURE", skin, "title")).padTop(50.0f);
        root.defaults().padTop(10.0f);
        root.row().padTop(130.0f);
        root.add(startGame);
        root.row().padTop(40.0f);
        root.add(quitGame);
        startGame.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.instance.uiAssests.buttonClick.play();
                game.setScreen(new ChooseLevelScreen(game));
                return true;
            }
        });
        quitGame.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.instance.uiAssests.buttonClick.play();
                Gdx.app.exit();
                return true;
            }
        });

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Assets.instance.gameAssests.backgroundMusicNature.play();
        Assets.instance.gameAssests.backgroundMusicNature.setLooping(true);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
