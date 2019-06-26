package com.code.adventure.game.overlays;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.code.adventure.game.entities.Adventurer;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;
import com.code.adventure.game.util.Utils;


public class OnscreenControls extends InputAdapter {

    public static final String TAG = OnscreenControls.class.getName();

    public final Viewport viewport;
    public Adventurer adventurer;
    private Vector2 moveLeftCenter;
    private Vector2 moveRightCenter;
    private Vector2 jumpCenter;

    public OnscreenControls() {
        this.viewport = new ExtendViewport(
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE);
        moveLeftCenter = new Vector2();
        moveRightCenter = new Vector2();
        jumpCenter = new Vector2();
        recalculateButtonPositions();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (viewportPosition.dst(moveLeftCenter) < Constants.BUTTON_RADIUS) {

            adventurer.leftButtonPressed = true;

        } else if (viewportPosition.dst(moveRightCenter) < Constants.BUTTON_RADIUS) {

            adventurer.rightButtonPressed = true;

        }
        else if (viewportPosition.dst(jumpCenter) < Constants.BUTTON_RADIUS) {

            adventurer.jumpButtonPressed = true;

        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));
        if (viewportPosition.dst(jumpCenter) < Constants.BUTTON_RADIUS) {

            adventurer.jumpButtonPressed = false;

        }
        return super.touchUp(screenX, screenY, pointer, button);
    }


    public void render(SpriteBatch batch) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();



        Utils.drawTextureRegion(
                batch,
                Assets.instance.onscreenControlsAssets.moveLeft,
                moveLeftCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onscreenControlsAssets.moveRight,
                moveRightCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onscreenControlsAssets.jump,
                jumpCenter,
                Constants.BUTTON_CENTER
        );

        batch.end();
    }

    public void recalculateButtonPositions() {
        moveLeftCenter.set(Constants.BUTTON_RADIUS * 3 / 4, Constants.BUTTON_RADIUS);
        moveRightCenter.set(Constants.BUTTON_RADIUS * 2, Constants.BUTTON_RADIUS * 3 / 4);
        jumpCenter.set(viewport.getWorldWidth() - Constants.BUTTON_RADIUS * 3 / 4, Constants.BUTTON_RADIUS);

    }
}