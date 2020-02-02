package com.dog.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class State internal constructor(var gsm: GameStateManager) {
    private val cam: OrthographicCamera = OrthographicCamera()
    abstract fun handleInput()
    abstract fun update(dt: Float)
    abstract fun render(sb: SpriteBatch?)
    abstract fun dispose()
    abstract fun tap(x: Float, y: Float, count: Int, button: Int)
    abstract fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float)

    fun percentOfWidth(percent: Double): Long {
        val screenValue = percent * Gdx.graphics.width
        return Math.round(screenValue)
    }

    fun percentOfHeight(percent: Double): Long {
        val screenValue = percent * Gdx.graphics.height
        return Math.round(screenValue)
    }

    fun showPercentOfHeight(x: Float, y: Float): String {
        val screenValueX = x / Gdx.graphics.width.toDouble()
        val screenValueY = y / Gdx.graphics.height.toDouble()
        return "$screenValueX $screenValueY"
    }

    init {
        cam.setToOrtho(false, 240f, 400f)
    }
}