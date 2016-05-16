package com.alekseyvecshev.sonicr.Bosses.Robot;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.alekseyvecshev.sonicr.states.BossRobotState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Алексей on 24.04.2016.
 */
public class BossRobot {
    private static final int SPEED = 4;
    private static final int SPEED_BOOST = 6;
    private static final int CHANGE_DT = 100;
    private static final int MAX_HP = 30;
    private float elapsedTime = 0;
    private Random rand;
    private EnumState state;
    private enum EnumState{Die, Fly, Attack, Bullet, GetDamage};
    private float timeState;
    TextureAtlas moveTexture;
    Animation moveAnimation;
    Vector2 position;
    Vector3 posPlatform;
    Rectangle collision;
    private int hp = MAX_HP;
    HelpersTool helpersTool;

    public BossRobot(){
        moveTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//moveRobot.txt"));
        moveAnimation = new Animation(1/15f, moveTexture.getRegions());
        position = new Vector2(600, 180);
        collision = new Rectangle();
        timeState = 0;
        rand = new Random();
        state = EnumState.Fly;
        helpersTool = new HelpersTool();
    }

    private void AttackMode(float dt){

        position.add(-dt * CHANGE_DT * SPEED_BOOST, 0);
    }

    private void changeState(Vector3 posCamera,  int sizeBullet){
        if ((timeState > 5) && (state == EnumState.Fly)){
            state = EnumState.Attack;
            timeState = 0;
            position.set(position.x, (130 + rand.nextInt(3) * 180));
        }
        if ((position.x < posCamera.x - (SonicRGame.WIDTH / 2)) && (state == EnumState.Attack)) {
            timeState = 0;
            state = EnumState.Bullet;
        }
        if ((sizeBullet == 0) && (state == EnumState.Bullet) && ( timeState > 1)){
            state = EnumState.Fly;
            timeState = 0;
        }
    }
    public void update(float dt, Vector3 posCamera, int sizeBullet){
        elapsedTime += dt;
        timeState += dt;
        collision.set(helpersTool.setCollision(moveAnimation, elapsedTime, position));

        changeState( posCamera, sizeBullet);
        if (state == EnumState.Attack){
            AttackMode(dt);
        }
        if (state == EnumState.Fly){
            position.set(posCamera.x + 500, position.y);
        }
    }

    public void render(SpriteBatch sb){
        sb.draw(moveAnimation.getKeyFrame(elapsedTime, true), position.x, position.y);
    }


    public boolean IsStateBullet(){
        if (state == EnumState.Bullet){
            return true;
        }
        else{
            return  false;
        }
    }

    public float getElapsedTime() {
        return elapsedTime;
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
}
