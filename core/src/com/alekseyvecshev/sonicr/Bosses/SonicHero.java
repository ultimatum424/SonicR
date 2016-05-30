package com.alekseyvecshev.sonicr.Bosses;

import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Алексей on 24.04.2016.
 */
public class SonicHero {
    private static final int SPEED = 4;
    private static final int SPEED_BOOST = 2;
    private static final int CHANGE_DT = 100;
    private static final int MAX_LEVEL_SPINDASH = 2;
    private static final int MAX_HP = 20;
    private Vector2 position;
    private TextureAtlas textureAtlasRun;
    private Animation animationRun;
    private TextureAtlas textureAtlasSpinDash;
    private Animation animationSpinDash;
    private Rectangle collision;
    private float elapsedTime = 0;
    private double timeSpinDash = 0;
    private double levelSpinDash = 0;
    private int positionPlatform = 1;
    private int hp = MAX_HP;
    HelpersTool helpersTool;

    public Vector2 getPosition() {
        return position;
    }

    public void setTimeSpinDash(double timeSpinDash) {
        this.timeSpinDash = timeSpinDash;
    }

    public double getTimeSpinDash() {
        return timeSpinDash;
    }

    public double getLevelSpinDash() {
        return levelSpinDash;
    }

    public static int getMaxLevelSpindash() {
        return MAX_LEVEL_SPINDASH;
    }

    public int getPositionPlatform() {
        return positionPlatform;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public static int getMaxHp() {
        return MAX_HP;
    }

    public void setPositionPlatform(int positionPlatform) {
        this.positionPlatform = positionPlatform;
    }

    public SonicHero(int x, int y){
        position = new Vector2(x, y);
        textureAtlasRun = new TextureAtlas(Gdx.files.internal("gameScr\\Sonic\\runSonic.txt"));
        animationRun = new Animation(1/15f, textureAtlasRun.getRegions());

        textureAtlasSpinDash = new TextureAtlas(Gdx.files.internal("gameScr\\Sonic\\SpinDash.txt"));
        animationSpinDash = new Animation(1/15f, textureAtlasSpinDash.getRegions());
        collision = new Rectangle();
        helpersTool = new HelpersTool();
    }


    public void update(float dt){
        position.add(dt * CHANGE_DT * SPEED, 0);
        if (positionPlatform == 1)
            position.set(position.x, 120);
        if (positionPlatform == 2)
            position.set(position.x, 300);
        if (positionPlatform == 3)
            position.set(position.x, 480);
        elapsedTime += dt;
        if (timeSpinDash > 0){
            timeSpinDash = timeSpinDash - dt;
            position.add(dt * CHANGE_DT * SPEED_BOOST, 0);
            levelSpinDash = 0;
        }
        if (timeSpinDash  <= 0) {
            timeSpinDash = 0;
            if (levelSpinDash < MAX_LEVEL_SPINDASH) {
                levelSpinDash += dt;
            }
            else {
                levelSpinDash = MAX_LEVEL_SPINDASH;
            }
        }
        if (timeSpinDash == 0) {
            collision.set(helpersTool.setCollision(animationRun, elapsedTime, position));
        }
        else{
            collision.set(helpersTool.setCollision(animationSpinDash, elapsedTime, position));
        }
    }

    public void render(SpriteBatch sb){
        if (timeSpinDash == 0) {
            sb.draw(animationRun.getKeyFrame(elapsedTime, true), position.x, position.y);
        } else {
            sb.draw(animationSpinDash.getKeyFrame(elapsedTime, true), position.x, position.y);
        }
    }
}
