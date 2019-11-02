package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

class GameOverState extends State {

    private Texture background;
    private Texture playBtn;
    private Float playerX, playerY;
    private BitmapFont font;

    GameOverState(GameStateManager gsm, Float playerX, Float playerY) {
        super(gsm);
        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        this.playerX = playerX;
        this.playerY = playerY;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)percentOfWidth(0.233333333);
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
        sb.draw(playBtn, playerX, playerY + percentOfHeight(0.409836066));
        font.draw(sb, "Game", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.807962529));
        font.draw(sb, "Over", playerX - percentOfWidth(0.1875), playerY + percentOfHeight(0.690866511));
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
