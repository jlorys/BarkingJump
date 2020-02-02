package com.dog.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.math.Rectangle
import com.dog.game.DatabaseInitialization
import java.math.BigDecimal

internal class GameEndState(gsm: GameStateManager?, playerX: Float, playerY: Float, time: Float) : State(gsm!!) {
    private val background = Texture("bg.png")
    private val playBtn = Texture("playbtn.png")
    private val playerX = playerX
    private val playerY = playerY
    private var time = time / 1000
    private val db = DatabaseInitialization()
    private val record = db.actualRecord
    private val font: BitmapFont
    private val fontNewRecord: BitmapFont
    private var newRecord = false

    override fun handleInput() {}

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(sb: SpriteBatch?) {
        sb!!.begin()
        sb.draw(background, playerX - percentOfWidth(0.253333), playerY - percentOfHeight(0.262275), Gdx.graphics.width + percentOfWidth(0.40).toFloat(), Gdx.graphics.height + percentOfHeight(0.40).toFloat())
        sb.draw(playBtn, playerX, playerY + percentOfHeight(0.409836066), percentOfWidth(0.4).toFloat(), percentOfHeight(0.1).toFloat())
        font.draw(sb, "Koniec", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.866510539))
        font.draw(sb, "Czas: ", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.351288056))
        font.draw(sb, time.toString(), playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.234192037))
        font.draw(sb, "Rekord: ", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.117096))
        font.draw(sb, record.toString(), playerX - percentOfWidth(0.1875), playerY)
        if (newRecord) {
            fontNewRecord.color = Color.GREEN
            fontNewRecord.draw(sb, "Nowy Rekord!", playerX - percentOfWidth(0.1875), playerY - percentOfHeight(0.117096))
        }
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

    companion object {
        private fun round(d: Float, decimalPlace: Int): Float {
            var bd = BigDecimal(d.toString())
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
            return bd.toFloat()
        }
    }

    init {
        this.time = round(this.time, 2)
        if (this.time < record!!) {
            db.insertRecord(this.time)
            newRecord = true
        }
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontParameter()
        parameter.size = percentOfWidth(0.11).toInt()
        font = generator.generateFont(parameter)
        fontNewRecord = generator.generateFont(parameter)
        generator.dispose()
    }
}