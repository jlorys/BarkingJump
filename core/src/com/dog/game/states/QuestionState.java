package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    Texture background;
    Texture aBtn, bBtn, cBtn, dBtn;
    Float playerX, playerY;
    Rectangle aTextureBounds, bTextureBounds, cTextureBounds, dTextureBounds;
    Sound goodAnswerSound, badAnswerSound;
    Character questionsGoodAnswer;
    Question question;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;

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
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 112;
        font = generator.generateFont(parameter);
        generator.dispose();
        loadQuestion();
    }

    private void loadQuestion() {
        Random r = new Random();
        String abcd = "abcd";
        questionsGoodAnswer = abcd.charAt(r.nextInt(abcd.length()));

        Integer randomNumber1 = r.nextInt(15);
        Integer randomNumber2 = r.nextInt(15);

        //Set is collection that store only unique values
        Set<Integer> badAnswers = new LinkedHashSet<Integer>();
        //So this loop works until all values will be set, and all will be unique
        while (badAnswers.size() < 4) {
            if(badAnswers.size() == 0){
                badAnswers.add(randomNumber1 * randomNumber2);
            }else{
                Integer next = (randomNumber1 * randomNumber2) + (r.nextInt(20) - 10);
                badAnswers.add(next);
            }
        }

        //-4 index is first number, this is correct answer
        if (questionsGoodAnswer == 'a') {
            question = new Question(randomNumber1 + " * " + randomNumber2,
                    "" + badAnswers.toArray()[badAnswers.size() - 4],
                    "" + badAnswers.toArray()[badAnswers.size() - 1],
                    "" + badAnswers.toArray()[badAnswers.size() - 2],
                    "" + badAnswers.toArray()[badAnswers.size() - 3],
                    'a');
        } else if (questionsGoodAnswer == 'b') {
            question = new Question(randomNumber1 + " * " + randomNumber2,
                    "" + badAnswers.toArray()[badAnswers.size() - 1],
                    "" + badAnswers.toArray()[badAnswers.size() - 4],
                    "" + badAnswers.toArray()[badAnswers.size() - 2],
                    "" + badAnswers.toArray()[badAnswers.size() - 3],
                    'b');
        } else if (questionsGoodAnswer == 'c') {
            question = new Question(randomNumber1 + " * " + randomNumber2,
                    "" + badAnswers.toArray()[badAnswers.size() - 1],
                    "" + badAnswers.toArray()[badAnswers.size() - 2],
                    "" + badAnswers.toArray()[badAnswers.size() - 4],
                    "" + badAnswers.toArray()[badAnswers.size() - 3],
                    'c');
        } else {
            question = new Question(randomNumber1 + " * " + randomNumber2,
                    "" + badAnswers.toArray()[badAnswers.size() - 1],
                    "" + badAnswers.toArray()[badAnswers.size() - 2],
                    "" + badAnswers.toArray()[badAnswers.size() - 3],
                    "" + badAnswers.toArray()[badAnswers.size() - 4],
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
        sb.draw(background, playerX - 112, playerY - 204, Gdx.graphics.getWidth() + 200, Gdx.graphics.getHeight() + 250);
        sb.draw(aBtn, playerX - 90, playerY + 350);
        sb.draw(bBtn, playerX - 90, playerY - aBtn.getHeight() + 350);
        sb.draw(cBtn, playerX - 90, playerY - 2 * aBtn.getHeight() + 350);
        sb.draw(dBtn, playerX - 90, playerY - 3 * aBtn.getHeight() + 350);
        font.draw(sb, question.getQuestion(), playerX, playerY + 690);
        font.draw(sb, question.getAnswerA(), playerX + 220, playerY + 490);
        font.draw(sb, question.getAnswerB(), playerX + 220, playerY + 320);
        font.draw(sb, question.getAnswerC(), playerX + 220, playerY + 150);
        font.draw(sb, question.getAnswerD(), playerX + 220, playerY - 20);
        sb.end();
    }

    public void dispose() {
        background.dispose();
        aBtn.dispose();
    }

    @Override
    public void tap(float x, float y, int count, int button) {
        //Width and height are 70% of normal size because of used camera.zoom in PlayState class
        //First 2 values are point of actual screen, not SpriteBatch screen (which store all things)
        this.aTextureBounds = new Rectangle(22, 260, 228, 133);
        this.bTextureBounds = new Rectangle(22, 404, 228, 133);
        this.cTextureBounds = new Rectangle(22, 545, 228, 133);
        this.dTextureBounds = new Rectangle(22, 689, 228, 133);

        if (aTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('a')) {
                gsm.pop();
                goodAnswerSound.play();
            } else {
                gsm.pop();
                badAnswerSound.play();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }

        if (bTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('b')) {
                gsm.pop();
                goodAnswerSound.play();
            } else {
                gsm.pop();
                badAnswerSound.play();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }

        if (cTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('c')) {
                gsm.pop();
                goodAnswerSound.play();
            } else {
                gsm.pop();
                badAnswerSound.play();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }

        if (dTextureBounds.contains(x, y)) {
            if (question.getCorrectAnswer().equals('d')) {
                gsm.pop();
                goodAnswerSound.play();
            } else {
                gsm.pop();
                badAnswerSound.play();
                gsm.set(new GameOverState(gsm, playerX, playerY));
            }
        }
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {

    }
}
