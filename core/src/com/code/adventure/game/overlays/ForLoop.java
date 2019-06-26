package com.code.adventure.game.overlays;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.code.adventure.game.Level;
import com.code.adventure.game.QuizScreen;
import com.code.adventure.game.entities.Enemy;
import com.code.adventure.game.entities.Item;
import com.code.adventure.game.entities.Key;
import com.code.adventure.game.entities.Path;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Enums;

public class ForLoop extends Stage{
    private TextButton runButton;
    public TextField indexField;
    public TextField sizeField;
    public TextField incrementValueField;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    public Skin skin;
    private short count;
    private ImageButton upButton;
    int index,size,incrementValue;
    public float screenHeight;
    private Array<Actor> textFields;
    private boolean menuDown=true;
    private TextureRegion menuButton;
    private Label dialogLabel;
    public Dialog dialog;
    private Table table;
    private Table dialogTable;
    public ForLoop(){
        screenHeight=0.0f;
        skin = Assets.instance.uiAssests.gameplaySkin;
        table = new Table(skin);
        dialogTable = new Table();
        getViewport().setWorldSize(Constants.TESTO_SIZE,Constants.TESTO_SIZE);
        upButton = new ImageButton(skin);
        upButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/UpButton.png"))));
        menuButton = new TextureRegion(new Texture(Gdx.files.internal("images/UpButton.png")));
        runButton = new TextButton("",skin);
        label1=new Label("for(int index = ",skin);
        label2=new Label(" index <",skin);
        label3=new Label(" index += ",skin);
        label4=new Label(")",skin);
        label1.setFontScale(.7f,1.3f);
        Label.LabelStyle labelStyle = skin.get("bold",Label.LabelStyle.class);
        label2.setFontScale(.7f,1.3f);
        label3.setFontScale(.7f,1.3f);
        label4.setFontScale(.7f,1.3f);
        label1.setStyle(labelStyle);
        label2.setStyle(labelStyle);
        label3.setStyle(labelStyle);
        label4.setStyle(labelStyle);
        indexField = new TextField("",skin);
        //indexField.setDisabled(true);
        indexField.setAlignment(Align.center);
        sizeField = new TextField("",skin);
        //sizeField.setDisabled(true);
        sizeField.setAlignment(Align.center);
        incrementValueField = new TextField("",skin);
        //incrementValueField.setDisabled(true);
        incrementValueField.setAlignment(Align.center);
        table.add(upButton).colspan(6).center().size(64).row();
        table.add(label1);
        table.add(indexField).size(47,30);
        table.add(label2);
        table.add(sizeField).size(47,30);
        table.add(label3);
        table.add(incrementValueField).size(47,30);
        table.add(label4);
        table.row();
        table.add(runButton).colspan(6).center().width(310);
        dialog = new Dialog("",skin);
        dialogLabel = new Label("Essayer de prendre tous les cles\n" +
                "Entrez le nombre de pas necessaire\n" +
                "pour atteindre chaque clé\n" +
                "Faites attention au nombre avec lequel vous\n"+
                "initialisez l\'index et quand\n il s\'arrete de s\'incrementer",skin);


        ScrollPane scrollPane = new ScrollPane(dialogLabel,skin);
        scrollPane.setFadeScrollBars(false);
        final TextButton dialogButton = new TextButton("Ok",skin);
        dialogTable.add(scrollPane).padBottom(30).grow().pad(10.0f).row();
        dialogTable.add(dialogButton);
        dialog.getContentTable().add(dialogTable).grow();
        dialog.setSize(dialogTable.getPrefWidth(), dialogTable.getPrefHeight()+64);
        dialog.setPosition(Constants.TESTO_SIZE/2,Constants.TESTO_SIZE/2,Align.center|Align.bottom);
        table.setPosition(Constants.TESTO_SIZE/2,-30,Align.center|Align.bottom);
        addActor(table);
        addActor(dialog);
        dialogButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.instance.uiAssests.buttonClick.play();
                dialog.hide();
                table.addAction(Actions.moveBy(0, table.getMinHeight(), .4f));
                menuButton.flip(false,true);
                upButton.getStyle().imageUp=new TextureRegionDrawable(menuButton);
                menuDown=false;
                return true;
            }
        });


        textFields = new Array<Actor>();
        textFields.add(indexField,sizeField,incrementValueField);
        if (Gdx.app.getType()==Application.ApplicationType.Android||Gdx.app.getType()==Application.ApplicationType.iOS) {
            for (Actor textField : textFields) {
                textField.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (screenHeight == 0.0f) {
                            screenHeight = getViewport().getScreenHeight() <= Constants.WORLD_SIZE * 2 ?
                                    getViewport().getScreenHeight() : Constants.WORLD_SIZE * 2;
                            table.addAction(Actions.moveBy(0, screenHeight, .4f));
                            return true;
                        }
                        return false;
                    }

                });
            }
        }
        upButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (menuDown) {
                        table.addAction(Actions.moveBy(0, table.getMinHeight(), .4f));
                        menuButton.flip(false,true);
                        upButton.getStyle().imageUp=new TextureRegionDrawable(menuButton);
                        menuDown=false;
                    }
                    else {
                        table.addAction(Actions.moveBy(0, -table.getMinHeight(), .4f));
                        menuButton.flip(false,true);
                        upButton.getStyle().imageUp=new TextureRegionDrawable(menuButton);
                        menuDown=true;
                    }
                Assets.instance.uiAssests.buttonClick.play();
                    return true;
            }
        });


        runButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                index = 0;
                size = 0;
                incrementValue = 0;
                try {
                    index = Integer.parseInt(indexField.getText());
                    size = Integer.parseInt(sizeField.getText());
                    incrementValue = Integer.parseInt(incrementValueField.getText());
                }
                catch (NumberFormatException e){
                    index = 0;
                    size = 0;
                    incrementValue = 0;
                }
                for (int i = index; i < size; i+=incrementValue) count++;
                Assets.instance.uiAssests.buttonClick.play();
                Level.getAdventurer().count=count;
                Level.getAdventurer().pathCount=1;
                Level.getAdventurer().walkingFlag = true;
                Level.getAdventurer().intialposition.x=Level.getAdventurer().getPosition().x;
                Level.getAdventurer().intialposition.y=Level.getAdventurer().getPosition().y;
                //create the path
                if (Level.getPaths().size>0)
                Level.getPaths().removeRange(0,Level.getPaths().size-1);
                Vector2 pathPosition = new Vector2();
                pathPosition.set(Level.getAdventurer().intialposition.x,Level.getAdventurer().intialposition.y+Constants.ADVENTURER_HEAD_POSITION.y*1.5f);
                pathPosition.x+=Level.getAdventurer().facing==Enums.Direction.RIGHT?
                        Constants.ADVENTURER_MOVE_PER_COUNT*Level.getAdventurer().count:
                        (-Constants.ADVENTURER_MOVE_PER_COUNT*Level.getAdventurer().count);
                Level.getAdventurer().pathFinal=count;
                for (Item item:Level.getItems()) {
                    item.intialIndex=item.getIndex();
                }

                Level.getPaths().add(new Path(pathPosition,true,count));
                count=0;
                table.addAction(Actions.moveBy(0, -table.getMinHeight(), .4f));
                menuButton.flip(false,true);
                upButton.getStyle().imageUp=new TextureRegionDrawable(menuButton);
                menuDown=true;
                int test = MathUtils.random(1,3);
                switch (test) {
                    case 1:
                        indexField.setDisabled(true);
                        indexField.setText(MathUtils.random(0,3)+"");
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
                    case 3:
                        sizeField.setText("");
                        sizeField.setDisabled(false);
                        indexField.setText(MathUtils.random(0,3)+"");
                        indexField.setDisabled(true);
                        incrementValueField.setDisabled(true);
                        incrementValueField.setText("2");
                        break;
                }
                return false;
            }
        });
    }

    public void render(){
        runButton.setText("deplacer"+(Level.getAdventurer().facing== Enums.Direction.RIGHT?"Droite":"Gauche") +"()");
        if (Level.getAdventurer().walkingFlag == false){
            table.addAction(Actions.fadeIn(0.4f));
        }
        getViewport().apply();
        act(Gdx.graphics.getDeltaTime());
        draw();
    }

    public Table getTable() {
        return table;
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
