package com.pousada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Main extends ApplicationAdapter {
    SpriteBatch spriteBatch;
    Stage stage;
    BitmapFont textLabel;
    Label logLabel;
    StringBuilder logs;
    Character personagem;

    // Criando a área rolável para os logs
    ScrollPane scrollPane;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        textLabel = criarFonte(14, Color.WHITE);
        logs = new StringBuilder();

        // Criação do Label para exibir os logs
        logLabel = new Label("", new Label.LabelStyle(textLabel, Color.WHITE));

        // Criando uma Table para o log, que servirá como container para o ScrollPane
        Table logTable = new Table();
        logTable.top().right().pad(10); // Ajuste a posição
        logTable.add(logLabel).expand().top().right().pad(10);

        // Criando o ScrollPane para permitir rolagem do campo de logs
        scrollPane = new ScrollPane(logTable);
//        scrollPane.setFillParent(true); // Faz o ScrollPane ocupar todo o tamanho disponível
        scrollPane.setForceScroll(false, true); // Permitir rolagem vertical, mas não horizontal
//        stage.addActor(scrollPane); // Adiciona o ScrollPane ao stage

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.add(scrollPane).expand().fill();

        stage.addActor(rootTable);

        drawBackground();
        drawButton();
        exemploDeLogs();

        personagem = new Character(205, 42);
    }

    @Override
    public void render() {
        spriteBatch.begin();
        ScreenUtils.clear(0.1686f, 0.1686f, 0.1686f, 1f);
        stage.draw();
        personagem.update(Gdx.graphics.getDeltaTime());
        personagem.render(spriteBatch);
        spriteBatch.end();

        // Atualiza o log
        logLabel.setText(logs.toString()); // Atualiza o conteúdo do log
        scrollPane.layout();// Atualiza o layout do ScrollPane
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

    // Função para adicionar mensagens ao log
    public void adicionarLog(String mensagem) {
        logs.append(mensagem).append("\n");
    }

    // Exemplo de como adicionar logs
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
