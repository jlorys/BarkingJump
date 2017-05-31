package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.dog.game.DatabaseInitialization;

public class MenuState extends State {

    Texture background;
    Texture playBtn, logo;
    Rectangle textureBounds;
    DatabaseInitialization db = new DatabaseInitialization();
    Float record = db.getActualRecord();
    FreeTypeFontGenerator generator;
    FreeTypeFontParameter parameter;
    BitmapFont font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        logo = new Texture("logo.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 32;
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
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(playBtn, 189, 397);
        sb.draw(logo, 25, 600);
        font.draw(sb, "Rekord: ", 15, 300);
        font.draw(sb, record.toString(), 15, 250);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        logo.dispose();
        font.dispose();
    }

    @Override
    public void tap(float x, float y, int count, int button) {
        this.textureBounds = new Rectangle(189, 397,
                playBtn.getWidth(),
                playBtn.getHeight());

        if (textureBounds.contains(x, y)) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {

    }

}
