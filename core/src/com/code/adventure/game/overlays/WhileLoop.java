package com.code.adventure.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.code.adventure.game.GameplayScreen;
import com.code.adventure.game.Level;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;

public class WhileLoop extends Stage {
    public Skin skin;
    public Label dialogLabel;
    public Table root;
    public TextField indexField;
    public TextField sizeField;
    public TextField incrementValueField;
    private Label IndexLabel;
    private Label whileLabel;
    private Label incrementLabel;
    private Label rightBracket;
    private Label semiColon;
    private Label semiColon2;
    private Label leftCurlybracket;
    private Label rightCurlybracket;
    private Label methode;
    int index,size,incrementValue;
    private short count;

    public WhileLoop(){
        getViewport().setWorldSize(Constants.TESTO_SIZE,Constants.TESTO_SIZE);
        skin = Assets.instance.uiAssests.gameplaySkin;
        dialogLabel = new Label("",skin);
        IndexLabel =new Label("index   = ",skin);
        methode = new Label("Attack();",skin);
        whileLabel =new Label("while ( index <= ",skin);
        incrementLabel =new Label("index += ",skin);
        rightBracket =new Label(")",skin);
        semiColon =new Label(";",skin);
        semiColon2 =new Label(";",skin);
        leftCurlybracket =new Label("{",skin);
        rightCurlybracket =new Label("}",skin);
        rightBracket.setFontScale(.7f,1.3f);
        semiColon.setFontScale(.7f,1.3f);
        IndexLabel.setFontScale(.7f,1.3f);
        Label.LabelStyle labelStyle = skin.get("bold",Label.LabelStyle.class);
        whileLabel.setFontScale(.7f,1.3f);
        incrementLabel.setFontScale(.7f,1.3f);
        rightCurlybracket.setFontScale(.7f,1.3f);
        leftCurlybracket.setFontScale(.7f,1.3f);
        methode.setFontScale(.7f,1.3f);
        semiColon2.setFontScale(.7f,1.3f);
        semiColon2.setStyle(labelStyle);
        methode.setStyle(labelStyle);
        rightCurlybracket.setStyle(labelStyle);
        leftCurlybracket.setStyle(labelStyle);
        IndexLabel.setStyle(labelStyle);
        whileLabel.setStyle(labelStyle);
        incrementLabel.setStyle(labelStyle);
        rightBracket.setStyle(labelStyle);
        semiColon.setStyle(labelStyle);
        indexField = new TextField("",skin);
        indexField.setAlignment(Align.center);
        sizeField = new TextField("",skin);
        sizeField.setAlignment(Align.center);
        incrementValueField = new TextField("",skin);
        incrementValueField.setAlignment(Align.center);
        final TextButton dialogButton1 = new TextButton("run",skin);
        final Table dialogTable = new Table();
        final Table question = new Table();
        final Table button = new Table();
        question.add(dialogLabel);
        dialogTable.add(IndexLabel);
        dialogTable.add(indexField).size(47,30);
        dialogTable.add(semiColon).row();
        dialogTable.add(whileLabel).colspan(2);
        dialogTable.add(sizeField).size(47,30).padRight(10).padLeft(10);
        dialogTable.add(rightBracket).row();
        dialogTable.add(leftCurlybracket).row();
        dialogTable.add(methode).row();
        dialogTable.add(incrementLabel);
        dialogTable.add(incrementValueField).size(47,30);
        dialogTable.add(semiColon2).row();
        dialogTable.add(rightCurlybracket);
        button.add(dialogButton1).padBottom(30);
        dialogTable.setPosition(Constants.TESTO_SIZE/2,Constants.TESTO_SIZE/2,Align.center|Align.bottom);
        root = new Table();
        addActor(root);
        root.setFillParent(true);
        root.setBackground(skin.getTiledDrawable("dirt"));
        root.align(Align.center);
        root.add(question).growX().padBottom(20).padTop(20).row();
        root.add(dialogTable).growX().padBottom(20).padTop(20).row();
        root.add(button);

        dialogButton1.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Assets.instance.uiAssests.buttonClick.play();
                    index = 1;
                    size = 0;
                    incrementValue = 0;
                    try {
                        index = Integer.parseInt(indexField.getText());
                        size = Integer.parseInt(sizeField.getText());
                        incrementValue = Integer.parseInt(incrementValueField.getText());
                    }
                    catch (NumberFormatException e){
                        index = 1;
                        size = 0;
                        incrementValue = 0;
                    }
                    int i = index;
                    while(i<=size){
                        count++;
                        i+=incrementValue;
                    }
                    Level.getAdventurer().countAttack=count;
                    count=0;

                    indexField.setText("");
                    sizeField.setText("");
                    incrementValueField.setText("");
                    GameplayScreen.whileLoopActive=false;
                    //start attack
                    setKeyboardFocus(GameplayScreen.forLoop.indexField);
                    GameplayScreen.forLoop.indexField.getOnscreenKeyboard().show(true);
                    Level.getAdventurer().attackingFlag=true;
                    int test = MathUtils.random(1,2);
                    switch (test) {
                        case 1:
                            indexField.setText(MathUtils.random(0,3)+"");
                            indexField.setDisabled(true);
                            sizeField.setText("");
                            sizeField.setDisabled(false);
                            incrementValueField.setDisabled(false);
                            incrementValueField.setText("");
                            break;
                        case 2:
                            sizeField.setText(MathUtils.random(7,13)+"");
                            sizeField.setDisabled(true);
                            indexField.setText("");
                            indexField.setDisabled(false);
                            incrementValueField.setDisabled(false);
                            incrementValueField.setText("");
                            break;
                    }
                    return true;
                }
            });
    }
    public void render(){
        getViewport().apply();
        act(Gdx.graphics.getDeltaTime());
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
