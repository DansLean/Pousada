package com.pousada.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Character implements Runnable {
    private Texture spriteSheet;
    private Animation<TextureRegion> animacaoAtual;
    private float tempo;
    private float velocidade = 100f;
    private String acaoAtual;

    private Vector2 posicao;
    private List<Vector2> waypoints; // Lista de pontos (caminho)
    private int waypointIndex = 0; // Índice do waypoint atual
    private float tolerancia = 1f;// Tolerância para considerar que chegou ao waypoint
    public float posAssentoX = 0f;
    public float posAssentoY = 0f;
    public int assento = 1;

    private Animation<TextureRegion> animacaoDescer;
    private Animation<TextureRegion> animacaoSubir;
    private Animation<TextureRegion> animacaoEsquerda;
    private Animation<TextureRegion> animacaoDireita;
    private Animation<TextureRegion> animacaoSentado;
    private Animation<TextureRegion> animacaoLendo;

    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore espera = new Semaphore(1);
    public static Semaphore assistindo = new Semaphore(0);
    int canal_atual;
    int canal_preferido;


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

        setSprite("esquerda");

        posicao = new Vector2(Gdx.graphics.getWidth() * 0.589f, Gdx.graphics.getHeight() * 0.35f); // posição inicial 205,52
        waypoints = new ArrayList<>();
        waypoints.add(new Vector2(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.35f)); // pé da entrada para TV

        //waypoints.add(new Vector2(posAssentoX, posAssentoY)); // assentos da tv
        waypoints.add(new Vector2(Gdx.graphics.getWidth() * 0.35f, Gdx.graphics.getHeight() * 0.65f)); // livros
    }

    public void setSprite(String novaAcao) {
        if (acaoAtual != null && acaoAtual.equals(novaAcao)) return;
        acaoAtual = novaAcao;
        tempo = 0;

        switch (novaAcao) {
            case "cima":
                animacaoAtual = animacaoSubir;
                break;
            case "baixo":
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
        Vector2 destino = waypoints.get(waypointIndex);

        Vector2 direcao = new Vector2(destino).sub(posicao).nor();

        posicao.add(direcao.x * velocidade * deltaTime, direcao.y * velocidade * deltaTime);

        if (posicao.dst(destino) < tolerancia) {
            posicao.set(destino); // Garante que está exatamente no ponto
            assento += 1;
            waypointIndex++;

            // Se chegou no último waypoint, reinicia o caminho (ou para)
            if (waypointIndex >= waypoints.size()) {
                waypointIndex = 0; // Reinicia o caminho (ou faça: waypointIndex = waypoints.size() - 1 para parar)
            }
        }

        if (assento == 1) {
            posAssentoX = Gdx.graphics.getWidth() * 0.25f;
            posAssentoY = Gdx.graphics.getHeight() * 0.35f;
        } else if (assento == 2) {
            posAssentoX= Gdx.graphics.getWidth() * 3.2f;
            posAssentoY = Gdx.graphics.getHeight() * 1.75f;
        }

        if (direcao.x > 0) {
            setSprite("direita");
        } else if (direcao.x < 0) {
            setSprite("esquerda");
        }

        tempo += deltaTime;
    }

    public void render(Batch batch) {
        TextureRegion quadroAtual = animacaoAtual.getKeyFrame(tempo);
        batch.draw(quadroAtual, posicao.x, posicao.y, quadroAtual.getRegionWidth() * 2.5f, quadroAtual.getRegionHeight() * 2.5f);
    }

    public void dispose() {
        spriteSheet.dispose();
    }

    public static void down(Semaphore semaphore) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void up(Semaphore semaphore) {
        semaphore.release();
    }

    public void assistir_tv() {
        // depois de sair de bloqueado, teleportar hóspede para um banquinho e colocar o sprite dele assistir tv
    }

    public void descansar() {
        // depois que passar o tempo de assistir tv, rodar essa função dentro de assistir_tv(), onde o hóspede vai
        // se teleportar pra perto das estantes e rodar o sprite dele lendo o livro
    }

    @Override
    public void run() {
        for(;;) {
            down(mutex);
            if (assistindo.availablePermits() > 0) {
                if (canal_atual == canal_preferido) {
                    up(mutex);
                    assistir_tv();
                } else {
                up(mutex);
                down(espera);
                }
            } else {
                canal_atual = canal_preferido;
                up(mutex);
                assistir_tv();
            }
        }
    }
}