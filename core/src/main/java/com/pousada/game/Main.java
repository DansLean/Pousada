package com.pousada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    Texture backgroundTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Stage stage;

    Label logLabel;
    StringBuilder logText;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture("Background.png");
        viewport = new FitViewport(300, 182);

        // Configura o Stage
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        // Carrega as texturas do botão normal e pressionado
        Texture normalTexture = new Texture("button_normal.png");
        Texture pressedTexture = new Texture("button_pressed.png");

        // Cria o botão com as texturas
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(normalTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(pressedTexture));

        ImageButton button = new ImageButton(buttonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logText.append("Botão pressionado!\n");
                logLabel.setText(logText);
            }
        });

        // Cria o campo de logs (Label) e o constrói
        logText = new StringBuilder();
        logLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Configura o layout usando Table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Posiciona os elementos na tela
        table.top().left(); // Topo e canto esquerdo
        table.row().bottom();
        table.add(button).pad(10); // Botão no canto inferior esquerdo
        table.add(logLabel).expand().right().top().pad(10); // Log no canto direito superior
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1686f, 0.1686f, 0.1686f, 1f); // Limpa a tela
        spriteBatch.begin();
        drawBackground(); // Desenha o background
        spriteBatch.end();

        // Atualiza e desenha o Stage (UI)
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void drawBackground() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Desenha o background no canto superior esquerdo
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.draw(backgroundTexture, 0, worldHeight - backgroundTexture.getHeight());
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        stage.dispose();
    }
}
