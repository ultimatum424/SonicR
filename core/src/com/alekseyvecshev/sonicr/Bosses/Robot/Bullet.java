package com.alekseyvecshev.sonicr.Bosses.Robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import java.util.Random;

/**
 * Created by Алексей on 24.04.2016.
 */
public class Bullet {
    private static final int BULLET_QUANTITY = 10;
    private static final int SPEED = 6;
    private static final int CHANGE_DT = 100;
    private float elapsedTime = 0;
    private TextureAtlas moveTexture;
    private TextureAtlas dieTexture;
    private Animation moveAnimation;
    private Animation dieAnimation;

    private Vector2 position;
    private Rectangle collision;
    private Random rand;

    public Bullet(int i, int posSonic) {
        rand = new Random();
        moveTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//moveBullet.txt"));
        dieTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//moveBullet.txt"));
        moveAnimation = new Animation(1/15f, moveTexture.getRegions());
        dieAnimation = new Animation(1/15f, dieTexture.getRegions());

        position = new Vector2((i + 3000 + posSonic + moveTexture.getRegions().get(0).getRegionWidth()),
                (130 + rand.nextInt(3) * 180));
        collision = new Rectangle(50, 60, 0, 0);
    }

    public void update(float dt){
        elapsedTime += dt;
        position.add(-dt * SPEED * CHANGE_DT, 0);
        collision.setPosition(position);
    }


    public Vector2 getPosition() {
        return position;
    }

    public static int getBulletQuantity() {
        return BULLET_QUANTITY;
    }

    public Animation getMoveAnimation() {
        return moveAnimation;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
}
