package com.pousada.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Character {
    private Texture spriteSheet;
    private Animation<TextureRegion> animacaoAtual;
    private float tempo;
    private float velocidade = 25f;
    private String acaoAtual;

    private Vector2 posicao;
    private List<Vector2> waypoints; // Lista de pontos (caminho)
    private int waypointIndex = 0; // Índice do waypoint atual
    private float tolerancia = 1f; // Tolerância para considerar que chegou ao waypoint

    private Animation<TextureRegion> animacaoDescer;
    private Animation<TextureRegion> animacaoSubir;
    private Animation<TextureRegion> animacaoEsquerda;
    private Animation<TextureRegion> animacaoDireita;
    private Animation<TextureRegion> animacaoSentado;
    private Animation<TextureRegion> animacaoLendo;

    public Character(int posX, int posY) {
        Random random = new Random();
        int numero = random.nextInt(6) + 1;
        String path = ("Characters/Character_" + numero + ".png");

        spriteSheet = new Texture(path);

        TextureRegion[][] quadros = TextureRegion.split(spriteSheet, 16, 20);

        animacaoDescer = new Animation<>(0.1f, quadros[0]);
        animacaoSubir = new Animation<>(0.1f, quadros[1]);
        animacaoEsquerda = new Animation<>(0.1f, quadros[2]);
        animacaoDireita = new Animation<>(0.1f, quadros[3]);
        animacaoSentado = new Animation<>(0.3f, quadros[4]);
        animacaoLendo = new Animation<>(0.3f, quadros[5]);

        animacaoSubir.setPlayMode(Animation.PlayMode.LOOP);
        animacaoDescer.setPlayMode(Animation.PlayMode.LOOP);
        animacaoEsquerda.setPlayMode(Animation.PlayMode.LOOP);
        animacaoDireita.setPlayMode(Animation.PlayMode.LOOP);
        animacaoSentado.setPlayMode(Animation.PlayMode.LOOP);
        animacaoLendo.setPlayMode(Animation.PlayMode.LOOP);

        setAcao("sentado");

        posicao = new Vector2(205, 52); // posição inicial 205,52
        waypoints = new ArrayList<>();
        waypoints.add(new Vector2(90, 52)); // pé da entrada para TV
        waypoints.add(new Vector2(120,100)); // livros
    }

    public void adicionarWaypoint(float x, float y) {
        waypoints.add(new Vector2(x, y));
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
                animacaoAtual = animacaoDireita;
                break;
            case "sentado":
                animacaoAtual = animacaoSentado;
                break;
            case "lendo":
                animacaoAtual = animacaoLendo;
                break;
            default:
                animacaoAtual = animacaoSentado;
        }
    }

    public void update(float deltaTime) {
//
//
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            posicao.x += velocidade * deltaTime;
//            setAcao("direita");
//        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && posicao.x > 0) {
//            posicao.x -= velocidade * deltaTime;
//            setAcao("esquerda");
//        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            posicao.y += velocidade * deltaTime;
//            setAcao("subir");
//        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && posicao.y > 0){
//            posicao.y -= velocidade * deltaTime;
//            setAcao("descer");
//        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
//            setAcao("lendo");
//        } else if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
//            setAcao("sentado");
//        } else {
//            posicao.x = posicao.x;
//            posicao.y = posicao.y;
//        }

        Vector2 destino = waypoints.get(waypointIndex);

        Vector2 direcao = new Vector2(destino).sub(posicao).nor();

        posicao.add(direcao.x * velocidade * deltaTime, direcao.y * velocidade * deltaTime);

        if (posicao.dst(destino) < tolerancia) {
            posicao.set(destino); // Garante que está exatamente no ponto
            waypointIndex++;

            // Se chegou no último waypoint, reinicia o caminho (ou para)
            if (waypointIndex >= waypoints.size()) {
                waypointIndex = 0; // Reinicia o caminho (ou faça: waypointIndex = waypoints.size() - 1 para parar)
            }
        }

        tempo += deltaTime;
    }

    public void render(Batch batch) {
        TextureRegion quadroAtual = animacaoAtual.getKeyFrame(tempo);
        batch.draw(quadroAtual, posicao.x, posicao.y);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}