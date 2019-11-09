package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.dog.game.domain.Question;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/*
Drawing background and buttons is made by SpriteBatch common for all classes
tap method use actual 480x854 screen size
*/
public class QuestionState extends State {
    private Texture background;
    private Texture aBtn, bBtn, cBtn, dBtn;
    private Float playerX, playerY;
    private Sound goodAnswerSound, badAnswerSound;
    private Question question;
    private BitmapFont font;

    public QuestionState(GameStateManager gsm, Float playerX, Float playerY) {
        super(gsm);
        background = new Texture("bg.png");
        aBtn = new Texture("abtn.png");
        bBtn = new Texture("bbtn.png");
        cBtn = new Texture("cbtn.png");
        dBtn = new Texture("dbtn.png");
        goodAnswerSound = Gdx.audio.newSound(Gdx.files.internal("goodanswer.mp3"));
        badAnswerSound = Gdx.audio.newSound(Gdx.files.internal("badanswer.mp3"));
        this.playerX = playerX;
        this.playerY = playerY;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) percentOfWidth(0.233333333);
        font = generator.generateFont(parameter);
        generator.dispose();
        loadQuestion();
    }

    private void loadQuestion() {

        Random r = new Random();
        String abcd = "abcd";
        Character questionsGoodAnswer = abcd.charAt(r.nextInt(abcd.length()));
        String addSubtractMultiplyDivide = "+-*/";
        Character whichArithmeticOperator = addSubtractMultiplyDivide.charAt(r.nextInt(abcd.length()));

        Integer randomNumber1 = r.nextInt(15) - 1;
        Integer randomNumber2 = r.nextInt(15) - 1;

        //Set is collection that store only unique values
        Set<Integer> answers = new LinkedHashSet<Integer>();
        //So this loop works until all values will be set, and all will be unique
        while (answers.size() < 4) {
            if (answers.size() == 0) {
                if (whichArithmeticOperator == '+') {
                    answers.add(randomNumber1 + randomNumber2);
                } else if (whichArithmeticOperator == '-') {
                    answers.add(randomNumber1 - randomNumber2);
                } else if (whichArithmeticOperator == '*') {
                    answers.add(randomNumber1 * randomNumber2);
                } else if (whichArithmeticOperator == '/') {
                    answers.add(randomNumber1 / randomNumber2);
                }
            } else {
                Integer next = answers.iterator().next() + (r.nextInt(20) - 10);
                answers.add(next);
            }
        }

        //-4 index is first number, this is correct answer
        if (questionsGoodAnswer == 'a') {
            question = new Question(randomNumber1 + " " + whichArithmeticOperator + " " + randomNumber2,
                    "" + answers.toArray()[answers.size() - 4],
                    "" + answers.toArray()[answers.size() - 1],
                    "" + answers.toArray()[answers.size() - 2],
                    "" + answers.toArray()[answers.size() - 3],
                    'a');
        } else if (questionsGoodAnswer == 'b') {
            question = new Question(randomNumber1 + " " + whichArithmeticOperator + " " + randomNumber2,
                    "" + answers.toArray()[answers.size() - 1],
                    "" + answers.toArray()[answers.size() - 4],
                    "" + answers.toArray()[answers.size() - 2],
                    "" + answers.toArray()[answers.size() - 3],
                    'b');
        } else if (questionsGoodAnswer == 'c') {
            question = new Question(randomNumber1 + " " + whichArithmeticOperator + " " + randomNumber2,
                    "" + answers.toArray()[answers.size() - 1],
                    "" + answers.toArray()[answers.size() - 2],
                    "" + answers.toArray()[answers.size() - 4],
                    "" + answers.toArray()[answers.size() - 3],
                    'c');
        } else {
            question = new Question(randomNumber1 + " " + whichArithmeticOperator + " " + randomNumber2,
                    "" + answers.toArray()[answers.size() - 1],
                    "" + answers.toArray()[answers.size() - 2],
                    "" + answers.toArray()[answers.size() - 3],
                    "" + answers.toArray()[answers.size() - 4],
                    'd');
        }
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,
                playerX - percentOfWidth(0.233333333),
                playerY - percentOfHeight(0.274004684),
                Gdx.graphics.getWidth() + percentOfWidth(0.416666667),
                Gdx.graphics.getHeight() + percentOfHeight(0.292740047));
        sb.draw(aBtn,
                playerX - percentOfWidth(0.1875),
                playerY + percentOfHeight(0.409836066),
                percentOfWidth(0.65),
                percentOfHeight(0.203747073));
        sb.draw(bBtn,
                playerX - percentOfWidth(0.1875),
                playerY - percentOfHeight(0.203747073) + percentOfHeight(0.409836066),
                percentOfWidth(0.65),
                percentOfHeight(0.203747073));
        sb.draw(cBtn,
                playerX - percentOfWidth(0.1875),
                playerY - 2 * percentOfHeight(0.203747073) + percentOfHeight(0.409836066),
                percentOfWidth(0.65),
                percentOfHeight(0.203747073));
        sb.draw(dBtn,
                playerX - percentOfWidth(0.1875),
                playerY - 3 * percentOfHeight(0.203747073) + percentOfHeight(0.409836066),
                percentOfWidth(0.65),
                percentOfHeight(0.203747073));
        font.draw(sb, question.getQuestion(), playerX, playerY + percentOfHeight(0.807962529));
        font.draw(sb, question.getAnswerA(), playerX + percentOfWidth(0.458333333), playerY + percentOfHeight(0.573770492));
        font.draw(sb, question.getAnswerB(), playerX + percentOfWidth(0.458333333), playerY + percentOfHeight(0.37470726));
        font.draw(sb, question.getAnswerC(), playerX + percentOfWidth(0.458333333), playerY + percentOfHeight(0.175644028));
        font.draw(sb, question.getAnswerD(), playerX + percentOfWidth(0.458333333), playerY - percentOfHeight(0.023419204));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        aBtn.dispose();
        bBtn.dispose();
        cBtn.dispose();
        dBtn.dispose();
        badAnswerSound.dispose();
        font.dispose();
    }

    @Override
    public void tap(float x, float y, int count, int button) {
        Rectangle aTextureBounds, bTextureBounds, cTextureBounds, dTextureBounds;
        //Width and height are 70% of normal size because of used camera.zoom in PlayState class
        //First 2 values are point of actual screen, not SpriteBatch screen (which store all things)
        aTextureBounds = new Rectangle(percentOfWidth(0.03958333283662796), percentOfHeight(0.3161592483520508), percentOfWidth(0.479166675), percentOfHeight(0.142857134));
        bTextureBounds = new Rectangle(percentOfWidth(0.03958333283662796), percentOfHeight(0.47306790947914124), percentOfWidth(0.479166675), percentOfHeight(0.142857134));
        cTextureBounds = new Rectangle(percentOfWidth(0.03958333283662796), percentOfHeight(0.6299765706062317), percentOfWidth(0.479166675), percentOfHeight(0.142857134));
        dTextureBounds = new Rectangle(percentOfWidth(0.03958333283662796), percentOfHeight(0.7857142686843872), percentOfWidth(0.479166675), percentOfHeight(0.142857134));

        if (aTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('a')) {
                goodAnswerSound.play();
                gsm.pop();
                dispose();
            } else {
                badAnswerSound.play();
                gsm.pop();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }

        if (bTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('b')) {
                goodAnswerSound.play();
                gsm.pop();
                dispose();
            } else {
                badAnswerSound.play();
                gsm.pop();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }

        if (cTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('c')) {
                goodAnswerSound.play();
                gsm.pop();
                dispose();
            } else {
                badAnswerSound.play();
                gsm.pop();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }

        if (dTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('d')) {
                goodAnswerSound.play();
                gsm.pop();
                dispose();
            } else {
                badAnswerSound.play();
                gsm.pop();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {

    }
}
