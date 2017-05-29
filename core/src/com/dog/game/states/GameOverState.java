package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

public class GameOverState extends State {

    Texture background;
    Texture playBtn;
    Rectangle textureBounds;
    Float playerX, playerY;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;

    public GameOverState(GameStateManager gsm, Float playerX, Float playerY) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        this.playerX = playerX;
        this.playerY = playerY;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 112;
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
        font.draw(sb, "Game", playerX - 90, playerY + 690);
        font.draw(sb, "Over", playerX - 90, playerY + 590);
        sb.end();
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
