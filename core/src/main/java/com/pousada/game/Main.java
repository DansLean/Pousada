package com.pousada.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
    Window inputWindow;

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
                mostrarInputs();
            }
        });

        stage.addActor(button);
    }

    private void mostrarInputs() {
        inputWindow = new Window("Criar Personagem", criarJanelaStyle());
        inputWindow.setSize(400, 300);
        inputWindow.setPosition(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 100);

        TextField.TextFieldStyle textFieldStyle = criarTextFieldStyle();
        TextButton.TextButtonStyle buttonStyle = criarTextButtonStyle();

        TextField nomeField = new TextField("", textFieldStyle);
        TextField canalField = new TextField("", textFieldStyle);
        TextField ttvField = new TextField("", textFieldStyle);
        TextField tdField = new TextField("", textFieldStyle);

        nomeField.setMessageText("Nome");
        canalField.setMessageText("Canal");
        ttvField.setMessageText("Tempo de TV");
        tdField.setMessageText("Tempo de Descanso");

        TextButton confirmarButton = new TextButton("Confirmar", buttonStyle);
        confirmarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String nome = nomeField.getText();
                String canal = canalField.getText();
                String ttv = ttvField.getText();
                String td = tdField.getText();

                if (!nome.isEmpty() && !canal.isEmpty() && !ttv.isEmpty() && !td.isEmpty()) {
                    try {
                        int canalInt = Integer.parseInt(canal);
                        float ttvFloat = Float.parseFloat(ttv);
                        float tdFloat = Float.parseFloat(td);
                        criarPersonagem(nome, canalInt, ttvFloat, tdFloat);
                        adicionarLog("Personagem criado:\nNome - " + nome + "\nCanal - " + canalInt + "\nTempo de TV - " + ttvFloat + "\nTempo de Descanso - " + tdFloat);
                        inputWindow.remove();
                    } catch (NumberFormatException e) {
                        adicionarLog("Canal inválido!");
                    }
                } else {
                    adicionarLog("Preencha todos os campos!");
                }
            }
        });

        Table inputTable = new Table();
        inputTable.add(new Label("Nome:", new Label.LabelStyle(textLabel, Color.WHITE))).pad(5);
        inputTable.add(nomeField).width(150).row();
        inputTable.add(new Label("Canal:", new Label.LabelStyle(textLabel, Color.WHITE))).pad(5);
        inputTable.add(canalField).width(150).row();
        inputTable.add(new Label("Tempo de TV:", new Label.LabelStyle(textLabel, Color.WHITE))).pad(5);
        inputTable.add(ttvField).width(150).row();
        inputTable.add(new Label("Tempo de Descanso:", new Label.LabelStyle(textLabel, Color.WHITE))).pad(5);
        inputTable.add(tdField).width(150).row();
        inputTable.add(confirmarButton).colspan(2).center().padTop(10);

        inputWindow.add(inputTable).expand().fill();
        stage.addActor(inputWindow);
    }

    private void criarPersonagem(String nome, int canal, float ttv, float td) {
//        personagem.setNome(nome);
//        personagem.setCanal(canal);
//        personagem.setTtv(ttv);
//        personagem.setTd(td);
    }

    private Window.WindowStyle criarJanelaStyle() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.titleFont = textLabel;
        style.titleFontColor = Color.WHITE;
        style.background = criarBackground(Color.DARK_GRAY);
        return style;
    }

    private TextField.TextFieldStyle criarTextFieldStyle() {
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = textLabel;
        style.fontColor = Color.WHITE;
        style.background = criarBackground(Color.DARK_GRAY);
        style.cursor = criarBackground(Color.WHITE);
        style.selection = criarBackground(Color.BLUE);
        return style;
    }

    private TextButton.TextButtonStyle criarTextButtonStyle() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = textLabel;
        style.fontColor = Color.WHITE;
        style.up = criarBackground(Color.DARK_GRAY);
        style.down = criarBackground(Color.LIGHT_GRAY);
        return style;
    }

    private Drawable criarBackground(Color cor) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(cor);
        pixmap.fill();
        return new Image(new Texture(pixmap)).getDrawable();
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
