package com.pousada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Main extends ApplicationAdapter {
    SpriteBatch spriteBatch;
    Stage stage;
    Texture textureBackGround;
    Texture buttonNormalTexture, buttonPressedTexture;
    ImageButton button;
    BitmapFont textLabel;
    Label logLabel;
    StringBuilder logs;
    Character personagem;
    ScrollPane scrollPane;
    Table rootTable;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        textureBackGround = new Texture("Background.png");
        Image backgroundImage = new Image(textureBackGround);
        backgroundImage.setPosition(0, Gdx.graphics.getHeight() - 440);
        backgroundImage.setSize(700, 450);
        backgroundImage.setTouchable(Touchable.disabled);
        stage.addActor(backgroundImage);

        personagem = new Character(205, 42);
        rootTable = new Table();
        rootTable.setFillParent(true);

        textLabel = criarFonte(14, Color.WHITE);
        logs = new StringBuilder();

        logLabel = new Label("", new Label.LabelStyle(textLabel, Color.WHITE));
        exemploDeLogs();

        Table logTable = new Table();
        logTable.top().right().pad(10);
        logTable.add(logLabel).expand().top().right().pad(10);

        scrollPane = new ScrollPane(logTable);
        scrollPane.setForceScroll(false, true);

        rootTable.add(scrollPane).expand().fill();

        stage.addActor(rootTable);

        buttonNormalTexture = new Texture("button_normal.png");
        buttonPressedTexture = new Texture("button_pressed.png");

        TextureRegionDrawable buttonNormal = new TextureRegionDrawable(new TextureRegion(buttonNormalTexture));
        TextureRegionDrawable buttonPressed = new TextureRegionDrawable(new TextureRegion(buttonPressedTexture));

        button = new ImageButton(buttonNormal, buttonPressed);
        button.setSize(65, 40);

        float buttonX = 325;
        float buttonY = Gdx.graphics.getHeight() - 470;
        button.setPosition(buttonX, buttonY);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão clicado!");
                adicionarLog("Botão clicado!");
            }
        });

        stage.addActor(button);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1686f, 0.1686f, 0.1686f, 1f);
        stage.act();
        stage.draw();
        scrollPane.layout();

        logLabel.setText(logs.toString());

        spriteBatch.begin();
        personagem.update(Gdx.graphics.getDeltaTime());
        personagem.render(spriteBatch);
        spriteBatch.end();
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

    public void adicionarLog(String mensagem) {
        logs.append(mensagem).append("\n");
    }

    public void exemploDeLogs() {
        adicionarLog("Iniciando o jogocfksdnl...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Iniciando o jogo...");
        adicionarLog("Personagem movido para a posição X: ");
    }
}
