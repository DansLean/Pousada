package com.pousada.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackGround extends Actor {
    Texture textureBackGround;
    float x;
    float y;
    float largura = 700;
    float altura = 450;

    public  BackGround(float x, float y) {
        textureBackGround = new Texture("Background.png");

        this.x = x;
        this.y = y;
        setBounds(x, y, largura, altura);
    }

    public void draw(Batch batch, float delta) {
        batch.draw(textureBackGround, x, y, largura, altura);
    }
}
