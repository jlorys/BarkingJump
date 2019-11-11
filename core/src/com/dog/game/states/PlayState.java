package com.dog.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.dog.game.domain.JumpDogHero;
import com.dog.game.domain.Platform;

import java.util.Arrays;
import java.util.List;

class PlayState extends State {

    private static final int FRAME_COLS = 1, FRAME_ROWS = 16;
    private final float gravity = -percentOfHeight(0.02);
    private final float playerVelocity = percentOfHeight(1.0);
    private final float platformsDistance = percentOfHeight(0.3);
    private final float platformsHeight = percentOfHeight(0.05625);
    private Animation<TextureRegion> walkAnimationLeft, walkAnimationRight;
    private Music music;
    private Texture playerTextureWalkLeft, playerTextureJumpLeft, playerTextureWalkRight, playerTextureJumpRight,
            platformTexture, platformQuestionTexture, background, stone, stoneWaterRight, stoneWaterLeft, water;
    private JumpDogHero player;
    private Array<Platform> platformArray;
    private OrthographicCamera camera;
    private long start = TimeUtils.millis();
    private TextureRegion currentFrame;
    private Texture currentJumpTexture;
    private List<Boolean> isQuestionAnswered = Arrays.asList(new Boolean[10]);
    private float time;

    PlayState(GameStateManager gsm) {
        super(gsm);
        loadData();
    }

    private void loadData() {
        TextureRegion[] walkFramesLeft, walkFramesRight;
        stone = new Texture(Gdx.files.internal("stone.png"));
        stoneWaterRight = new Texture(Gdx.files.internal("stonewaterright.png"));
        stoneWaterLeft = new Texture(Gdx.files.internal("stonewaterleft.png"));
        water = new Texture(Gdx.files.internal("water.png"));
        playerTextureWalkLeft = new Texture(Gdx.files.internal("dogleft1.png"));
        playerTextureJumpLeft = new Texture(Gdx.files.internal("dog jump left.png"));
        playerTextureWalkRight = new Texture(Gdx.files.internal("dogright1.png"));
        playerTextureJumpRight = new Texture(Gdx.files.internal("dog jump right.png"));
        walkFramesLeft = DivideToRegion(playerTextureWalkLeft);
        walkFramesRight = DivideToRegion(playerTextureWalkRight);
        walkAnimationLeft = new Animation<TextureRegion>(0.025f, walkFramesLeft);
        walkAnimationRight = new Animation<TextureRegion>(0.025f, walkFramesRight);
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        platformTexture = new Texture(Gdx.files.internal("platform.png"));
        platformQuestionTexture = new Texture(Gdx.files.internal("questionplatform.png"));
        background = new Texture(Gdx.files.internal("bg.png"));
        player = new JumpDogHero(walkFramesLeft[0], Gdx.audio.newSound(Gdx.files.internal("dog.ogg")));
        currentFrame = walkAnimationLeft.getKeyFrame(player.getStateTimeLeft(), true);
        currentJumpTexture = playerTextureJumpLeft;
        platformArray = new Array<Platform>();
        //1 is 100%
        camera = new OrthographicCamera(percentOfWidth(1), percentOfHeight(1));

        for (int i = 0; i <= 9; i++) {
            isQuestionAnswered.set(i, false);
        }

        for (int i = 1; i <= 50; i++) {
            Platform p = new Platform(platformTexture, platformsHeight);
            p.x = MathUtils.random(percentOfWidth(0.7));
            p.y = platformsDistance * i;
            Platform pQuestion = new Platform(platformQuestionTexture, platformsHeight);
            pQuestion.x = MathUtils.random(percentOfWidth(0.7));
            pQuestion.y = platformsDistance * i;

            if (i % 5 == 0) platformArray.add(pQuestion);
            else platformArray.add(p);
        }
    }

    private TextureRegion[] DivideToRegion(Texture texture) {
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        return walkFrames;
    }

