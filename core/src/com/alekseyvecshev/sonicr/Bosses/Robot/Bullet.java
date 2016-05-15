package com.alekseyvecshev.sonicr.Bosses.Robot;

import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import java.util.Random;

/**
 * Created by Алексей on 24.04.2016.
 */
public class Bullet {

    private static final int SPEED = 6;
    private static final int CHANGE_DT = 100;
    private float elapsedTime = 0;
    private float elapsedTimeDie = 0;
    private ArrayBullets arrayBullets;
    private Animation moveAnimation;
    private Animation dieAnimation;
    private Array<Sprite> sprites;
    private HelpersTool helpersTool;
    private TextureAtlas moveTexture;
    private TextureAtlas dieTexture;
    private Vector2 position;
    private Rectangle collision;
    private Random rand;
    private boolean isDie;

    public Bullet(int i, int posSonic) {
        helpersTool = new HelpersTool();
        rand = new Random();
        moveTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//moveBullet.txt"));
        dieTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//dieBullet.txt"));

        moveAnimation = new Animation(1/15f, moveTexture.getRegions());
        dieAnimation = new Animation(1/12f, dieTexture.getRegions());
        //sprites = moveTexture.createSprites();
        position = new Vector2((i + 3000 + posSonic + moveTexture.getRegions().get(0).getRegionWidth()),
                (130 + rand.nextInt(3) * 180));
        collision = new Rectangle();
        isDie = false;
    }


    public void update(float dt){
        elapsedTime += dt;
        if (isDie()){
            elapsedTimeDie += dt;
        }
        position.add(-dt * SPEED * CHANGE_DT, 0);
        if (isDie) {
            collision.set(0,0, 0, 0);
        }
        else {
            collision.set(helpersTool.setCollision(moveAnimation, elapsedTime, position));
            }
    }


    public Vector2 getPosition() {
        return position;
    }


    public Animation getMoveAnimation() {
        return moveAnimation;
    }

    public Animation getDieAnimation() {
        return dieAnimation;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public boolean isDie() {
        return isDie;
    }

    public void setIsDie(boolean isDie) {
        this.isDie = isDie;
    }

    public float getElapsedTimeDie() {
        return elapsedTimeDie;
    }
}
