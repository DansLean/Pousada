package com.pousada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture backgroundTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture normalButon;
    Texture pressedButon;
    ImageButton button;
    TextureRegionDrawable normalDrawable;
    TextureRegionDrawable pressedDrawable;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture("Background.png");
        viewport = new FitViewport(8, 5);
        normalButon = new Texture("buttons/botao_normal.png");
        pressedButon = new Texture("buttons/botao_press.png");
        normalDrawable = new TextureRegionDrawable(new TextureRegion(normalButon));
        pressedDrawable = new TextureRegionDrawable(new TextureRegion(pressedButon));

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = normalDrawable;
        buttonStyle.down = pressedDrawable;


        button.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.input.InputEvent event, float x, float y) {
                System.out.println("Botão de imagem clicado!");
            }
        });


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        draw();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }

    private void draw() {
        ScreenUtils.clear(0.1686f, 0.1686f, 0.1686f, 1f);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        button = new ImageButton(buttonStyle);
        button.setPosition(200, 200);

        spriteBatch.end();
    }
}
