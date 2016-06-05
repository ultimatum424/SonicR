package com.alekseyvecshev.sonicr.Bosses.Chaos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Алексей on 05.06.2016.
 */
public class Tails {

    private static final int SPEED = 3;
    private static final int CHANGE_DT = 100;

    TextureAtlas move;
    Animation moveAnimation;

    TextureAtlas attack;
    Animation attackAnimation;

    boolean isFly;
    boolean isAttack;
    float elapsedTimes = 0;

    Vector2 position;
    Vector2 posAttack;

    public Tails(){

        position = new Vector2();
        isFly = false;
        isAttack = false;
        move = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//moveTails.txt"));
        moveAnimation = new Animation(1/15f, move.getRegions());

        attack = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//attackAnimation.txt"));
        attackAnimation = new Animation(1/15f, attack.getRegions());
    }

    public void update(float dt){
        elapsedTimes += dt;
        if (isFly){
            position.add(SPEED * CHANGE_DT * dt, 0);
        }

    }

    public void render(SpriteBatch sb){
        if (isFly){
            sb.draw(moveAnimation.getKeyFrame(elapsedTimes, false),position.x, position.y);
        }
        if (isAttack){
            sb.draw(attackAnimation.getKeyFrame(elapsedTimes, false),posAttack.x, posAttack.y);
        }
    }

    public boolean isFly() {
        return isFly;
    }

    public void setIsFly(boolean isFly) {
        this.isFly = isFly;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public void setIsAttack(boolean isAttack) {
        this.isAttack = isAttack;
    }
}
