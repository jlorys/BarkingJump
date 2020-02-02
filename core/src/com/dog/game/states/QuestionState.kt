package com.dog.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.math.Rectangle
import com.dog.game.domain.Question
import java.util.*

/*
Drawing background and buttons is made by SpriteBatch common for all classes
tap method use actual 480x854 screen size
*/
class QuestionState(gsm: GameStateManager?, playerX: Float, playerY: Float) : State(gsm!!) {
    private val background: Texture = Texture("bg.png")
    private val aBtn: Texture = Texture("abtn.png")
    private val bBtn: Texture = Texture("bbtn.png")
    private val cBtn: Texture = Texture("cbtn.png")
    private val dBtn: Texture = Texture("dbtn.png")
    private val playerX: Float = playerX
    private val playerY: Float = playerY
    private val goodAnswerSound: Sound = Gdx.audio.newSound(Gdx.files.internal("goodanswer.mp3"))
    private val badAnswerSound: Sound = Gdx.audio.newSound(Gdx.files.internal("badanswer.mp3"))
    private var question: Question? = null
    private val font: BitmapFont

    private fun loadQuestion() {
        val r = Random()
        val abcd = "abcd"
        val questionsGoodAnswer = abcd[r.nextInt(abcd.length)]
        val addSubtractMultiplyDivide = "+-x/"
        val whichArithmeticOperator = addSubtractMultiplyDivide[r.nextInt(abcd.length)]
        val randomNumber1 = r.nextInt(15) - 1
        val randomNumber2 = r.nextInt(15) - 1
        //Set is collection that store only unique values
        val answers: MutableSet<Int> = LinkedHashSet()
        //So this loop works until all values will be set, and all will be unique
        while (answers.size < 4) {
            if (answers.size == 0) {
                when (whichArithmeticOperator) {
                    '+' -> answers.add(randomNumber1 + randomNumber2)
                    '-' -> answers.add(randomNumber1 - randomNumber2)
                    'x' -> answers.add(randomNumber1 * randomNumber2)
                    '/' -> answers.add(randomNumber1 / randomNumber2)
                }
            } else {
                val next = answers.iterator().next() + (r.nextInt(20) - 10)
                answers.add(next)
            }
        }
        //-4 index is first number, this is correct answer
        question = when (questionsGoodAnswer) {
            'a' -> {
                Question("$randomNumber1 $whichArithmeticOperator $randomNumber2",
                        "" + answers.toTypedArray()[answers.size - 4],
                        "" + answers.toTypedArray()[answers.size - 1],
                        "" + answers.toTypedArray()[answers.size - 2],
                        "" + answers.toTypedArray()[answers.size - 3],
                        'a')
            }
            'b' -> {
                Question("$randomNumber1 $whichArithmeticOperator $randomNumber2",
                        "" + answers.toTypedArray()[answers.size - 1],
                        "" + answers.toTypedArray()[answers.size - 4],
                        "" + answers.toTypedArray()[answers.size - 2],
                        "" + answers.toTypedArray()[answers.size - 3],
                        'b')
            }
            'c' -> {
                Question("$randomNumber1 $whichArithmeticOperator $randomNumber2",
                        "" + answers.toTypedArray()[answers.size - 1],
                        "" + answers.toTypedArray()[answers.size - 2],
                        "" + answers.toTypedArray()[answers.size - 4],
                        "" + answers.toTypedArray()[answers.size - 3],
                        'c')
            }
            else -> {
                Question("$randomNumber1 $whichArithmeticOperator $randomNumber2",
                        "" + answers.toTypedArray()[answers.size - 1],
                        "" + answers.toTypedArray()[answers.size - 2],
                        "" + answers.toTypedArray()[answers.size - 3],
                        "" + answers.toTypedArray()[answers.size - 4],
                        'd')
            }
        }
    }

    override fun handleInput() {}
    override fun update(dt: Float) = handleInput()

