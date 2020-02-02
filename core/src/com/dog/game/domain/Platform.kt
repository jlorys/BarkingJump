package com.dog.game.domain

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

class Platform(private val texture: Texture, height: Float) : Rectangle() {
    fun draw(batch: SpriteBatch, width: Long, height: Long) {
        batch.draw(texture, x + width, y, width.toFloat(), height.toFloat())
    }

    init {
        this.height = height
        width = texture.width.toFloat()
    }
}