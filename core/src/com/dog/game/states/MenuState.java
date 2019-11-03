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

    private Texture background;
    private Texture playBtn, logo;
    private Rectangle textureBounds;
    private DatabaseInitialization db = new DatabaseInitialization();
    private Float record = db.getActualRecord();
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    private BitmapFont font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        logo = new Texture("logo.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = (int) percentOfWidth(0.066666667);
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
        sb.draw(playBtn, percentOfWidth(0.39375), percentOfHeight(0.46487), percentOfWidth(0.210416687), percentOfHeight(0.067916906));
        sb.draw(logo, percentOfWidth(0.0520833), percentOfHeight(0.70257), percentOfWidth(0.891666667), percentOfHeight(0.119437939));
        font.draw(sb, "Rekord: ", percentOfWidth(0.03125), percentOfHeight(0.35128));
        font.draw(sb, record.toString(), percentOfWidth(0.03125), percentOfHeight(0.29274));
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
        this.textureBounds = new Rectangle(percentOfWidth(0.39375), percentOfHeight(0.46487),
                percentOfWidth(0.210416687),
                percentOfHeight(0.067916906));

        if (textureBounds.contains(x, y)) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {

    }

}
