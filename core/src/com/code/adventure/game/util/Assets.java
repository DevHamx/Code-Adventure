package com.code.adventure.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    private AssetManager assetManager;
    public AdventurerAssets adventurerAssets;
    public TextureAtlas adventureAtlas;
    public TextureAtlas itemsAtlas;
    public TextureAtlas enemyAtlas;
    public GameAssests gameAssests;
    public UiAssests uiAssests;
    public ItemAssests itemAssests;
    public OnscreenControlsAssets onscreenControlsAssets;
    public EnemyAssests enemyAssests;
    private Assets() {
    }

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.IMAGES_ADVENTURER_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.IMAGES_ITEMS_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.IMAGES_ENEMY_ATLAS, TextureAtlas.class);
        assetManager.load("sound/sword-swing.mp3",Sound.class);
        assetManager.load("sound/sword-hit.mp3",Sound.class);
        assetManager.load("sound/landingOnGround.mp3",Sound.class);
        assetManager.load("sound/runningInGrass.mp3",Sound.class);
        assetManager.load("sound/hop.mp3",Sound.class);
        assetManager.load("sound/enemeDeath.mp3",Sound.class);
        assetManager.load("sound/button-click.mp3",Sound.class);
        assetManager.load("sound/adventurer-death.mp3",Sound.class);
        assetManager.load("music/Distant-Mountains-Looping.mp3",Music.class);
        assetManager.finishLoading();
        adventureAtlas = assetManager.get(Constants.IMAGES_ADVENTURER_ATLAS);
        itemsAtlas = assetManager.get(Constants.IMAGES_ITEMS_ATLAS);
        enemyAtlas = assetManager.get(Constants.IMAGES_ENEMY_ATLAS);
        adventurerAssets = new AdventurerAssets(adventureAtlas);
        onscreenControlsAssets = new OnscreenControlsAssets(adventureAtlas);
        itemAssests = new ItemAssests(itemsAtlas);
        enemyAssests = new EnemyAssests(enemyAtlas);
        gameAssests = new GameAssests();
        uiAssests = new UiAssests();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);

    }

    @Override
    public void dispose() {
        assetManager.dispose();
        adventurerAssets.runningInGrass.dispose();
        gameAssests.backgroundMusicNature.dispose();
        adventurerAssets.swordSwing.dispose();
        adventurerAssets.swordHit.dispose();
        adventurerAssets.landingOnTheGround.dispose();
        adventurerAssets.hop.dispose();
        uiAssests.quizSkin.dispose();
        uiAssests.gameplaySkin.dispose();
        enemyAssests.death.dispose();
        adventurerAssets.adventurerDeath.dispose();
    }

    public class UiAssests {
        public final Sound buttonClick;
        public Skin quizSkin;
        public Skin gameplaySkin;
        public UiAssests(){
            buttonClick = assetManager.get("sound/button-click.mp3",Sound.class);
            quizSkin = new Skin(Gdx.files.internal("skins/quiz/level-plane-ui.json"));
            gameplaySkin = new Skin(Gdx.files.internal("skins/skin/craftacular-ui.json"));

        }
    }

    public class GameAssests {
        public Music backgroundMusicNature;
        public GameAssests(){
           backgroundMusicNature = assetManager.get("music/Distant-Mountains-Looping.mp3",Music.class);
        }
    }

    public class ItemAssests {
        public final TextureRegion path;
        public final TextureRegion pathEnd;
        public final TextureRegion key;

        public ItemAssests(TextureAtlas atlas){
            path = atlas.findRegion(Constants.PATH_SPRITE);
            pathEnd = atlas.findRegion(Constants.PATH_END_SPRITE);
            key = atlas.findRegion(Constants.KEY_SPRITE);
        }
    }

    public class EnemyAssests{
        public final Animation standingAnimation;
        public final Animation deadAnimation;
        public final Sound death;
        public AtlasRegion DeathRegion1;

        public EnemyAssests(TextureAtlas atlas){
            death = assetManager.get("sound/enemeDeath.mp3",Sound.class);
            DeathRegion1=atlas.findRegion(Constants.ENEMY_DEAD_1);
            Array<AtlasRegion> standingArray = new Array<AtlasRegion>();
            standingArray.add(
                    atlas.findRegion(Constants.ENEMY_STANDING_1),
                    atlas.findRegion(Constants.ENEMY_STANDING_2),
                    atlas.findRegion(Constants.ENEMY_STANDING_3),
                    atlas.findRegion(Constants.ENEMY_STANDING_4)
            );

            standingArray.add(
                    atlas.findRegion(Constants.ENEMY_STANDING_5),
                    atlas.findRegion(Constants.ENEMY_STANDING_6),
                    atlas.findRegion(Constants.ENEMY_STANDING_7),
                    atlas.findRegion(Constants.ENEMY_STANDING_8)
            );
            standingArray.add(
                    atlas.findRegion(Constants.ENEMY_STANDING_9),
                    atlas.findRegion(Constants.ENEMY_STANDING_10),
                    atlas.findRegion(Constants.ENEMY_STANDING_11)
            );
            standingAnimation = new Animation(Constants.ENEMY_STANDING_ANIMATION_DURATION,standingArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> deadArray = new Array<AtlasRegion>();
            deadArray.add(
                    atlas.findRegion(Constants.ENEMY_DEAD_1),
                    atlas.findRegion(Constants.ENEMY_DEAD_2),
                    atlas.findRegion(Constants.ENEMY_DEAD_3),
                    atlas.findRegion(Constants.ENEMY_DEAD_4)
            );
            deadArray.add(
                    atlas.findRegion(Constants.ENEMY_DEAD_5),
                    atlas.findRegion(Constants.ENEMY_DEAD_6),
                    atlas.findRegion(Constants.ENEMY_DEAD_7),
                    atlas.findRegion(Constants.ENEMY_DEAD_8)
            );
            deadArray.add(
                    atlas.findRegion(Constants.ENEMY_DEAD_9),
                    atlas.findRegion(Constants.ENEMY_DEAD_10),
                    atlas.findRegion(Constants.ENEMY_DEAD_11),
                    atlas.findRegion(Constants.ENEMY_DEAD_12)
                    );
            deadAnimation = new Animation(Constants.ENEMY_DEATH_ANIMATION_DURATION,deadArray,Animation.PlayMode.NORMAL);


        }
    }

    public class AdventurerAssets{
        public final Animation standingRightAnimation;
        public final Animation standingLeftAnimation;
        public final Animation walkingLeftAnimation;
        public final Animation walkingRightAnimation;
        public final Animation fallingRightAnimation;
        public final Animation fallingLeftAnimation;
        public final Animation groundAttack1RightAnimation;
        public final Animation airAttack1RightAnimation;
        public final Animation groundAttack1LeftAnimation;
        public final Animation airAttack1LeftAnimation;
        public final Sound runningInGrass;
        public final Sound swordSwing;
        public final Sound swordHit;
        public final Sound landingOnTheGround;
        public final Sound hop;
        public final Sound adventurerDeath;
        public short groundAttack1Size;
        public short airAttack1Size;
        public AdventurerAssets(TextureAtlas atlas){
            runningInGrass = assetManager.get("sound/runningInGrass.mp3",Sound.class);
            swordSwing = assetManager.get("sound/sword-swing.mp3",Sound.class);
            swordHit = assetManager.get("sound/sword-hit.mp3",Sound.class);
            landingOnTheGround = assetManager.get("sound/landingOnGround.mp3",Sound.class);
            hop = assetManager.get("sound/hop.mp3",Sound.class);
            adventurerDeath = assetManager.get("sound/adventurer-death.mp3",Sound.class);

            Array<AtlasRegion> airAttack1LeftArray = new Array<AtlasRegion>();
            airAttack1LeftArray.add(
                    atlas.findRegion(Constants.AIR_ATTACK1_LEFT0),
                    atlas.findRegion(Constants.AIR_ATTACK1_LEFT1),
                    atlas.findRegion(Constants.AIR_ATTACK1_LEFT2),
                    atlas.findRegion(Constants.AIR_ATTACK1_LEFT3)
            );
            airAttack1LeftAnimation = new Animation(Constants.STANDING_ANIMATION_DURATION,airAttack1LeftArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> airAttack1RightArray = new Array<AtlasRegion>();
            airAttack1RightArray.add(
                    atlas.findRegion(Constants.AIR_ATTACK1_RIGHT0),
                    atlas.findRegion(Constants.AIR_ATTACK1_RIGHT1),
                    atlas.findRegion(Constants.AIR_ATTACK1_RIGHT2),
                    atlas.findRegion(Constants.AIR_ATTACK1_RIGHT3)
            );
            airAttack1RightAnimation = new Animation(Constants.STANDING_ANIMATION_DURATION,airAttack1RightArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> groundAttack1RightArray = new Array<AtlasRegion>();
            groundAttack1RightArray.add(
                    atlas.findRegion(Constants.GROUND_ATTACK1_RIGHT0),
                    atlas.findRegion(Constants.GROUND_ATTACK1_RIGHT1),
                    atlas.findRegion(Constants.GROUND_ATTACK1_RIGHT2),
                    atlas.findRegion(Constants.GROUND_ATTACK1_RIGHT3)
            );
            groundAttack1RightArray.add(atlas.findRegion(Constants.GROUND_ATTACK1_RIGHT4));
            groundAttack1RightAnimation = new Animation(Constants.STANDING_ANIMATION_DURATION,groundAttack1RightArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> groundAttack1LeftArray = new Array<AtlasRegion>();
            groundAttack1LeftArray.add(
                    atlas.findRegion(Constants.GROUND_ATTACK1_LEFT0),
                    atlas.findRegion(Constants.GROUND_ATTACK1_LEFT1),
                    atlas.findRegion(Constants.GROUND_ATTACK1_LEFT2),
                    atlas.findRegion(Constants.GROUND_ATTACK1_LEFT3)
            );
            groundAttack1LeftArray.add(atlas.findRegion(Constants.GROUND_ATTACK1_LEFT4));
            groundAttack1LeftAnimation = new Animation(Constants.STANDING_ANIMATION_DURATION,groundAttack1LeftArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> standingLeftArray = new Array<AtlasRegion>();
            standingLeftArray.add(
                    atlas.findRegion(Constants.STANDING_LEFT0),
                    atlas.findRegion(Constants.STANDING_LEFT1),
                    atlas.findRegion(Constants.STANDING_LEFT2),
                    atlas.findRegion(Constants.STANDING_LEFT3)
            );
            standingLeftAnimation = new Animation(Constants.STANDING_ANIMATION_DURATION,standingLeftArray,Animation.PlayMode.LOOP);
            Array<AtlasRegion> walkingLeftArray = new Array<AtlasRegion>();
            walkingLeftArray.add(
                    atlas.findRegion(Constants.WALKING_LEFT0),
                    atlas.findRegion(Constants.WALKING_LEFT1),
                    atlas.findRegion(Constants.WALKING_LEFT2),
                    atlas.findRegion(Constants.WALKING_LEFT3)
                    );
            walkingLeftArray.add(
                    atlas.findRegion(Constants.WALKING_LEFT4),
                    atlas.findRegion(Constants.WALKING_LEFT5)
            );

            Array<AtlasRegion> standingRightArray = new Array<AtlasRegion>();
            standingRightArray.add(
                    atlas.findRegion(Constants.STANDING_RIGHT0),
                    atlas.findRegion(Constants.STANDING_RIGHT1),
                    atlas.findRegion(Constants.STANDING_RIGHT2),
                    atlas.findRegion(Constants.STANDING_RIGHT3)
            );
            standingRightAnimation = new Animation(Constants.STANDING_ANIMATION_DURATION,standingRightArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> walkingRightArray = new Array<AtlasRegion>();
            walkingRightArray.add(
                    atlas.findRegion(Constants.WALKING_RIGHT0),
                    atlas.findRegion(Constants.WALKING_RIGHT1),
                    atlas.findRegion(Constants.WALKING_RIGHT2),
                    atlas.findRegion(Constants.WALKING_RIGHT3)
            );
            walkingLeftAnimation = new Animation(Constants.WALKING_ANIMATION_DURATION,walkingLeftArray,Animation.PlayMode.LOOP);

            walkingRightArray.add(
                    atlas.findRegion(Constants.WALKING_RIGHT4),
                    atlas.findRegion(Constants.WALKING_RIGHT5)
            );

            walkingRightAnimation = new Animation(Constants.WALKING_ANIMATION_DURATION,walkingRightArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> jumpingRightArray = new Array<AtlasRegion>();

            jumpingRightArray.add(
                    atlas.findRegion(Constants.FALLING_RIGHT0),
                    atlas.findRegion(Constants.FALLING_RIGHT1)
            );
            fallingRightAnimation = new Animation(Constants.JUMPING_ANIMATION_DURATION,jumpingRightArray,Animation.PlayMode.LOOP);

            Array<AtlasRegion> jumpingLeftArray = new Array<AtlasRegion>();

            jumpingLeftArray.add(
                    atlas.findRegion(Constants.FALLING_LEFT0),
                    atlas.findRegion(Constants.FALLING_LEFT1)
            );
            fallingLeftAnimation = new Animation(Constants.JUMPING_ANIMATION_DURATION,jumpingLeftArray,Animation.PlayMode.LOOP);
            groundAttack1Size = (short) groundAttack1RightArray.size;
            airAttack1Size = (short) airAttack1RightArray.size;
        }
    }
    public class OnscreenControlsAssets {

        public final AtlasRegion moveRight;
        public final AtlasRegion moveLeft;
        public final AtlasRegion jump;


        public OnscreenControlsAssets(TextureAtlas atlas) {
            moveRight = atlas.findRegion(Constants.MOVE_RIGHT_BUTTON);
            moveLeft = atlas.findRegion(Constants.MOVE_LEFT_BUTTON);
            jump = atlas.findRegion(Constants.JUMP_BUTTON);

        }


    }
}
