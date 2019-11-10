package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.dog.game.DatabaseInitialization;

import java.math.BigDecimal;

class GameEndState extends State {

    private Texture background;
    private Texture playBtn;
    private Float playerX, playerY, time;
    private DatabaseInitialization db = new DatabaseInitialization();
    private Float record = db.getActualRecord();
    private BitmapFont font;
    private BitmapFont fontNewRecord;
    private boolean newRecord;

    GameEndState(GameStateManager gsm, Float playerX, Float playerY, Float time) {
        super(gsm);
        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        this.playerX = playerX;
        this.playerY = playerY;
        this.time = time / 1000;
        this.time = round(this.time, 2);
        if (this.time < record) {
            db.insertRecord(this.time);
            this.newRecord = true;
        }
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) percentOfWidth(0.11);
        font = generator.generateFont(parameter);
        fontNewRecord = generator.generateFont(parameter);
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
        sb.draw(playBtn, playerX, playerY + percentOfHeight(0.409836066), percentOfWidth(0.4), percentOfHeight(0.1));
        font.draw(sb, "Koniec", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.866510539));
        font.draw(sb, "Czas: ", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.351288056));
        font.draw(sb, time.toString(), playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.234192037));
        font.draw(sb, "Rekord: ", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.117096));
        font.draw(sb, record.toString(), playerX - percentOfWidth(0.1875), playerY);
        if(newRecord){
            fontNewRecord.setColor(Color.GREEN);
            fontNewRecord.draw(sb, "Nowy Rekord!", playerX - percentOfWidth(0.1875), playerY - percentOfHeight(0.117096));
        }
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
        textureBounds = new Rectangle(percentOfWidth(0.1833333), percentOfHeight(0.3946135938167572), percentOfWidth(0.297916651), percentOfHeight(0.070257634));

        if (textureBounds.contains(x, y)) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {

    }

    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}