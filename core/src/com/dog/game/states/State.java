package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    GameStateManager gsm;
    private OrthographicCamera cam;

    State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 240, 400);
    }

    public abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

    public abstract void tap(float x, float y, int count, int button);

    public abstract void pan(float x, float y, float deltaX, float deltaY);

    long percentOfWidth(double percent) {
        double screenValue = percent * Gdx.graphics.getWidth();
        return Math.round(screenValue);
    }

    long percentOfHeight(double percent) {
        double screenValue = percent * Gdx.graphics.getHeight();
        return Math.round(screenValue);
    }

    public String showPercentOfHeight(float x, float y) {
        double screenValueX = x / Gdx.graphics.getWidth();
        double screenValueY = y / Gdx.graphics.getHeight();
        return (screenValueX + " " + screenValueY);
    }
}
