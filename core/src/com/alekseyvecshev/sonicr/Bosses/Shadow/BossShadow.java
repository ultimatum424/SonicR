package com.alekseyvecshev.sonicr.Bosses.Shadow;

import com.alekseyvecshev.sonicr.Sprites.Sonic;
import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by Алексей on 15.05.2016.
 */
public class BossShadow {
    private static final int SPEED = 4;
    private static final int SPEED_BOOST = 6;
    private static final int CHANGE_DT = 100;
    private static final int MAX_HP = 50;
    private static final int MAX_TIME_STATE = 5;

    private EnumState state;
    //private enum EnumState{Die, Run, Attack, Teleport, SpinDash, GetDamage};
    private enum EnumState{Run, Attack, Teleport, SpinDash};
    private Array<Float> arrayElapsedTimes;
    private float timeState;
    private boolean isTeleport;
    private boolean isAttack;
    private float elapsedTime = 0;

    TextureAtlas moveTexture;
    Animation moveAnimation;
    TextureAtlas teleportTexture;
    Animation teleportAnimation;
    TextureAtlas attackTexture;
    Animation attackAnimation;
    TextureAtlas spinDashTexture;
    Animation spinDashAnimation;

    Vector2 position;
    Rectangle collision;
    private int hp = MAX_HP;
    private Random rand;
    HelpersTool helpersTool;
    private boolean isHitRobot = false;
    private float timeSpinDash = 0;
    private final static float MAX_TIME_SPINDASH = (float) 0.5;
    public BossShadow(){

        arrayElapsedTimes = new Array();
        arrayElapsedTimes.addAll(elapsedTime, elapsedTime, elapsedTime, elapsedTime);
        moveTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossShadow//run.txt"));
        moveAnimation = new Animation(1/25f, moveTexture.getRegions());

        teleportTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossShadow//teleport.txt"));
        teleportAnimation = new Animation(1/15f, teleportTexture.getRegions());

        attackTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossShadow//attack.txt"));
        attackAnimation = new Animation(1/15f, attackTexture.getRegions());

        spinDashTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossShadow//spinDash.txt"));
        spinDashAnimation = new Animation(1/15f, spinDashTexture.getRegions());

        rand = new Random();
        position = new Vector2(700, 300);
        collision = new Rectangle();
        helpersTool = new HelpersTool();
        state = EnumState.Run;
        timeState = 0;
    }
    private void updateRun(float dt){
        if (isHitRobot || timeSpinDash > 0) {
            timeSpinDash += dt;
            state = EnumState.SpinDash;
            arrayElapsedTimes.set(EnumState.SpinDash.ordinal(), arrayElapsedTimes.get(EnumState.SpinDash.ordinal()) + dt);
            System.out.println(timeSpinDash);
        }
        if (!isHitRobot && (timeSpinDash >= MAX_TIME_SPINDASH || timeSpinDash == 0)){
            timeSpinDash = 0;
            state = EnumState.Run;
            arrayElapsedTimes.set(EnumState.Run.ordinal(), arrayElapsedTimes.get(EnumState.Run.ordinal()) + dt);
        }
    }

    private void updateAttack(float dt, boolean isHit) {
            state = EnumState.Attack;
            arrayElapsedTimes.set(EnumState.Attack.ordinal(), arrayElapsedTimes.get(EnumState.Attack.ordinal())+ dt);
            if (attackAnimation.isAnimationFinished(arrayElapsedTimes.get(EnumState.Attack.ordinal()))) {
                state = EnumState.Run;
                arrayElapsedTimes.set(EnumState.Attack.ordinal(), (float) 0);
        }
    }

    private void updateTeleport(float dt, Vector3 posCamera){
        arrayElapsedTimes.set(EnumState.Teleport.ordinal(), arrayElapsedTimes.get(EnumState.Teleport.ordinal())+ dt);
        if (teleportAnimation.isAnimationFinished(arrayElapsedTimes.get(EnumState.Teleport.ordinal()))){
            isTeleport = false;
            state = EnumState.Run;
            arrayElapsedTimes.set(EnumState.Teleport.ordinal(), (float) 0);
        }
        if (teleportAnimation.getKeyFrameIndex(arrayElapsedTimes.get(EnumState.Teleport.ordinal())) == 6){
            position.set(posCamera.x - (rand.nextInt(3) * 230), (130 + rand.nextInt(3) * 180));
        }
    }

    private void updateSate(float dt, Vector2 posSonic) {
        timeState += dt;
        if ((timeState >= MAX_TIME_STATE))
        {
            state = EnumState.Teleport;
            isTeleport = true;
            isAttack = true;
            timeState = 0;
            arrayElapsedTimes.set(0, (float) 0);
            arrayElapsedTimes.set(1, (float) 0);
            arrayElapsedTimes.set(2, (float) 0);
        }
    }
    public void update(float dt, Vector3 posCamera, Vector2 posSonic, boolean isHit, boolean isHitRobot){
       // System.out.println(state);
        collision.set(helpersTool.setCollision(moveAnimation, elapsedTime, position));
        position.add(dt * CHANGE_DT * SPEED, 0);
        updateSate(dt, posSonic);
        if (!isHit && state == EnumState.Attack){
            state = EnumState.Run;
            arrayElapsedTimes.set(EnumState.Attack.ordinal(), (float) 0);
        }
        if (isHit) {
            updateAttack(dt, isHit);
        }
        if (state == EnumState.Teleport){
            updateTeleport(dt, posCamera);
        }
        if (state == EnumState.Run || state == EnumState.SpinDash) {
            updateRun(dt);
        }


    }
    public void render(SpriteBatch sb){
        if (state == EnumState.Run){
            sb.draw(moveAnimation.getKeyFrame(arrayElapsedTimes.get(EnumState.Run.ordinal()), true), position.x, position.y);
        }
        else if (state == EnumState.Teleport){
            sb.draw(teleportAnimation.getKeyFrame(arrayElapsedTimes.get(EnumState.Teleport.ordinal()), true), position.x, position.y);
        }
        else if (state == EnumState.Attack){
            sb.draw(spinDashAnimation.getKeyFrame(arrayElapsedTimes.get(EnumState.Attack.ordinal()), true), position.x, position.y);
        }
        else if (state == EnumState.SpinDash) {
            sb.draw(attackAnimation.getKeyFrame(arrayElapsedTimes.get(EnumState.SpinDash.ordinal()), true), position.x, position.y);
        }
    }

    public static int getMaxHp() {
        return MAX_HP;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public void setIsHitRobot(boolean isHitRobot) {
        this.isHitRobot = isHitRobot;
    }

    public void dispose(){
        moveTexture.dispose();
        attackTexture.dispose();
        teleportTexture.dispose();
    }
}
