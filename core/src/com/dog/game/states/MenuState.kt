package com.dog.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.math.Rectangle
import com.dog.game.DatabaseInitialization

class MenuState(gsm: GameStateManager?) : State(gsm!!) {
    private val background = Texture("bg.png")
    private val playBtn = Texture("playbtn.png")
    private val logo = Texture("logo.png")
    private val db = DatabaseInitialization()
    private val record = db.actualRecord
    private val font: BitmapFont
    override fun handleInput() {}
    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(sb: SpriteBatch?) {
        sb!!.begin()
        sb.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        sb.draw(playBtn, percentOfWidth(0.39375).toFloat(), percentOfHeight(0.46487).toFloat(), percentOfWidth(0.210416687).toFloat(), percentOfHeight(0.067916906).toFloat())
        sb.draw(logo, percentOfWidth(0.0520833).toFloat(), percentOfHeight(0.70257).toFloat(), percentOfWidth(0.891666667).toFloat(), percentOfHeight(0.119437939).toFloat())
        font.draw(sb, "Rekord: ", percentOfWidth(0.03125).toFloat(), percentOfHeight(0.35128).toFloat())
        font.draw(sb, record.toString(), percentOfWidth(0.03125).toFloat(), percentOfHeight(0.29274).toFloat())
        sb.end()
    }

    override fun dispose() {
        background.dispose()
        playBtn.dispose()
        logo.dispose()
        font.dispose()
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int) {
        val textureBounds = Rectangle(percentOfWidth(0.39375).toFloat(), percentOfHeight(0.46487).toFloat(),
                percentOfWidth(0.210416687).toFloat(),
                percentOfHeight(0.067916906).toFloat())
        if (textureBounds.contains(x, y)) {
            gsm.set(PlayState(gsm))
        }
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}

    init {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontParameter()
        parameter.size = percentOfWidth(0.066666667).toInt()
        font = generator.generateFont(parameter)
        generator.dispose()
    }
}