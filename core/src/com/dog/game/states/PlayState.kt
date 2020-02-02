package com.dog.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.TimeUtils
import com.dog.game.domain.JumpDogHero
import com.dog.game.domain.Platform
import java.util.*

internal class PlayState(gsm: GameStateManager?) : State(gsm!!) {
    private val gravity = -percentOfHeight(0.02).toFloat()
    private val playerVelocity = percentOfHeight(1.0).toFloat()
    private val platformsDistance = percentOfHeight(0.3).toFloat()
    private val platformsHeight = percentOfHeight(0.05625).toFloat()
    private var walkAnimationLeft: Animation<TextureRegion>? = null
    private var walkAnimationRight: Animation<TextureRegion>? = null
    private var music: Music? = null
    private var playerTextureWalkLeft: Texture? = null
    private var playerTextureJumpLeft: Texture? = null
    private var playerTextureWalkRight: Texture? = null
    private var playerTextureJumpRight: Texture? = null
    private var platformTexture: Texture? = null
    private var platformQuestionTexture: Texture? = null
    private var background: Texture? = null
    private var stone: Texture? = null
    private var stoneWaterRight: Texture? = null
    private var stoneWaterLeft: Texture? = null
    private var water: Texture? = null
    private var player: JumpDogHero? = null
    private var platformArray: com.badlogic.gdx.utils.Array<Platform>? = null
    private var camera: OrthographicCamera? = null
    private val start = TimeUtils.millis()
    private var currentFrame: TextureRegion? = null
    private var currentJumpTexture: Texture? = null
    private val isQuestionAnswered = Arrays.asList<Boolean>(*arrayOfNulls(10))
    private var time = 0f
    private fun loadData() {
        val walkFramesLeft: Array<TextureRegion?>
        val walkFramesRight: Array<TextureRegion?>
        stone = Texture(Gdx.files.internal("stone.png"))
        stoneWaterRight = Texture(Gdx.files.internal("stonewaterright.png"))
        stoneWaterLeft = Texture(Gdx.files.internal("stonewaterleft.png"))
        water = Texture(Gdx.files.internal("water.png"))
        playerTextureWalkLeft = Texture(Gdx.files.internal("dogleft1.png"))
        playerTextureJumpLeft = Texture(Gdx.files.internal("dog jump left.png"))
        playerTextureWalkRight = Texture(Gdx.files.internal("dogright1.png"))
        playerTextureJumpRight = Texture(Gdx.files.internal("dog jump right.png"))
        walkFramesLeft = DivideToRegion(playerTextureWalkLeft!!)
        walkFramesRight = DivideToRegion(playerTextureWalkRight!!)
        walkAnimationLeft = Animation<TextureRegion>(0.025f, *walkFramesLeft)
        walkAnimationRight = Animation<TextureRegion>(0.025f, *walkFramesRight)
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"))
        platformTexture = Texture(Gdx.files.internal("platform.png"))
        platformQuestionTexture = Texture(Gdx.files.internal("questionplatform.png"))
        background = Texture(Gdx.files.internal("bg.png"))
        player = walkFramesLeft[0]?.let { JumpDogHero(it, Gdx.audio.newSound(Gdx.files.internal("dog.ogg"))) }
        currentFrame = walkAnimationLeft!!.getKeyFrame(player!!.stateTimeLeft, true)
        currentJumpTexture = playerTextureJumpLeft
        platformArray = com.badlogic.gdx.utils.Array()
        //1 is 100%
        camera = OrthographicCamera(percentOfWidth(1.0).toFloat(), percentOfHeight(1.0).toFloat())
        for (i in 0..9) {
            isQuestionAnswered[i] = false
        }
        for (i in 1..50) {
            val p = Platform(platformTexture!!, platformsHeight)
            p.x = MathUtils.random(percentOfWidth(0.7)).toFloat()
            p.y = platformsDistance * i
            val pQuestion = Platform(platformQuestionTexture!!, platformsHeight)
            pQuestion.x = MathUtils.random(percentOfWidth(0.7)).toFloat()
            pQuestion.y = platformsDistance * i
            if (i % 5 == 0) platformArray!!.add(pQuestion) else platformArray!!.add(p)
        }
    }

