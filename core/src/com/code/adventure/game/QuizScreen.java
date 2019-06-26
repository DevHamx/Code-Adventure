package com.code.adventure.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;

import java.util.HashMap;
import java.util.Map;

public class QuizScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private Table root;
    private Element quiz;
    private Table questions;
    private Table answers;
    private Table bottom;
    private Label question;
    private ButtonGroup answersButtons;
    private TextButton submit;
    private CodeAdventureGame game;
    public static String currentLevel="while";
    private int questionIndex =0;
    private int correctAnswerIndex=Integer.MAX_VALUE;
    public QuizScreen(CodeAdventureGame game) {
        this.game=game;
    }

    @Override
    public void show() {
        /*
        if (Gdx.app.getType()==Application.ApplicationType.Android||Gdx.app.getType()==Application.ApplicationType.iOS){
            stage = new Stage(new ExtendViewport(Constants.QUIZ_SCREEN_SIZE_MOBILE,Constants.QUIZ_SCREEN_SIZE_MOBILE));
            stage.getCamera().rotate(270, 0, 0, 1);
        }
        else*/
            stage = new Stage(new ExtendViewport(Constants.QUIZ_SCREEN_SIZE_NOT_MOBILE,Constants.QUIZ_SCREEN_SIZE_NOT_MOBILE));
        skin = Assets.instance.uiAssests.quizSkin;
        answersButtons =new ButtonGroup<Button>();
        root = new Table();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(root);
        root.setFillParent(true);
        root.align(Align.center);
        questions = new Table();
        answers = new Table();
        bottom = new Table();
        submit = new TextButton("Continue", skin,"c3");
        bottom.add(submit);
        question = new Label("",skin);

        submit.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (correctAnswerIndex==answersButtons.getCheckedIndex()) {
                    //finish
                    if (questionIndex+1==quiz.getChildCount()){
                        currentLevel="while";
                        ChooseLevelScreen.gameplayScreen.startNewLevel(ChooseLevelScreen.gameplayScreen.levelIndex);
                        game.setScreen(ChooseLevelScreen.gameplayScreen);
                    }
                    //correct
                    else {
                        questionIndex++;
                        loadQuestion(questionIndex);
                    }
                }
                else{
                    //wrong
                    CheckBox correctCheckbox= (CheckBox) answersButtons.getButtons().get(correctAnswerIndex);
                    correctCheckbox.setChecked(true);
                }
                return true;
            }
        });
        quiz = new XmlReader().parse(Gdx.files.internal("quizzes/"+currentLevel+"_quiz.xml"));
        loadQuestion(questionIndex);
        questions.add(question);
        root.add(questions).growX().padBottom(20).padTop(20).row();
        root.add(answers).growX().padBottom(20).padTop(20).row();
        root.add(bottom);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(5/255.0f, 107/255.0f, 153/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
    public void loadQuestion(int index){
        Element item = quiz.getChild(index);

        question.setText(item.get("question"));

        CheckBox checkBox;
        answers.clearChildren();
        answersButtons.clear();
        for (int i = 0 ; i < item.getChildrenByName("answer").size ; i++) {
            if (item.getChildrenByName("answer").get(i).hasAttribute("correct"))
                correctAnswerIndex=item.getChildrenByName("answer").get(i).getInt("correct")==1?i:correctAnswerIndex;

            checkBox = new CheckBox(item.getChildrenByName("answer").get(i).getText(), skin, "radio-1");
            answersButtons.add(checkBox);
            answers.add(checkBox).padBottom(10).row();

        }


    }
}
