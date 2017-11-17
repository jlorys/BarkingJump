package com.dog.game.domain;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class JumpDogHero extends Rectangle {

    private Sound jumpSound;
    private TextureRegion texture;
    private boolean canJump = true;
    private float stateTimeLeft, stateTimeRight;

    private float jumpVelocity;

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

    public TextureRegion getTexture() {return texture;}

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public float getStateTimeLeft() {
        return stateTimeLeft;
    }

    public void setStateTimeLeft(float stateTimeLeft) {
        this.stateTimeLeft = stateTimeLeft;
    }

    public float getStateTimeRight() {
        return stateTimeRight;
    }

    public void setStateTimeRight(float stateTimeRight) {
        this.stateTimeRight = stateTimeRight;
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public void setJumpVelocity(float jumpVelocity) {
        this.jumpVelocity = jumpVelocity;
    }
}
