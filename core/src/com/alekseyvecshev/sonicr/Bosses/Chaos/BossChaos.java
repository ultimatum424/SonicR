package com.alekseyvecshev.sonicr.Bosses.Chaos;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by Алексей on 04.06.2016.
 */
public class BossChaos {

    private static final int MAX_HP = 50;
    private final static float MAX_TIME_STATE = 10;


    Texture stayTexture;
    TextureAtlas rebornTexture;
    Animation rebornAnimation;

    TextureAtlas attackTexture;
    Animation attackAnimation;

    TextureAtlas dieTexture;
    Animation dieAnimation;

    Vector2 position;
    Vector2 posAttack;

    boolean isAttackSetDamage;
    boolean isDie;
    boolean isReborn;

    Rectangle collision;
    private int hp = MAX_HP;
    private Random rand;
    float elapsedTimes = 0;
    HelpersTool helpersTool;
    Vector3 posPlatformY;

    private EnumState state;
    private enum EnumState{Stay, Attack, Bullet, Reborn, Die};
    private float timeState = 0;

    public BossChaos(){
        stayTexture = new Texture("BossStage//BossChaos//stay.png");
        position = new Vector2();
        rand = new Random();

        attackTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//attackAnimation.txt"));
        attackAnimation = new Animation(1/15f, attackTexture.getRegions());

        rebornTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//rebornAnimation.txt"));
        rebornAnimation = new Animation(1/2f, rebornTexture.getRegions());

        dieTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//dieAnimation.txt"));
        dieAnimation = new Animation(1/15f, dieTexture.getRegions());


        posPlatformY = new Vector3(80, 260, 440);
        posAttack = new Vector2();

        state = EnumState.Stay;
    }

    private void ChangeState(){
        if (isDie){
            state = EnumState.Die;
            elapsedTimes = 0;
            timeState = 0;
        }
        else  if (isReborn){
            state= EnumState.Reborn;
            elapsedTimes = 0;
            timeState = 0;
        }
        if (timeState >= MAX_TIME_STATE){
            posAttack = new Vector2(0, 0);
            if (state == EnumState.Stay){
                if (rand.nextBoolean()){
                    state = EnumState.Bullet;
                }
                else {

                    state = EnumState.Attack;
                }
                elapsedTimes = 0;
                timeState = 0;
            }
            else if (state == EnumState.Bullet){
                state = EnumState.Stay;
                timeState = MAX_TIME_STATE - 3;
            }
            else if (state == EnumState.Attack){
                state = EnumState.Stay;
                timeState = MAX_TIME_STATE - 3;
            }
        }
    }

    private void AttackUpdate(){
        isAttackSetDamage = false;
        if (attackAnimation.isAnimationFinished(elapsedTimes)){
            timeState = MAX_TIME_STATE;
        }
        else if (timeState == 0){
            posAttack.set(posAttack.x, (80 + 180 * rand.nextInt(3)));
            System.out.println(rand.nextInt(3));
        }

        else if (attackAnimation.getKeyFrameIndex(elapsedTimes) > 20 && attackAnimation.getKeyFrameIndex(elapsedTimes) < 35){
            isAttackSetDamage = true;
        }
    }

    private void DieUpdate(){
        if (dieAnimation.isAnimationFinished(elapsedTimes)){
            timeState = 0;
            state = EnumState.Reborn;
        }
    }

    private void RebornUpdate(){
        if (rebornAnimation.isAnimationFinished(elapsedTimes)){
            timeState = MAX_TIME_STATE;
        }
    }

    public void update(float dt, Vector3 posCamera){
        elapsedTimes += dt;
        timeState += dt;
        position.set(posCamera.x + 400, posCamera.y - 100);
        //System.out.println(state);
       // System.out.print(" - ");
       // System.out.print(timeState);
       // System.out.println();
        ChangeState();
        if (state == EnumState.Attack){
            AttackUpdate();
        }
        if (state == EnumState.Die){
            DieUpdate();
        }
        if (state == EnumState.Reborn){
            RebornUpdate();
        }
    }



    private void AttackRender(SpriteBatch sb, Vector3 posCamera){
        for (int i = 0; i < 8; i++){
            sb.draw(attackAnimation.getKeyFrame(elapsedTimes, true), posCamera.x - SonicRGame.WIDTH / 2 + 150 * i, posAttack.y);
        }
    }

    public void render(SpriteBatch sb, Vector3 posCamera){
        if (state != EnumState.Die && state != EnumState.Reborn){
            sb.draw(stayTexture, position.x, position.y);
        }
        if (state == EnumState.Reborn){
            sb.draw(rebornAnimation.getKeyFrame(elapsedTimes, true), position.x, position.y);
        }
        if (state == EnumState.Attack){
            sb.draw(stayTexture, position.x, position.y);
            AttackRender(sb, posCamera);
        }
        if (state == EnumState.Die)
            sb.draw(dieAnimation.getKeyFrame(elapsedTimes, true), position.x, position.y);
    }

    public int getHp() {
        return hp;
    }

    public Vector2 getPosition() {
        return position;
    }

    public static int getMaxHp() {
        return MAX_HP;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isStateBullet(){
        if (state == EnumState.Bullet){
            return true;
        }
        return false;
    }

    public Vector2 getPosAttack() {
        return posAttack;
    }

    public int getNumberAttackPlatform(){
        if (posAttack.y == 80){
            return 1;
        }
        if (posAttack.y == 260){
            return 2;
        }
        if (posAttack.y == 440){
            return 3;
        }
        return 0;
    }

    public boolean isAttackSetDamage() {
        return isAttackSetDamage;
    }

    public void setIsAttackSetDamage(boolean isAttackSetDamage) {
        this.isAttackSetDamage = isAttackSetDamage;
    }

    public void setIsDie(boolean isDie) {
        this.isDie = isDie;
    }

    public void setIsReborn(boolean isReborn) {
        this.isReborn = isReborn;
    }
}

