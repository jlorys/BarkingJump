package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.dog.game.DatabaseInitialization;

public class GameEndState extends State {

    Texture background;
    Texture playBtn;
    Rectangle textureBounds;
    Float playerX, playerY, time;
    DatabaseInitialization db = new DatabaseInitialization();
    Float record = db.getActualRecord();
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;

    public GameEndState(GameStateManager gsm, Float playerX, Float playerY, Float time) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        this.playerX = playerX;
        this.playerY = playerY;
        this.time = time / 1000;
        if (this.time < record) {
            db.insertRecord(this.time);
        }
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 92;
        font = generator.generateFont(parameter);
        generator.dispose();
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
        sb.draw(playBtn, playerX, playerY + 350);
        font.draw(sb, "Koniec", playerX - 90, playerY + 740);
        font.draw(sb, "Twój czas: ", playerX - 90, playerY + 300);
        font.draw(sb, time.toString(), playerX - 90, playerY + 200);
        font.draw(sb, "Rekord: ", playerX - 90, playerY + 100);
        font.draw(sb, record.toString(), playerX - 90, playerY);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        font.dispose();
    }

    @Override
    public void tap(float x, float y, int count, int button) {
        this.textureBounds = new Rectangle(88, 351, playBtn.getWidth(), playBtn.getHeight());

        if (textureBounds.contains(x, y)) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {

    }
}