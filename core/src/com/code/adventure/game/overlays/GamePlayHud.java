package com.code.adventure.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.code.adventure.game.entities.Key;
import com.code.adventure.game.entities.Path;
import com.code.adventure.game.entities.Platform;
import com.code.adventure.game.util.Assets;
import com.code.adventure.game.util.Constants;

public class GamePlayHud {
    public final Viewport viewport;
    final BitmapFont font;


    public GamePlayHud() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE,Constants.WORLD_SIZE);
        font = new BitmapFont(Gdx.files.internal("skins/skin/font.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(Constants.FONT_SCALE);
    }

    public void render(SpriteBatch batch,int keysSize) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        String txt = "il reste: "+keysSize+" cles";
        GlyphLayout layout = new GlyphLayout(font,txt);
        font.draw(batch,txt,viewport.getWorldWidth()-Constants.HUD_MARGIN-layout.width,viewport.getWorldHeight()-Constants.HUD_MARGIN);
        batch.end();

    }
}
