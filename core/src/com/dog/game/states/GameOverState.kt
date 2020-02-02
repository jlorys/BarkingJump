package com.dog.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.math.Rectangle

internal class GameOverState(gsm: GameStateManager?, playerX: Float, playerY: Float) : State(gsm!!) {
    private val background = Texture("bg.png")
    private val playBtn = Texture("playbtn.png")
    private val playerX = playerX
    private val playerY = playerY
    private val font: BitmapFont
    override fun handleInput() {}
    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(sb: SpriteBatch?) {
        sb!!.begin()
        sb.draw(background, playerX - percentOfWidth(0.253333), playerY - percentOfHeight(0.262275), Gdx.graphics.width + percentOfWidth(0.40).toFloat(), Gdx.graphics.height + percentOfHeight(0.40).toFloat())
        sb.draw(playBtn, playerX, playerY + percentOfHeight(0.409836066), percentOfWidth(0.4).toFloat(), percentOfHeight(0.1).toFloat())
        font.draw(sb, "Game", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.807962529))
        font.draw(sb, "Over", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.690866511))
        sb.end()
    }

    override fun dispose() {
        background.dispose()
        playBtn.dispose()
        font.dispose()
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int) {
        val textureBounds = Rectangle(percentOfWidth(0.1833333).toFloat(), percentOfHeight(0.3946135938167572).toFloat(), percentOfWidth(0.297916651).toFloat(), percentOfHeight(0.070257634).toFloat())
        if (textureBounds.contains(x, y)) {
            gsm.set(PlayState(gsm))
        }
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}

    init {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontParameter()
        parameter.size = percentOfWidth(0.233333333).toInt()
        font = generator.generateFont(parameter)
        generator.dispose()
    }
}