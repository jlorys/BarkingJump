package com.dog.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.dog.game.JumpingDog

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 480
        config.height = 854
        config.resizable = false
        LwjglApplication(JumpingDog(), config)
    }
}