    private boolean isPlayerOnPlatform(Platform p) {
        return player.getJumpVelocity() <= 0 && player.overlaps(p) && !(player.y <= p.y);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            player.x -= 400 * Gdx.graphics.getDeltaTime();
            currentJumpTexture = playerTextureJumpLeft;

            if (player.isCanJump()) {
                player.setStateTimeLeft(player.getStateTimeLeft() + Gdx.graphics.getDeltaTime() * 0.3f); // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationLeft.getKeyFrame(player.getStateTimeLeft(), true);
                player.setTexture(currentFrame);
                currentJumpTexture = playerTextureJumpLeft;
            } else {
                player.setTexture(new TextureRegion(currentJumpTexture));
                currentFrame = walkAnimationLeft.getKeyFrame(player.getStateTimeLeft(), true);
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            player.x += 400 * Gdx.graphics.getDeltaTime();
            currentJumpTexture = playerTextureJumpRight;

            if (player.isCanJump()) {
                player.setStateTimeRight(player.getStateTimeRight() + Gdx.graphics.getDeltaTime() * 0.3f); // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationRight.getKeyFrame(player.getStateTimeRight(), true);
                player.setTexture(currentFrame);
                currentJumpTexture = playerTextureJumpRight;
            } else {
                player.setTexture(new TextureRegion(currentJumpTexture));
                currentFrame = walkAnimationRight.getKeyFrame(player.getStateTimeRight(), true);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        music.play();

        camera.update();
        camera.zoom = 1.3f;
        camera.position.set(player.x + percentOfWidth(0.416666667), player.y + percentOfHeight(0.351288056), 0);

        if (player.isCanJump()) {
            player.setTexture(currentFrame);
        } else {
            player.setTexture(new TextureRegion(currentJumpTexture));
        }

        player.y += player.getJumpVelocity() * Gdx.graphics.getDeltaTime();

        if (player.y > 0) {
            player.setJumpVelocity(player.getJumpVelocity() + gravity);
        } else {
            player.y = 0;
            player.setCanJump(true);
            player.setJumpVelocity(0);
        }

        //This code makes right limit for dog walking space
        if (player.x > percentOfWidth(4.4)) {
            player.x = percentOfWidth(4.4);
        }

        //This code makes left limit for dog walking space
        if (player.x < percentOfWidth(-2.1)) {
            player.x = percentOfWidth(-2.1);
        }

        for (Platform p : platformArray) {

            if (isPlayerOnPlatform(p)) {
                player.setCanJump(true);
                player.setJumpVelocity(0);
                player.y = p.y + p.height;

                for (int i = 5; i <= 50; i += 5) {
                    if (p.getY() == platformsDistance * i && !isQuestionAnswered.get((i / 5) - 1)) {
                        gsm.push(new QuestionState(gsm, player.x, player.y));
                        isQuestionAnswered.set((i / 5) - 1, true);
                    } else if (p.getY() == platformsDistance * 50 && isQuestionAnswered.get(9)) {
                        if (this.time == 0) {
                            long stop = TimeUtils.millis();
                            this.time = (stop - start);
                            gsm.set(new GameEndState(gsm, player.x, player.y, time));
                            dispose();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);

        drawTheTower(sb);
        drawGroundAndWater(sb);
        drawPlatforms(sb);
        drawDogPlayer(sb);

        sb.end();
    }

    private void drawDogPlayer(SpriteBatch sb) {
        player.draw(sb, percentOfWidth(0.816666667), percentOfHeight(0.221311475));
    }

    private void drawPlatforms(SpriteBatch sb) {
        for (Platform p : platformArray) {
            p.setWidth(percentOfWidth(1.05));
            p.draw(sb, percentOfWidth(0.5625), percentOfHeight(0.05625));
        }
    }

    private void drawGroundAndWater(SpriteBatch sb) {
        int x = -(int) percentOfWidth(4.375);
        for (long j = 0; j < 19; j++) {
            if (x < -percentOfWidth(2.5))
                sb.draw(water, x, -percentOfHeight(0.351288056), percentOfWidth(0.625), percentOfHeight(0.351288056));
            else if (x == -percentOfWidth(2.5))
                sb.draw(stoneWaterLeft, x, -percentOfHeight(0.351288056), percentOfWidth(0.625), percentOfHeight(0.351288056));
            else if (j < 15)
                sb.draw(stone, x, -percentOfHeight(0.351288056), percentOfWidth(0.625), percentOfHeight(0.351288056));
            else if (j == 15)
                sb.draw(stoneWaterRight, x, -percentOfHeight(0.351288056), percentOfWidth(0.625), percentOfHeight(0.351288056));
            else
                sb.draw(water, x, -percentOfHeight(0.351288056), percentOfWidth(0.625), percentOfHeight(0.351288056));
            x += percentOfWidth(0.625);

        }
    }

    private void drawTheTower(SpriteBatch sb) {
        int y = 0;
        for (long i = 0; i < 35; i++) {
            long x = -percentOfWidth(1.2);
            for (int j = 0; j < 8; j++) {
                sb.draw(background, x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                x += (int) percentOfWidth(0.6);
            }
            y += percentOfHeight(0.599531616);
        }
    }

    @Override
    public void dispose() {
        music.dispose();
        playerTextureWalkLeft.dispose();
        playerTextureJumpLeft.dispose();
        playerTextureWalkRight.dispose();
        playerTextureJumpRight.dispose();
        platformTexture.dispose();
        platformQuestionTexture.dispose();
        background.dispose();
        stone.dispose();
        stoneWaterRight.dispose();
        stoneWaterLeft.dispose();
        water.dispose();
        currentFrame.getTexture().dispose();
        currentJumpTexture.dispose();
    }

    @Override
    public void tap(float x, float y, int count, int button) {
        player.jump(playerVelocity);
    }

    @Override
    public void pan(float x, float y, float deltaX, float deltaY) {
        float centerX = 200.0f;
        if (centerX < x) {
            player.x += 200 * Gdx.graphics.getDeltaTime();
            currentJumpTexture = playerTextureJumpRight;

            if (player.isCanJump()) {
                player.setStateTimeRight(player.getStateTimeRight() + Gdx.graphics.getDeltaTime() * 0.3f); // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationRight.getKeyFrame(player.getStateTimeRight(), true);
                player.setTexture(currentFrame);
                currentJumpTexture = playerTextureJumpRight;
            } else {
                player.setTexture(new TextureRegion(currentJumpTexture));
                currentFrame = walkAnimationRight.getKeyFrame(player.getStateTimeRight(), true);
            }
        } else {
            player.x -= 200 * Gdx.graphics.getDeltaTime();
            currentJumpTexture = playerTextureJumpLeft;

            if (player.isCanJump()) {
                player.setStateTimeLeft(player.getStateTimeLeft() + Gdx.graphics.getDeltaTime() * 0.3f); // Accumulate elapsed animation time
                // Get current frame of animation for the current stateTimeLeft
                currentFrame = walkAnimationLeft.getKeyFrame(player.getStateTimeLeft(), true);
                player.setTexture(currentFrame);
                currentJumpTexture = playerTextureJumpLeft;
            } else {
                player.setTexture(new TextureRegion(currentJumpTexture));
                currentFrame = walkAnimationLeft.getKeyFrame(player.getStateTimeLeft(), true);
            }
        }
    }
}
