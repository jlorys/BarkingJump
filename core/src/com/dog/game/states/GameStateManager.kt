package com.dog.game.states

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.util.*

class GameStateManager {
    private val states: Stack<State> = Stack()

    fun push(state: State): State = states.push(state)

    fun pop(): State = states.pop()

    fun set(state: State) {
        states[0].dispose()
        states.pop()
        states.push(state)
    }

    fun update(dt: Float) =
        states.peek().update(dt)

    fun render(sb: SpriteBatch?) =
        states.peek().render(sb)

    fun tap(x: Float, y: Float, count: Int, button: Int) =
        states.peek().tap(x, y, count, button)

    fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float) =
        states.peek().pan(x, y, deltaX, deltaY)

}