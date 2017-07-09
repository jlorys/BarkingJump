package com.dog.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    public void set(State state) {
        states.get(0).dispose();
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

    public void tap(float x, float y, int count, int button) {
        states.peek().tap(x, y, count, button);
    }

    public void pan(float x, float y, float deltaX, float deltaY) {
        states.peek().pan(x, y, deltaX, deltaY);
    }

}
