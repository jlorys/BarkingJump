package com.dog.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.dog.game.states.GameStateManager;
import com.dog.game.states.MenuState;

public class JumpingDog extends ApplicationAdapter implements GestureDetector.GestureListener {

    private SpriteBatch spriteBatch;
    private GameStateManager gameStateManager;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.push(new MenuState(gameStateManager));
        GestureDetector gestureDetector;
        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);

        Gdx.gl.glClearColor(0, 0, 0, 0);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(spriteBatch);
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        gameStateManager.tap(x, y, count, button);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        gameStateManager.pan(x, y, deltaX, deltaY);
        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