    private fun DivideToRegion(texture: Texture): Array<TextureRegion?> {
        val tmp = TextureRegion.split(texture,
                texture.width / FRAME_COLS,
                texture.height / FRAME_ROWS)
        val walkFrames = arrayOfNulls<TextureRegion>(FRAME_COLS * FRAME_ROWS)
        var index = 0
        for (i in 0 until FRAME_ROWS) {
            for (j in 0 until FRAME_COLS) {
                walkFrames[index++] = tmp[i][j]
            }
        }
        return walkFrames
    }

    private fun isPlayerOnPlatform(p: Platform): Boolean {
        return player!!.jumpVelocity <= 0 && player!!.overlaps(p) && player!!.y > p.y
    }

    override fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            player!!.x -= 400 * Gdx.graphics.deltaTime
            currentJumpTexture = playerTextureJumpLeft
            if (player!!.isCanJump) {
                player!!.stateTimeLeft = player!!.stateTimeLeft + Gdx.graphics.deltaTime * 0.3f // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationLeft!!.getKeyFrame(player!!.stateTimeLeft, true)
                player!!.texture = currentFrame!!
                currentJumpTexture = playerTextureJumpLeft
            } else {
                player!!.texture = TextureRegion(currentJumpTexture)
                currentFrame = walkAnimationLeft!!.getKeyFrame(player!!.stateTimeLeft, true)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            player!!.x += 400 * Gdx.graphics.deltaTime
            currentJumpTexture = playerTextureJumpRight
            if (player!!.isCanJump) {
                player!!.stateTimeRight = player!!.stateTimeRight + Gdx.graphics.deltaTime * 0.3f // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationRight!!.getKeyFrame(player!!.stateTimeRight, true)
                player!!.texture = currentFrame!!
                currentJumpTexture = playerTextureJumpRight
            } else {
                player!!.texture = TextureRegion(currentJumpTexture)
                currentFrame = walkAnimationRight!!.getKeyFrame(player!!.stateTimeRight, true)
            }
        }
    }

    override fun update(dt: Float) {
        handleInput()
        music!!.play()
        camera!!.update()
        camera!!.zoom = 1.3f
        camera!!.position[player!!.x + percentOfWidth(0.416666667), player!!.y + percentOfHeight(0.351288056)] = 0f
        if (player!!.isCanJump) {
            player!!.texture = currentFrame!!
        } else {
            player!!.texture = TextureRegion(currentJumpTexture)
        }
        player!!.y += player!!.jumpVelocity * Gdx.graphics.deltaTime
        if (player!!.y > 0) {
            player!!.jumpVelocity = player!!.jumpVelocity + gravity
        } else {
            player!!.y = 0f
            player!!.isCanJump = true
            player!!.jumpVelocity = 0f
        }
        //This code makes right limit for dog walking space
        if (player!!.x > percentOfWidth(4.4)) {
            player!!.x = percentOfWidth(4.4).toFloat()
        }
        //This code makes left limit for dog walking space
        if (player!!.x < percentOfWidth(-2.1)) {
            player!!.x = percentOfWidth(-2.1).toFloat()
        }
        for (p in platformArray!!) {
            if (isPlayerOnPlatform(p)) {
                player!!.isCanJump = true
                player!!.jumpVelocity = 0f
                player!!.y = p.y + p.height
                var i = 5
                while (i <= 50) {
                    if (p.getY() == platformsDistance * i && !isQuestionAnswered[i / 5 - 1]) {
                        gsm.push(QuestionState(gsm, player!!.x, player!!.y))
                        isQuestionAnswered[i / 5 - 1] = true
                    } else if (p.getY() == platformsDistance * 50 && isQuestionAnswered[9]) {
                        if (time == 0f) {
                            val stop = TimeUtils.millis()
                            time = (stop - start).toFloat()
                            gsm.set(GameEndState(gsm, player!!.x, player!!.y, time))
                            dispose()
                        }
                    }
                    i += 5
                }
            }
        }
    }

    override fun render(sb: SpriteBatch?) {
        sb!!.begin()
        sb.projectionMatrix = camera!!.combined
        drawTheTower(sb)
        drawGroundAndWater(sb)
        drawPlatforms(sb)
        drawDogPlayer(sb)
        sb.end()
    }

    private fun drawDogPlayer(sb: SpriteBatch?) {
        sb?.let { player!!.draw(it, percentOfWidth(0.816666667), percentOfHeight(0.221311475)) }
    }

    private fun drawPlatforms(sb: SpriteBatch?) {
        for (p in platformArray!!) {
            p.setWidth(percentOfWidth(1.05).toFloat())
            sb?.let { p.draw(it, percentOfWidth(0.5625), percentOfHeight(0.05625)) }
        }
    }

    private fun drawGroundAndWater(sb: SpriteBatch?) {
        var x = (-percentOfWidth(4.375)).toInt()
        for (j in 0..18) {
            if (x < -percentOfWidth(2.5))
                sb!!.draw(water, x.toFloat(),
                        -percentOfHeight(0.351288056).toFloat(),
                        percentOfWidth(0.625).toFloat(),
                        percentOfHeight(0.351288056).toFloat())
            else if (x.toLong() == -percentOfWidth(2.5))
                sb!!.draw(stoneWaterLeft, x.toFloat(),
                        -percentOfHeight(0.351288056).toFloat(),
                        percentOfWidth(0.625).toFloat(),
                        percentOfHeight(0.351288056).toFloat())
            else if (j < 15) sb!!.draw(stone, x.toFloat(),
                    -percentOfHeight(0.351288056).toFloat(),
                    percentOfWidth(0.625).toFloat(),
                    percentOfHeight(0.351288056).toFloat())
            else if (j == 15) sb!!.draw(stoneWaterRight, x.toFloat(),
                    -percentOfHeight(0.351288056).toFloat(),
                    percentOfWidth(0.625).toFloat(),
                    percentOfHeight(0.351288056).toFloat())
            else sb!!.draw(water, x.toFloat(), -percentOfHeight(0.351288056).toFloat(),
                    percentOfWidth(0.625).toFloat(),
                    percentOfHeight(0.351288056).toFloat())
            x += percentOfWidth(0.625).toInt()
        }
    }

    private fun drawTheTower(sb: SpriteBatch?) {
        var y = 0
        for (i in 0..34) {
            var x = -percentOfWidth(1.2)
            for (j in 0..7) {
                sb!!.draw(background, x.toFloat(), y.toFloat(), Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
                x += percentOfWidth(0.6)
            }
            y += percentOfHeight(0.599531616).toInt()
        }
    }

    override fun dispose() {
        music!!.dispose()
        playerTextureWalkLeft!!.dispose()
        playerTextureJumpLeft!!.dispose()
        playerTextureWalkRight!!.dispose()
        playerTextureJumpRight!!.dispose()
        platformTexture!!.dispose()
        platformQuestionTexture!!.dispose()
        background!!.dispose()
        stone!!.dispose()
        stoneWaterRight!!.dispose()
        stoneWaterLeft!!.dispose()
        water!!.dispose()
        currentFrame!!.texture.dispose()
        currentJumpTexture!!.dispose()
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int) {
        player!!.jump(playerVelocity)
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float) {
        val centerX = percentOfWidth(0.5).toFloat()
        if (centerX < x) {
            player!!.x += percentOfWidth(0.28) * Gdx.graphics.deltaTime
            currentJumpTexture = playerTextureJumpRight
            if (player!!.isCanJump) {
                player!!.stateTimeRight = player!!.stateTimeRight + Gdx.graphics.deltaTime * 0.3f // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationRight!!.getKeyFrame(player!!.stateTimeRight, true)
                player!!.texture = currentFrame!!
                currentJumpTexture = playerTextureJumpRight
            } else {
                player!!.texture = TextureRegion(currentJumpTexture)
                currentFrame = walkAnimationRight!!.getKeyFrame(player!!.stateTimeRight, true)
            }
        } else {
            player!!.x -= percentOfWidth(0.28) * Gdx.graphics.deltaTime
            currentJumpTexture = playerTextureJumpLeft
            if (player!!.isCanJump) {
                player!!.stateTimeLeft = player!!.stateTimeLeft + Gdx.graphics.deltaTime * 0.3f // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationLeft!!.getKeyFrame(player!!.stateTimeLeft, true)
                player!!.texture = (currentFrame)!!
                currentJumpTexture = playerTextureJumpLeft
            } else {
                player!!.texture = TextureRegion(currentJumpTexture)
                currentFrame = walkAnimationLeft!!.getKeyFrame(player!!.stateTimeLeft, true)
            }
        }
    }

    companion object {
        private const val FRAME_COLS = 1
        private const val FRAME_ROWS = 16
    }

    init {
        loadData()
    }
}