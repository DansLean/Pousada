package com.pousada.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Character {
    private Texture spriteSheet;
    private Animation<TextureRegion> animacaoAtual;
    private float tempo;
    private float x, y;
    private float velocidade = 100f;
    private String acaoAtual;

    private Animation<TextureRegion> animacaoSubir;
    private Animation<TextureRegion> animacaoDescer;
    private Animation<TextureRegion> animacaoEsquerda;
    private Animation<TextureRegion> animationDireita;

    public Character(int posX, int posY) {
        Random random = new Random();
        int numero = random.nextInt(6) + 1;
        String path = ("Characters/Character_" + numero + ".png");

        spriteSheet = new Texture(path);

        TextureRegion[][] quadros = TextureRegion.split(spriteSheet, 16, 20);

        animacaoDescer = new Animation<>(0.1f, quadros[0]);
        animacaoSubir = new Animation<>(0.1f, quadros[1]);
        animacaoEsquerda = new Animation<>(0.1f, quadros[2]);
        animationDireita = new Animation<>(0.1f, quadros[3]);

        animacaoSubir.setPlayMode(Animation.PlayMode.LOOP);
        animacaoDescer.setPlayMode(Animation.PlayMode.LOOP);
        animacaoEsquerda.setPlayMode(Animation.PlayMode.LOOP);
        animationDireita.setPlayMode(Animation.PlayMode.LOOP);

        setAcao("descer");

        // Posição inicial do personagem
        x = posX;
        y = posY;
    }

    public void setAcao(String novaAcao) {
        if (acaoAtual != null && acaoAtual.equals(novaAcao)) return;
        acaoAtual = novaAcao;
        tempo = 0;

        switch (novaAcao) {
            case "subir":
                animacaoAtual = animacaoSubir;
                break;
            case "descer":
                animacaoAtual = animacaoDescer;
                break;
            case "esquerda":
                animacaoAtual = animacaoEsquerda;
                break;
            case "direita":
                animacaoAtual = animationDireita;
                break;
            default:
                animacaoAtual = animacaoDescer;
        }
    }

    public void update(float deltaTime) {
        tempo += deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += velocidade * deltaTime;
            setAcao("direita");
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= velocidade * deltaTime;
            setAcao("esquerda");
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += velocidade * deltaTime;
            setAcao("subir");
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= velocidade * deltaTime;
            setAcao("descer");
        } else {
            x = x;
            y = y;
        }
    }

    public void render(Batch batch) {
        TextureRegion quadroAtual = animacaoAtual.getKeyFrame(tempo);
        batch.draw(quadroAtual, x, y);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
