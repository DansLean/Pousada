package com.pousada.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;

public class Button extends Actor {
    Texture normalTexture;
    Texture pressedTexture;
    boolean isPressed;

    float x;
    float y;
    float largura = 65;
    float altura = 40;

    public  Button(float x, float y) {
        normalTexture = new Texture("button_normal.png");
        pressedTexture = new Texture("button_pressed.png");

        this.x = x;
        this.y = y;
        setBounds(x, y, largura, altura);
        isPressed = false;

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPressed = true;
                System.out.println("Botão pressionado!\n");
            }
        });
    }

    public void draw(Batch batch, float delta) {
        batch.draw(isPressed ? pressedTexture : normalTexture, x, y, largura, altura);
    }
}
