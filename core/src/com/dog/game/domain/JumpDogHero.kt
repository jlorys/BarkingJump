package com.dog.game.domain

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle

class JumpDogHero(var texture: TextureRegion, private val jumpSound: Sound) : Rectangle() {
    var isCanJump = true
    var stateTimeLeft = 0f
    var stateTimeRight = 0f
    var jumpVelocity = 0f

    fun draw(batch: SpriteBatch, width: Long, height: Long) =
        batch.draw(texture, x, y, width.toFloat(), height.toFloat())

    fun jump(playerVelocity: Float) {
        if (isCanJump && jumpVelocity >= -100) {
            jumpVelocity += playerVelocity
            isCanJump = false
            jumpSound.play(0.25f)
        }
    }

}