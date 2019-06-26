package com.code.adventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;

public class ChooseLevelScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private Table root;
    private TextButton forLoop;
    private TextButton whileLoop;
    private TextButton doWhileLoop;
    private CodeAdventureGame game;
    public static GameplayScreen gameplayScreen;
    private Label dialog;

    public ChooseLevelScreen(CodeAdventureGame game) {
        this.game = game;
        gameplayScreen = new GameplayScreen(game, (short) 0);
    }

    @Override
    public void show() {
        stage = new Stage(new ExtendViewport(Constants.TESTO_SIZE,Constants.TESTO_SIZE));
        skin = new Skin(Gdx.files.internal("skins/skin/craftacular-ui.json"));
        root = new Table(skin);
        Gdx.input.setInputProcessor(stage);
        root.setFillParent(true);
        root.setBackground(skin.getTiledDrawable("dirt"));
        root.align(Align.center);
        stage.addActor(root);
        forLoop = new TextButton("Boucle For",skin);
        whileLoop = new TextButton("Boucle While",skin);
        doWhileLoop = new TextButton("Boucle Do While",skin);
        root.defaults().padTop(10.0f);
        root.add(forLoop).row();
        root.add(whileLoop).row();
        root.add(doWhileLoop).row();
        final Array<Actor> buttons = new Array<Actor>(3);

        buttons.add(forLoop,whileLoop,doWhileLoop);
        forLoop.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                QuizScreen.currentLevel="for";
                Assets.instance.uiAssests.buttonClick.play();

                for (Actor choice:buttons) {
                    choice.addAction(Actions.hide());
                }
                final Table mock = new Table(skin);
                mock.setFillParent(true);
                mock.align(Align.center);
                stage.addActor(mock);
                final String forText = "la boucle For est un concept de langage de programmation" +
                        " qui sert a verifier certaines conditions" +
                        " puis executez plusieurs fois un bloc de code" +
                        " tant que ces conditions sont remplies." +
                        " La boucle for est distinguée des autres instructions en boucle" +
                        " via un compteur de boucle explicite ou une variable de boucle (Index)" +
                        " qui permet au corps de la boucle de connaitre le sequencement exact de chaque iteration.";
                dialog = new Label(forText, skin);
                dialog.setWrap(true);
                mock.add(dialog).growX().padBottom(15.0f).row();
                TextButton letsGo = new TextButton("Allons-y",skin);
                TextButton goBack = new TextButton("Retourner!",skin);
                letsGo.align(Align.center);
                mock.add(letsGo).padBottom(20).row();
                mock.add(goBack);
                letsGo.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Assets.instance.uiAssests.buttonClick.play();
                        game.setScreen(gameplayScreen);
                        return true;
                    }
                });
                goBack.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Assets.instance.uiAssests.buttonClick.play();
                        mock.addAction(Actions.hide());
                        for (Actor choice:buttons) {
                            choice.addAction(Actions.show());
                        }

                        return true;
                    }
                });
                return true;
            }
        });
        whileLoop.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                QuizScreen.currentLevel="while";
                Assets.instance.uiAssests.buttonClick.play();
                for (Actor choice:buttons) {
                    choice.addAction(Actions.hide());
                }
                final Table mock = new Table(skin);
                mock.setFillParent(true);
                mock.align(Align.center);
                stage.addActor(mock);
                final String whileText = "la boucle while, francisee en boucle tant que, est une structure de controle"
                +"permettant d'executer un ensemble d'instructions de facon repetee sur la base d\'une condition booleenne."
                +"La boucle while peut etre considérée comme une repetition de l\'instruction if";
                dialog = new Label(whileText, skin);
                dialog.setWrap(true);
                mock.add(dialog).growX().padBottom(15.0f).row();
                TextButton letsGo = new TextButton("Allons-y",skin);
                TextButton goBack = new TextButton("Retourner!",skin);
                letsGo.align(Align.center);
                mock.add(letsGo).padBottom(20).row();
                mock.add(goBack);
                letsGo.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Assets.instance.uiAssests.buttonClick.play();
                        game.setScreen(gameplayScreen);
                        return true;
                    }
                });
                goBack.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Assets.instance.uiAssests.buttonClick.play();
                        mock.addAction(Actions.hide());
                        for (Actor choice:buttons) {
                            choice.addAction(Actions.show());
                        }

                        return true;
                    }
                });
                return true;
            }
        });
        doWhileLoop.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                QuizScreen.currentLevel="doWhile";
                Assets.instance.uiAssests.buttonClick.play();
                for (Actor choice:buttons) {
                    choice.addAction(Actions.hide());
                }
                final Table mock = new Table(skin);
                mock.setFillParent(true);
                mock.align(Align.center);
                stage.addActor(mock);
                final String whileText = "une boucle do while est une instruction de flux de contrôle qui exécute un bloc de code au moins une fois, puis à plusieurs reprises, ou non, en fonction d'une condition booléenne donnée à la fin du bloc.";
                dialog = new Label(whileText, skin);
                dialog.setWrap(true);
                mock.add(dialog).growX().padBottom(15.0f).row();
                TextButton letsGo = new TextButton("Allons-y",skin);
                TextButton goBack = new TextButton("Retourner!",skin);
                letsGo.align(Align.center);
                mock.add(letsGo).padBottom(20).row();
                mock.add(goBack);
                letsGo.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Assets.instance.uiAssests.buttonClick.play();
                        game.setScreen(gameplayScreen);
                        return true;
                    }
                });
                goBack.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Assets.instance.uiAssests.buttonClick.play();
                        mock.addAction(Actions.hide());
                        for (Actor choice:buttons) {
                            choice.addAction(Actions.show());
                        }

                        return true;
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
