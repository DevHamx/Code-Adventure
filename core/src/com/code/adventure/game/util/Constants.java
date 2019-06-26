package com.code.adventure.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    //HUD
    public static final float HUD_MARGIN = 10;

    // Onscreen Controls
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 250;//344
    public static final String MOVE_LEFT_BUTTON = "leftControl";
    public static final String MOVE_RIGHT_BUTTON = "rightControl";
    public static final String JUMP_BUTTON = "jumpControl";
    public static final Vector2 BUTTON_CENTER = new Vector2(15, 15);
    public static final float BUTTON_RADIUS = 32;

    // World/Camera
    public static final float FONT_SCALE = 0.3f;
    public static final float WORLD_SIZE = 275;//275
    public static final float QUIZ_SCREEN_SIZE_MOBILE = 300;
    public static final float QUIZ_SCREEN_SIZE_NOT_MOBILE = 450;
    public static final float TESTO_SIZE = 800;
    public static final float GRAVITY = 10;
    public static final String IMAGES_ADVENTURER_ATLAS = "images/Adventurer.atlas";
    public static final String IMAGES_ITEMS_ATLAS = "levels/Sheet.atlas";
    public static final String IMAGES_ENEMY_ATLAS = "images/Enemy.atlas";
    public static final float KILL_PLANE = -200;

    //tiles
    public static final float PLATFORM_HEIGHT = 12f;
    public static final float TILE_SIZE = 16f;
    public static final String PLATFORM_SPRITE = "platform";
    public static final String KEY_SPRITE = "key";
    public static final String PATH_SPRITE = "path";
    public static final String ENEMY_SPRITE = "enemy";
    public static final String PATH_END_SPRITE = "pathEnd";
    //adventurer
    public static final Vector2 ADVENTURER_HEAD_POSITION = new Vector2(23, 27);
    public static final float ADVENTURER_MOVE_PER_COUNT = 20f;
    public static final float ADVENTURER_STANCE_WIDTH=14f;
    public static final float ADVENTURER_MOVE_SPEED=50f;
    public static final float ADVENTURER_JUMP_SPEED = 100;
    public static final float ADVENTURER_MAX_JUMP_DURATION = .5f;
    public static final float WALKING_ANIMATION_DURATION=.1f;
    public static final float STANDING_ANIMATION_DURATION=.15f;
    public static final float JUMPING_ANIMATION_DURATION=.1f;
    public static final float ATTACK_ANIMATION_DURATION=.1f;
    public static final float LANDING_ON_GROUND_VOLUME = 0.2f;
    public static final float RUNING_IN_GRASS_VOLUME = 1f;
    //right animation
    public static final String STANDING_RIGHT0="adventurer-idle-2-00-right";
    public static final String STANDING_RIGHT1="adventurer-idle-2-01-right";
    public static final String STANDING_RIGHT2="adventurer-idle-2-02-right";
    public static final String STANDING_RIGHT3="adventurer-idle-2-03-right";
    public static final String WALKING_RIGHT0="adventurer-run3-00-right";
    public static final String WALKING_RIGHT1="adventurer-run3-01-right";
    public static final String WALKING_RIGHT2="adventurer-run3-02-right";
    public static final String WALKING_RIGHT3="adventurer-run3-03-right";
    public static final String WALKING_RIGHT4="adventurer-run3-04-right";
    public static final String WALKING_RIGHT5="adventurer-run3-05-right";
    public static final String FALLING_RIGHT0="adventurer-fall-00-right";
    public static final String FALLING_RIGHT1="adventurer-fall-01-right";
    public static final String GROUND_ATTACK1_RIGHT0="adventurer-attack1-00-right";
    public static final String GROUND_ATTACK1_RIGHT1="adventurer-attack1-01-right";
    public static final String GROUND_ATTACK1_RIGHT2="adventurer-attack1-02-right";
    public static final String GROUND_ATTACK1_RIGHT3="adventurer-attack1-03-right";
    public static final String GROUND_ATTACK1_RIGHT4="adventurer-attack1-04-right";
    public static final String AIR_ATTACK1_RIGHT0="adventurer-air-attack1-00-right";
    public static final String AIR_ATTACK1_RIGHT1="adventurer-air-attack1-01-right";
    public static final String AIR_ATTACK1_RIGHT2="adventurer-air-attack1-02-right";
    public static final String AIR_ATTACK1_RIGHT3="adventurer-air-attack1-03-right";
    //left animation
    public static final String STANDING_LEFT0="adventurer-idle-2-00-left";
    public static final String STANDING_LEFT1="adventurer-idle-2-01-left";
    public static final String STANDING_LEFT2="adventurer-idle-2-02-left";
    public static final String STANDING_LEFT3="adventurer-idle-2-03-left";
    public static final String WALKING_LEFT0="adventurer-run3-00-left";
    public static final String WALKING_LEFT1="adventurer-run3-01-left";
    public static final String WALKING_LEFT2="adventurer-run3-02-left";
    public static final String WALKING_LEFT3="adventurer-run3-03-left";
    public static final String WALKING_LEFT4="adventurer-run3-04-left";
    public static final String WALKING_LEFT5="adventurer-run3-05-left";
    public static final String FALLING_LEFT0="adventurer-fall-00-left";
    public static final String FALLING_LEFT1="adventurer-fall-01-left";
    public static final String GROUND_ATTACK1_LEFT0="adventurer-attack1-00-left";
    public static final String GROUND_ATTACK1_LEFT1="adventurer-attack1-01-left";
    public static final String GROUND_ATTACK1_LEFT2="adventurer-attack1-02-left";
    public static final String GROUND_ATTACK1_LEFT3="adventurer-attack1-03-left";
    public static final String GROUND_ATTACK1_LEFT4="adventurer-attack1-04-left";
    public static final String AIR_ATTACK1_LEFT0="adventurer-air-attack1-00-left";
    public static final String AIR_ATTACK1_LEFT1="adventurer-air-attack1-01-left";
    public static final String AIR_ATTACK1_LEFT2="adventurer-air-attack1-02-left";
    public static final String AIR_ATTACK1_LEFT3="adventurer-air-attack1-03-left";

    //Enemy
    public static final float ENEMY_STANDING_ANIMATION_DURATION=.15f;
    public static final float ENEMY_DEATH_ANIMATION_DURATION=.2f;
    public static final Vector2 ENEMY_HEAD_POSITION = new Vector2(14, 21);
    public static final String ENEMY_STANDING_1="Skeleton_Idle01";
    public static final String ENEMY_STANDING_2="Skeleton_Idle02";
    public static final String ENEMY_STANDING_3="Skeleton_Idle03";
    public static final String ENEMY_STANDING_4="Skeleton_Idle04";
    public static final String ENEMY_STANDING_5="Skeleton_Idle05";
    public static final String ENEMY_STANDING_6="Skeleton_Idle06";
    public static final String ENEMY_STANDING_7="Skeleton_Idle07";
    public static final String ENEMY_STANDING_8="Skeleton_Idle08";
    public static final String ENEMY_STANDING_9="Skeleton_Idle09";
    public static final String ENEMY_STANDING_10="Skeleton_Idle10";
    public static final String ENEMY_STANDING_11="Skeleton_Idle11";
    public static final String ENEMY_DEAD_1="Skeleton_Dead01";
    public static final String ENEMY_DEAD_2="Skeleton_Dead02";
    public static final String ENEMY_DEAD_3="Skeleton_Dead03";
    public static final String ENEMY_DEAD_4="Skeleton_Dead04";
    public static final String ENEMY_DEAD_5="Skeleton_Dead05";
    public static final String ENEMY_DEAD_6="Skeleton_Dead06";
    public static final String ENEMY_DEAD_7="Skeleton_Dead07";
    public static final String ENEMY_DEAD_8="Skeleton_Dead08";
    public static final String ENEMY_DEAD_9="Skeleton_Dead09";
    public static final String ENEMY_DEAD_10="Skeleton_Dead10";
    public static final String ENEMY_DEAD_11="Skeleton_Dead11";
    public static final String ENEMY_DEAD_12="Skeleton_Dead12";


}
