package com.dog.game.domain;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class JumpDogHero extends Rectangle {

    private Sound jumpSound;
    private TextureRegion texture;
    public boolean canJump = true;
    public float stateTimeLeft, stateTimeRight;

    public float jumpVelocity;

    public JumpDogHero(TextureRegion texture, Sound jumpSound) {
        this.texture = texture;
        this.jumpSound = jumpSound;
    }

    public void draw(SpriteBatch batch, long width, long height) {
        batch.draw(texture, x, y, width, height);
    }

    public void jump() {
        if (canJump && jumpVelocity >= -100) {
            jumpVelocity += 800;
            canJump = false;
            jumpSound.play(0.25f);
        }
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}
