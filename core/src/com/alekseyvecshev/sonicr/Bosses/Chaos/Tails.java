package com.alekseyvecshev.sonicr.Bosses.Chaos;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 05.06.2016.
 */
public class Tails {

    private static final int SPEED = 7;
    private static final int CHANGE_DT = 100;

    TextureAtlas move;
    Animation moveAnimation;

    TextureAtlas attack;
    Animation attackAnimation;

    boolean isFly;
    boolean isAttack;
    float elapsedTimes = 0;
    float timeState = 0;

    Vector2 position;
    Vector2 posAttack;

    private enum EnumState{Stay, Fly, Attack};
    private EnumState state;

    public Tails(){

        position = new Vector2();
        isFly = true;
        isAttack = false;
        move = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//moveTails.txt"));
        moveAnimation = new Animation(1/8f, move.getRegions());
        state = EnumState.Stay;

        attack = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//attackAnimation.txt"));
        attackAnimation = new Animation(1/15f, attack.getRegions());

    }


    private void FlySet(Vector3 posCamera){
        System.out.println(isFly);
        if (isFly){
            isFly = false;
            state = EnumState.Fly;
            position.set(posCamera.x -SonicRGame.WIDTH, 550);

        }
    }
    public void update(float dt, Vector3 posCamera){
        elapsedTimes += dt;
        FlySet(posCamera);
        if (state == EnumState.Fly){
            position.add(SPEED * CHANGE_DT * dt, 0);
        }
        System.out.println(position);
    }

    public void render(SpriteBatch sb){
        if (state == EnumState.Fly){
            sb.draw(moveAnimation.getKeyFrame(elapsedTimes, true),position.x, position.y);
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

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
