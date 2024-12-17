package com.pousada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Main extends ApplicationAdapter {
    SpriteBatch spriteBatch;
    Stage stage;
    BitmapFont textLabel;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        textLabel = criarFonte(28, Color.WHITE);
        drawBackground();
        drawButton();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1686f, 0.1686f, 0.1686f, 1f);
        stage.draw();
    }

    private void drawBackground() {
        BackGround background = new BackGround(0, Gdx.graphics.getHeight() - 440);
        stage.addActor(background);
    }

    private void drawButton() {
        Button button = new Button(325, Gdx.graphics.getHeight() - 470);
        stage.addActor(button);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }

    private BitmapFont criarFonte(int tamanho, Color cor) {
        BitmapFont fonte;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CaskaydiaCoveNerdFontPropo-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametro = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parametro.size = tamanho;
        parametro.color = cor;

        fonte = generator.generateFont(parametro);
        generator.dispose();

        return fonte;
    }

}
