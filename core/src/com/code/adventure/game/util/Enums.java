package com.code.adventure.game.util;

public class Enums {

    public enum AttackState {
        NOT_ATTACKING,
        ATTACKING
    }

    public enum Direction {
        LEFT, RIGHT
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        RECOILING
    }

    public enum WalkState {
        NOT_WALKING,
        WALKING
    }
}
