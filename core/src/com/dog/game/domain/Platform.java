package com.dog.game.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Platform extends Rectangle {
    private Texture texture;

    public Platform(Texture texture, float height) {
        this.texture = texture;
        this.height = height;
        this.width = texture.getWidth();
    }

    public void draw(SpriteBatch batch, long width, long height) {
        batch.draw(texture, x + width, y, width, height);
    }
}
