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
    private static final float MAX_STATE_TIME = 15;

    TextureAtlas move;
    Animation moveAnimation;

    TextureAtlas attack;
    Animation attackAnimation;

    boolean isFly;
    boolean isAttack;
    boolean isDamage;
    float elapsedTimes = 0;
    float elapsedTimesAttack = 0;
    float timeState = 0;

    Vector2 position;
    Vector2 posAttack;

    private enum EnumState{Stay, Fly, Attack};
    private EnumState state;

    public Tails(){

        position = new Vector2();
        posAttack = new Vector2();
        isFly = true;
        isAttack = false;
        isDamage = false;
        move = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//moveTails.txt"));
        moveAnimation = new Animation(1/8f, move.getRegions());
        state = EnumState.Stay;

        attack = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//attackTails.txt"));
        attackAnimation = new Animation(1/6f, attack.getRegions());

    }

    private void FlySet(Vector3 posCamera){
        //System.out.println(state);
        if (isFly){
            isFly = false;
            state = EnumState.Fly;
            position.set(posCamera.x -SonicRGame.WIDTH, 550);

        }
        if (position.x > posCamera.x + SonicRGame.WIDTH){
            state = EnumState.Stay;
            timeState = 0;
        }
    }
    private void AttackSet( Vector2 posChaos){
        isAttack = false;
        elapsedTimesAttack = 0;
        state = EnumState.Attack;
        posAttack.x = position.x + 50;
        posAttack.y = posChaos.y + 30;
        state = EnumState.Attack;
        if (posAttack.x + 100 >= posChaos.x &&  posAttack.x - 100 <= posChaos.x){
            System.out.println("!!!!!!!!!!!!!!!!!!");
            isDamage = true;
        }
    }

    private void AttackUpdate(){
        if (attackAnimation.isAnimationFinished(elapsedTimesAttack)){
            posAttack = new Vector2(0 , 0);
        }
    }
    public void update(float dt, Vector3 posCamera, Vector2 posChaos){
        elapsedTimes += dt;
        elapsedTimesAttack += dt;
        timeState += dt;
        FlySet(posCamera);
        if (isAttack){
            AttackSet(posChaos);
        }
        AttackUpdate();
        if (state == EnumState.Fly || state == EnumState.Attack){
            position.add(SPEED * CHANGE_DT * dt, 0);
        }
        if (timeState > MAX_STATE_TIME && state == EnumState.Stay){
            state = EnumState.Fly;
        }
        //System.out.println(position);
    }

    public void render(SpriteBatch sb){
        if (state == EnumState.Fly || state == EnumState.Attack){
            sb.draw(moveAnimation.getKeyFrame(elapsedTimes, true),position.x, position.y);
        }
        if (state == EnumState.Attack) {
            sb.draw(attackAnimation.getKeyFrame(elapsedTimesAttack, false), posAttack.x, posAttack.y);
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

    public boolean isDamage() {
        return isDamage;
    }

    public void setIsDamage(boolean isDamage) {
        this.isDamage = isDamage;
    }
}
