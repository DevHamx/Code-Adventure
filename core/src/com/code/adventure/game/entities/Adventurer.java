package com.code.adventure.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.TimeUtils;
import com.code.adventure.game.CodeAdventureGame;
import com.code.adventure.game.DeathScreen;
import com.code.adventure.game.GameplayScreen;
import com.code.adventure.game.Level;
import com.code.adventure.game.QuizScreen;
import com.code.adventure.game.overlays.WhileLoop;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Enums;
import com.code.adventure.game.util.Enums.*;
import com.code.adventure.game.util.Utils;

public class Adventurer {
    private Level level;
    public Vector2 spawnLocation;
    private Vector2 position;
    public Vector2 lastFramePosition;
    public Vector2 nextFramePosition;
    Vector2 nextFramevelocity;
    public Direction facing;
    public WalkState walkState;
    public JumpState jumpState;
    public AttackState attackState;
    public Vector2 velocity;
    private long walkStartTime;
    private long standingStartTime;
    private long jumpStartTime;
    private long attackStartTime;
    public  boolean attackingFlag;
    public  boolean walkingFlag;
    public  short count;
    public  short countAttack;
    public short pathCount;
    public Vector2 intialposition;
    public boolean swordHit;
    public boolean playerPassTheItem;
    public boolean leftButtonPressed;
    public boolean rightButtonPressed;
    public boolean jumpButtonPressed;
    public Skin skin;
    public Stage stage;
    public Dialog message;
    private Label txtMsg;
    public short pathFinal;
    public Enemy nearEnemy;
    public Item item ;
    public DelayedRemovalArray<Enemy> killedEnemies;
    public DelayedRemovalArray<Item> newItems;
    public Adventurer(Vector2 spawnLocation, Level level) {
        killedEnemies=new DelayedRemovalArray<Enemy>();
        newItems = new DelayedRemovalArray<Item>();
        this.spawnLocation = spawnLocation;
        this.level = level;
        intialposition = new Vector2();
        position = new Vector2();
        velocity = new Vector2();
        lastFramePosition = new Vector2();
        nextFramePosition = new Vector2();
        nextFramevelocity = new Vector2();
        swordHit=true;
        playerPassTheItem = false;
        skin = new Skin(Gdx.files.internal("skins/skin/craftacular-ui.json"));
        message = new Dialog("",skin);
        //final String txtMsg = " u didn't grab the key! ";
        final TextButton dialogButton = new TextButton("Ok",skin);
        Table dialogTable = new Table();
        txtMsg = new Label("",skin);
        dialogTable.add(txtMsg).row();
        dialogTable.add(dialogButton);
        message.getContentTable().add(dialogTable).grow();
        message.setSize(dialogTable.getPrefWidth(), dialogTable.getPrefHeight()+64);
        message.setPosition(Constants.TESTO_SIZE/2,Constants.TESTO_SIZE/2,Align.center|Align.bottom);


        dialogButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.instance.uiAssests.buttonClick.play();
                message.hide();
                if (nearEnemy!=null){
                    if (QuizScreen.currentLevel.equals("while"))
                    GameplayScreen.whileLoopActive=true;
                    else
                        GameplayScreen.doWhileLoopActive=true;
                }
                return true;
            }
        });
        spawn();
    }
    public void spawn() {
        position.set(spawnLocation);
        lastFramePosition.set(spawnLocation);
        nextFramePosition.set(spawnLocation);
        velocity.setZero();
        facing = Direction.RIGHT;
        jumpState = JumpState.FALLING;
        walkState = WalkState.NOT_WALKING;
        attackState = AttackState.NOT_ATTACKING;
        standingStartTime = TimeUtils.nanoTime();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float delta,DelayedRemovalArray<Platform> platforms,DelayedRemovalArray<Item> items) {

        lastFramePosition.set(position);
        nextFramePosition.y = velocity.y == 0 ? 0 : nextFramevelocity.y;
        velocity.y -= Constants.GRAVITY;
        nextFramePosition.y -= velocity.y - Constants.GRAVITY;
        position.mulAdd(velocity, delta);
        nextFramePosition.y = position.y + ((nextFramevelocity.y) * delta);
        if (position.y < Constants.KILL_PLANE) {
            Assets.instance.adventurerAssets.adventurerDeath.play();
            level.gameplayScreen.game.setScreen(new DeathScreen(level.gameplayScreen.game,level.gameplayScreen.levelIndex));
        }
        if (leftButtonPressed||Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            facing = Direction.LEFT;
            leftButtonPressed = false;
        }
        if (rightButtonPressed||Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            facing = Direction.RIGHT;
            rightButtonPressed = false;
        }
        /*
            polygons.get(Constants.STANDING_RIGHT0).setPosition(position.x,position.y);
        Polygon polygonStandingRight = polygons.get(Constants.STANDING_RIGHT0);
        for (Map.Entry<String, Polygon> entry : polygons.entrySet()) {
            String key = entry.getKey();
            Polygon value = entry.getValue();
            if (!key.equals(Constants.STANDING_RIGHT0)){
                if (Intersector.overlapConvexPolygons(value,polygonStandingRight)){
                    Gdx.app.log("hi","hit!");
                }
            }
        }
        */

        if (jumpState != JumpState.GROUNDED)
            //stop running in grass sound effect
            Assets.instance.adventurerAssets.runningInGrass.stop();

        // Land on/fall off platforms
        if (jumpState != JumpState.JUMPING) {
            if (jumpState != JumpState.RECOILING) {
                jumpState = JumpState.FALLING;
            }

            for (Platform platform : platforms) {
                if (landedOnPlatform(platform, position)) {
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    velocity.x = 0;
                    position.y = platform.position.y + Constants.PLATFORM_HEIGHT + Constants.ADVENTURER_HEAD_POSITION.y;
                    if (position.y < lastFramePosition.y)
                        Assets.instance.adventurerAssets.landingOnTheGround.play(Constants.LANDING_ON_GROUND_VOLUME);
                    if (walkState == WalkState.WALKING && position.y < lastFramePosition.y)
                        Assets.instance.adventurerAssets.runningInGrass.loop(Constants.RUNING_IN_GRASS_VOLUME);
                    break;
                }
            }
        }
        //block the player
        for (Platform platform : platforms) {
            if (blockThePlayer(platform)) {
                jumpState = JumpState.GROUNDED;
                velocity.x = 0;
                position.x = facing == Direction.RIGHT ? platform.left - Constants.ADVENTURER_HEAD_POSITION.x / 2 : platform.right + Constants.ADVENTURER_HEAD_POSITION.x / 2;
                nextFramePosition.x = position.x;
                Assets.instance.adventurerAssets.runningInGrass.stop();
                Assets.instance.adventurerAssets.hop.stop();
                startJump();
            }

        }

        // Jump
        if (Gdx.input.isKeyPressed(Input.Keys.Z)||jumpButtonPressed) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }
        if (jumpState != JumpState.RECOILING) {
/*
            boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
            boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            if (left && !right) {
                moveLeft(delta);
            } else if (right && !left) {
                moveRight(delta);
            } else {

               // walkState = WalkState.NOT_WALKING;
                //Assets.instance.adventurerAssets.runningInGrass.stop();
            }
*/
            //attack
            if (killedEnemies.size > 0) {
                newItems = new DelayedRemovalArray<Item>();
                for (int i = 0; i < items.size; i++) {
                    boolean enemyKilled = false;
                    for (int j = 0; j < killedEnemies.size; j++) {
                        if (items.get(i).equals(killedEnemies.get(j))) {
                            enemyKilled = true;
                            break;
                        }
                    }
                    if (!enemyKilled) newItems.add(items.get(i));

                }
            } else if (newItems.size == 0) {
                for (Item item : items
                ) {
                    newItems.add(item);
                }
            }
            if (nearEnemy != null){
                if (attackingFlag && countAttack == nearEnemy.getTotalHit() && nearEnemy.getTotalHit() > 0) {
                    startAttack();
                } else if (attackingFlag && countAttack != nearEnemy.getTotalHit()) {
                    final int diff = countAttack - nearEnemy.getTotalHit();
                    StringBuilder text = new StringBuilder("L\'ennemi a etait besoin de ");
                    text.append(diff > 0 ? diff + " attack de moin" :
                            diff * -1 + " attack de plus");
                    txtMsg.setText(text);
                    message.show(stage);
                    attackingFlag = false;
                } else {
                    if (nearEnemy != null && nearEnemy.getTotalHit() <= 1) {
                        killedEnemies.add(nearEnemy);
                        newItems.removeValue(nearEnemy, false);
                        nearEnemy = null;
                        Utils.firstEnemyPosition.set(Integer.MAX_VALUE, Integer.MAX_VALUE);
                        for (Item item : newItems) {
                            if (item.getType().equals(Constants.ENEMY_SPRITE) && item.getPosition().x < Utils.firstEnemyPosition.x)
                                Utils.firstEnemyPosition.set(item.getPosition());
                        }
                    }
                    attackingFlag = false;
                }
        }
            //walk
            if (walkingFlag && count != 0) {
                Vector2 pathPosition = new Vector2();
                pathPosition.set(intialposition.x, intialposition.y + Constants.ADVENTURER_HEAD_POSITION.y * 1.5f);
                if (count > pathCount) {
                    if (facing == Direction.RIGHT && position.x >= intialposition.x + ((Constants.ADVENTURER_MOVE_PER_COUNT * pathCount))) {
                        pathPosition.x = intialposition.x + (Constants.ADVENTURER_MOVE_PER_COUNT * pathCount);
                        Level.getPaths().add(new Path(pathPosition, false, pathCount));
                        pathCount++;
                    } else if (facing == Direction.LEFT && position.x <= intialposition.x - ((Constants.ADVENTURER_MOVE_PER_COUNT * pathCount))) {
                        pathPosition.x = intialposition.x - (Constants.ADVENTURER_MOVE_PER_COUNT * pathCount);
                        Level.getPaths().add(new Path(pathPosition, false, pathCount));
                        pathCount++;
                    }
                }
                if (facing == Direction.RIGHT) moveRight(delta);
                else moveLeft(delta);

            }
            if (position.x>=Utils.firstEnemyPosition.x- Constants.ENEMY_HEAD_POSITION.x&&!QuizScreen.currentLevel.equals("for")){
                Assets.instance.adventurerAssets.runningInGrass.stop();
                Assets.instance.adventurerAssets.adventurerDeath.play();
                level.gameplayScreen.game.setScreen(new DeathScreen(level.gameplayScreen.game,level.gameplayScreen.levelIndex));
            }
            else if (((position.x >= intialposition.x + Constants.ADVENTURER_MOVE_PER_COUNT * count && facing == Direction.RIGHT) ||
                    (facing == Direction.LEFT && position.x <= intialposition.x - Constants.ADVENTURER_MOVE_PER_COUNT * count)
            ) && walkingFlag) {
                walkingFlag = false;
                Assets.instance.adventurerAssets.runningInGrass.stop();
                walkState = WalkState.NOT_WALKING;
                short minIntialIndex = Short.MAX_VALUE;
                short minKeyIndex = Short.MAX_VALUE;
                int minIntialIndexItem=0;
                int minKeyIntialIndexItem=0;
                for (int i = 0; i < newItems.size; i++) {
                    float heightBtwKey_Adventurer = (position.y - Constants.ADVENTURER_HEAD_POSITION.y) - (newItems.get(i).getPosition().y - Constants.TILE_SIZE / 2);
                    heightBtwKey_Adventurer *= heightBtwKey_Adventurer < 0 ? -1 : 1;
                    Item item ;
                    if (newItems.get(i).getType().equals(Constants.KEY_SPRITE))
                        item = (Key) newItems.get(i);
                    else item = (Enemy) newItems.get(i);
                    if (item.intialIndex == pathFinal && heightBtwKey_Adventurer >= 0 && heightBtwKey_Adventurer <= Constants.TILE_SIZE) {
                        spawnLocation.x = item.getPosition().x;
                        spawnLocation.y = item.getPosition().y + Constants.ADVENTURER_HEAD_POSITION.y;
                        if (item.getType().equals(Constants.KEY_SPRITE)) {
                            items.removeValue(item, false);
                            newItems.removeIndex(i);
                            Level.setPaths(new DelayedRemovalArray<Path>());
                        }
                        else{
                            nearEnemy=((Enemy) item);
                            if (QuizScreen.currentLevel.equals("while")) {
                                GameplayScreen.whileLoop.dialogLabel.setText("cet ennemi a besoin de " + nearEnemy.getTotalHit() + " attaques pour le tuer");
                                GameplayScreen.whileLoopActive=true;
                            }
                            else if (count!=0){
                                GameplayScreen.doWhileLoop.dialogLabel.setText("cet ennemi a besoin de " + nearEnemy.getTotalHit() + " attaques pour le tuer");
                                GameplayScreen.doWhileLoopActive=true;
                            }
                        }
                        if (newItems.size == 0) level.victory = true;
                        break;
                    }
                    else{
                        if (item.intialIndex < minIntialIndex){
                            minIntialIndex =item.intialIndex;
                            minIntialIndexItem=i;
                            if (item.getType().equals(Constants.KEY_SPRITE)){
                                minKeyIndex =item.intialIndex;
                                minKeyIntialIndexItem=i;
                            }
                        }
                        else{
                        }
                        if (i == newItems.size - 1) {
                            final int keydist = minKeyIndex - pathFinal;
                            final int distanceBtwKey_pathFinal = minIntialIndex - pathFinal;
                            StringBuilder text;
                            if(QuizScreen.currentLevel.equals("for")){
                                text=new StringBuilder(" La cle etait ");
                                text.append(keydist > 0 ? keydist + " pas vers l'avant" :
                                        keydist * -1 + " pas derriere");
                            }
                            else {
                                text = new StringBuilder(newItems.get(minIntialIndexItem).getType().equals(Constants.KEY_SPRITE) ? " La cle etait " : " Le ennemi etait ");
                            text.append(distanceBtwKey_pathFinal > 0 ? distanceBtwKey_pathFinal + " pas vers l'avant" :
                                    distanceBtwKey_pathFinal * -1 + " pas derriere");
                            }
                            txtMsg.setText(text);
                            spawn();
                            level.resizeCam = true;
                            message.show(stage);
                        }

                    }

                    count = 0;
                }
            }

        }
    }

    private boolean blockThePlayer(Platform platform){
        boolean toucheFromLeft = false;
        boolean toucheFromRight = false;
        float feetPosition = position.y-Constants.ADVENTURER_HEAD_POSITION.y;
        float platHeightDiff=Constants.TILE_SIZE-Constants.PLATFORM_HEIGHT;
        if ((feetPosition-platform.bottom+platHeightDiff>=0&&
                feetPosition-platform.bottom<Constants.PLATFORM_HEIGHT)||
                    (position.y-platform.bottom>=0&&position.y-platform.bottom<Constants.PLATFORM_HEIGHT)) {
            if (facing==Direction.RIGHT)
                toucheFromLeft = (position.x + Constants.ADVENTURER_HEAD_POSITION.x/2)-platform.left>=0&&(position.x + Constants.ADVENTURER_HEAD_POSITION.x/2)-platform.left< Constants.TILE_SIZE;
            else
                toucheFromRight =platform.right - (position.x - Constants.ADVENTURER_HEAD_POSITION.x/2) >= 0&&platform.right - (position.x - Constants.ADVENTURER_HEAD_POSITION.x/2) < Constants.TILE_SIZE;
        }
         if ((position.y-platform.bottom>=0&&position.y-platform.bottom<=Constants.PLATFORM_HEIGHT)
                    &&(position.x-platform.left>=0&&position.x-platform.right<=0)){
            jumpState=JumpState.FALLING;
            position.y=platform.bottom;
            return false;
        }
        return toucheFromLeft || toucheFromRight;
    }

    boolean landedOnPlatform(Platform platform,Vector2 position) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.ADVENTURER_HEAD_POSITION.y >= platform.top &&
                position.y - Constants.ADVENTURER_HEAD_POSITION.y < platform.top) {

            float leftFoot = position.x - Constants.ADVENTURER_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.ADVENTURER_STANCE_WIDTH / 2;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);
            straddle = (platform.left > leftFoot && platform.right < rightFoot);
        }
        return leftFootIn || rightFootIn || straddle;
    }

    public void moveLeft(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
            Assets.instance.adventurerAssets.runningInGrass.loop(Constants.RUNING_IN_GRASS_VOLUME);
        }
        walkState = WalkState.WALKING;
        facing = Direction.LEFT;
        position.x -= delta * Constants.ADVENTURER_MOVE_SPEED;
        nextFramePosition.x=position.x-delta * Constants.ADVENTURER_MOVE_SPEED;
    }

    public void moveRight(float delta) {

        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
            Assets.instance.adventurerAssets.runningInGrass.loop(Constants.RUNING_IN_GRASS_VOLUME);
        }
        walkState = WalkState.WALKING;
        facing = Direction.RIGHT;
        position.x += delta * Constants.ADVENTURER_MOVE_SPEED;
        nextFramePosition.x=position.x+delta * Constants.ADVENTURER_MOVE_SPEED;
    }
    ////////////////////////////////////
    private void startAttack() {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING && attackState == AttackState.NOT_ATTACKING&&nearEnemy.getTotalHit()>0) {
            attackStartTime = TimeUtils.nanoTime();
            if (swordHit)
                Assets.instance.adventurerAssets.swordHit.play();
            else
                Assets.instance.adventurerAssets.swordSwing.play();
        }

        final float timeAfterAttackFinish = Constants.ATTACK_ANIMATION_DURATION*(jumpState==JumpState.GROUNDED?
                Assets.instance.adventurerAssets.groundAttack1Size:Assets.instance.adventurerAssets.airAttack1Size);
        if (attackState==AttackState.ATTACKING&&Utils.secondsSince(attackStartTime)>timeAfterAttackFinish){
            attackState=AttackState.NOT_ATTACKING;
            nearEnemy.setTotalHit(nearEnemy.getTotalHit()-1);
            countAttack--;
        }
        else
        attackState=AttackState.ATTACKING;
    }



    /////////////////////////////////
    private void startJump() {
        jumpState = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        Assets.instance.adventurerAssets.hop.play();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            if (Utils.secondsSince(jumpStartTime) < Constants.ADVENTURER_MAX_JUMP_DURATION) {
                velocity.y = Constants.ADVENTURER_JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
    }
    public void render(SpriteBatch batch){
        TextureRegion region;
        if (facing == Direction.RIGHT) {
            if (walkState == WalkState.WALKING&&jumpState==JumpState.GROUNDED) {
                float walkTimeSeconds = Utils.secondsSince(walkStartTime);
                region = (TextureRegion) Assets.instance.adventurerAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            }
            else {
                if (jumpState != JumpState.GROUNDED) {
                    float jumpTimeSeconds = Utils.secondsSince(jumpStartTime);
                    region = (TextureRegion) Assets.instance.adventurerAssets.fallingRightAnimation.getKeyFrame(jumpTimeSeconds);
                } else {
                    if (attackState!=AttackState.ATTACKING) {
                        float standingTimeSeconds = Utils.secondsSince(standingStartTime);
                        region = (TextureRegion) Assets.instance.adventurerAssets.standingRightAnimation.getKeyFrame(standingTimeSeconds);
                    }
                    else{
                        float attackTimeSeconds = Utils.secondsSince(attackStartTime);
                        region = (TextureRegion) Assets.instance.adventurerAssets.groundAttack1RightAnimation.getKeyFrame(attackTimeSeconds);
                    }
                }
            }
        }
        else {
            if (walkState == WalkState.WALKING&&jumpState==JumpState.GROUNDED){
                float walkTimeSeconds = Utils.secondsSince(walkStartTime);
                region = (TextureRegion) Assets.instance.adventurerAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
            }
            else{
                if (jumpState!=JumpState.GROUNDED){
                    float jumpTimeSeconds = Utils.secondsSince(jumpStartTime);
                    region = (TextureRegion) Assets.instance.adventurerAssets.fallingLeftAnimation.getKeyFrame(jumpTimeSeconds);
                }
                else {
                    if (attackState!=AttackState.ATTACKING) {
                        float standingTimeSeconds = Utils.secondsSince(standingStartTime);
                        region = (TextureRegion) Assets.instance.adventurerAssets.standingLeftAnimation.getKeyFrame(standingTimeSeconds);
                    }
                    else{
                        float attackTimeSeconds = Utils.secondsSince(attackStartTime);
                        region = (TextureRegion) Assets.instance.adventurerAssets.groundAttack1LeftAnimation.getKeyFrame(attackTimeSeconds);
                    }
                }
            }
        }
        Utils.drawTextureRegion(batch,region,position,Constants.ADVENTURER_HEAD_POSITION);
    }
}



