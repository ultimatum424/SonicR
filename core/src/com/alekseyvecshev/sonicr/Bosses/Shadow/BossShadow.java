package com.alekseyvecshev.sonicr.Bosses.Shadow;

import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 15.05.2016.
 */
public class BossShadow {
    private static final int SPEED = 4;
    private static final int SPEED_BOOST = 6;
    private static final int CHANGE_DT = 100;
    private static final int MAX_HP = 50;
    private EnumState state;
    private enum EnumState{Die, Run, Attack, Teleport, SpinDash, GetDamage};
    private float timeState;
    private boolean isTeleport;
    private float elapsedTime = 0;
    TextureAtlas moveTexture;
    Animation moveAnimation;
    TextureAtlas teleportTexture;
    Animation teleportAnimation;
    TextureAtlas attackTexture;
    Animation attackAnimation;
    Vector2 position;
    Rectangle collision;
    private int hp = MAX_HP;
    HelpersTool helpersTool;
    public BossShadow(){

        moveTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossShadow//run.txt"));
        moveAnimation = new Animation(1/25f, moveTexture.getRegions());

        teleportTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossShadow//teleport.txt"));
        teleportAnimation = new Animation(1/15f, teleportTexture.getRegions());

        attackTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossShadow//attack.txt"));
        attackAnimation = new Animation(1/15f, attackTexture.getRegions());

        position = new Vector2(600, 300);
        collision = new Rectangle();
        helpersTool = new HelpersTool();
        state = EnumState.Run;
        timeState = 0;
    }
    private void updateRun(float dt, Vector3 posCamera){
        position.set(posCamera.x , position.y);
        timeState += dt;
    }

    public void update(float dt, Vector3 posCamera){
        elapsedTime += dt;
        collision.set(helpersTool.setCollision(moveAnimation, elapsedTime, position));
        updateRun(dt, posCamera);

    }
    public void render(SpriteBatch sb){
        if (state == EnumState.Run){
            sb.draw(moveAnimation.getKeyFrame(elapsedTime, true), position.x, position.y);
        }
        if (state == EnumState.Teleport){
            sb.draw(teleportAnimation.getKeyFrame(elapsedTime, true), position.x, position.y);
        }
        if (state == EnumState.Attack){
            sb.draw(attackAnimation.getKeyFrame(elapsedTime, true), position.x, position.y);
        }
    }

    public static int getMaxHp() {
        return MAX_HP;
    }

    public int getHp() {
        return hp;
    }

    public void dispose(){
        moveTexture.dispose();
        attackTexture.dispose();
        teleportTexture.dispose();
    }
}