    override fun render(sb: SpriteBatch?) {
        sb!!.begin()
        sb.draw(background,
                playerX - percentOfWidth(0.233333333),
                playerY - percentOfHeight(0.274004684),
                Gdx.graphics.width + percentOfWidth(0.416666667).toFloat(),
                Gdx.graphics.height + percentOfHeight(0.292740047).toFloat())
        sb.draw(aBtn,
                playerX - percentOfWidth(0.1875),
                playerY + percentOfHeight(0.409836066),
                percentOfWidth(0.65).toFloat(),
                percentOfHeight(0.203747073).toFloat())
        sb.draw(bBtn,
                playerX - percentOfWidth(0.1875),
                playerY - percentOfHeight(0.203747073) + percentOfHeight(0.409836066),
                percentOfWidth(0.65).toFloat(),
                percentOfHeight(0.203747073).toFloat())
        sb.draw(cBtn,
                playerX - percentOfWidth(0.1875),
                playerY - 2 * percentOfHeight(0.203747073) + percentOfHeight(0.409836066),
                percentOfWidth(0.65).toFloat(),
                percentOfHeight(0.203747073).toFloat())
        sb.draw(dBtn,
                playerX - percentOfWidth(0.1875),
                playerY - 3 * percentOfHeight(0.203747073) + percentOfHeight(0.409836066),
                percentOfWidth(0.65).toFloat(),
                percentOfHeight(0.203747073).toFloat())
        font.draw(sb, question!!.question, playerX, playerY + percentOfHeight(0.807962529))
        font.draw(sb, question!!.answerA, playerX + percentOfWidth(0.458333333), playerY + percentOfHeight(0.573770492))
        font.draw(sb, question!!.answerB, playerX + percentOfWidth(0.458333333), playerY + percentOfHeight(0.37470726))
        font.draw(sb, question!!.answerC, playerX + percentOfWidth(0.458333333), playerY + percentOfHeight(0.175644028))
        font.draw(sb, question!!.answerD, playerX + percentOfWidth(0.458333333), playerY - percentOfHeight(0.023419204))
        sb.end()
    }

    override fun dispose() {
        background.dispose()
        aBtn.dispose()
        bBtn.dispose()
        cBtn.dispose()
        dBtn.dispose()
        badAnswerSound.dispose()
        font.dispose()
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int) {
        //Width and height are 70% of normal size because of used camera.zoom in PlayState class
        //First 2 values are point of actual screen, not SpriteBatch screen (which store all things)
        val aTextureBounds: Rectangle = Rectangle(percentOfWidth(0.03958333283662796).toFloat(), percentOfHeight(0.3161592483520508).toFloat(), percentOfWidth(0.479166675).toFloat(), percentOfHeight(0.142857134).toFloat())
        val bTextureBounds: Rectangle = Rectangle(percentOfWidth(0.03958333283662796).toFloat(), percentOfHeight(0.47306790947914124).toFloat(), percentOfWidth(0.479166675).toFloat(), percentOfHeight(0.142857134).toFloat())
        val cTextureBounds: Rectangle = Rectangle(percentOfWidth(0.03958333283662796).toFloat(), percentOfHeight(0.6299765706062317).toFloat(), percentOfWidth(0.479166675).toFloat(), percentOfHeight(0.142857134).toFloat())
        val dTextureBounds: Rectangle = Rectangle(percentOfWidth(0.03958333283662796).toFloat(), percentOfHeight(0.7857142686843872).toFloat(), percentOfWidth(0.479166675).toFloat(), percentOfHeight(0.142857134).toFloat())
        if (aTextureBounds.contains(x, y)) {
            if (question!!.correctAnswer == 'a') {
                goodAnswerSound.play()
                gsm.pop()
                dispose()
            } else {
                badAnswerSound.play()
                gsm.pop()
                gsm.set(GameOverState(gsm, playerX, playerY))
            }
        }
        if (bTextureBounds.contains(x, y)) {
            if (question!!.correctAnswer == 'b') {
                goodAnswerSound.play()
                gsm.pop()
                dispose()
            } else {
                badAnswerSound.play()
                gsm.pop()
                gsm.set(GameOverState(gsm, playerX, playerY))
            }
        }
        if (cTextureBounds.contains(x, y)) {
            if (question!!.correctAnswer == 'c') {
                goodAnswerSound.play()
                gsm.pop()
                dispose()
            } else {
                badAnswerSound.play()
                gsm.pop()
                gsm.set(GameOverState(gsm, playerX, playerY))
            }
        }
        if (dTextureBounds.contains(x, y)) {
            if (question!!.correctAnswer == 'd') {
                goodAnswerSound.play()
                gsm.pop()
                dispose()
            } else {
                badAnswerSound.play()
                gsm.pop()
                gsm.set(GameOverState(gsm, playerX, playerY))
            }
        }
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}

    init {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontParameter()
        parameter.size = percentOfWidth(0.233333333).toInt()
        font = generator.generateFont(parameter)
        generator.dispose()
        loadQuestion()
    }
}