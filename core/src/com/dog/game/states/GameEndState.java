package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.dog.game.DatabaseInitialization;

class GameEndState extends State {

    private Texture background;
    private Texture playBtn;
    private Float playerX, playerY, time;
    private DatabaseInitialization db = new DatabaseInitialization();
    private Float record = db.getActualRecord();
    private BitmapFont font;

    GameEndState(GameStateManager gsm, Float playerX, Float playerY, Float time) {
        super(gsm);
        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;
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
        parameter.size = (int)percentOfWidth(0.191666667);
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
        sb.draw(background, playerX - percentOfWidth(0.253333), playerY - percentOfHeight(0.262275), Gdx.graphics.getWidth() + percentOfWidth(0.40), Gdx.graphics.getHeight() + percentOfHeight(0.40));
        sb.draw(playBtn, playerX, playerY + percentOfHeight(0.409836));
        font.draw(sb, "Koniec", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.866510539));
        font.draw(sb, "Twój czas: ", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.351288056));
        font.draw(sb, time.toString(), playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.234192037));
        font.draw(sb, "Rekord: ", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.117096));
        font.draw(sb, record.toString(), playerX - percentOfWidth(0.1875), playerY);
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
        Rectangle textureBounds;
        textureBounds = new Rectangle(percentOfWidth(0.183333333), percentOfHeight(0.411007026), playBtn.getWidth(), playBtn.getHeight());

        if (textureBounds.contains(x, y)) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {

    }
}