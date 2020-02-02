package com.dog.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.dog.game.states.GameStateManager
import com.dog.game.states.MenuState

class JumpingDog : ApplicationAdapter(), GestureListener {
    private var spriteBatch: SpriteBatch? = null
    private var gameStateManager: GameStateManager? = null
    override fun create() {
        spriteBatch = SpriteBatch()
        gameStateManager = GameStateManager()
        gameStateManager!!.push(MenuState(gameStateManager))
        val gestureDetector = GestureDetector(this)
        Gdx.input.inputProcessor = gestureDetector
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        gameStateManager!!.update(Gdx.graphics.deltaTime)
        gameStateManager!!.render(spriteBatch)
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        gameStateManager!!.tap(x, y, count, button)
        return true
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        gameStateManager!!.pan(x, y, deltaX, deltaY)
        return true
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return false
    }

    override fun pinch(initialPointer1: Vector2, initialPointer2: Vector2, pointer1: Vector2, pointer2: Vector2): Boolean {
        return false
    }

    override fun pinchStop() {}
}