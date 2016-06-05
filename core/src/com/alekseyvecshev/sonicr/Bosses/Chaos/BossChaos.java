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
    private static final float MAX_TIMESTATE = 5;

    Texture stayTexture;
    TextureAtlas rebornTexture;
    Animation rebornAnimation;

    TextureAtlas attackTexture;
    Animation attackAnimation;

    Vector2 position;
    Vector2 posAttack;
    Rectangle collision;
    private int hp = MAX_HP;
    private Random rand;
    float elapsedTimes = 0;
    HelpersTool helpersTool;
    Vector3 posPlatformY;

    private EnumState state;
    private enum EnumState{Stay, Attack, Bullet, Reborn};
    private final static float MAX_TIME_STATE = 7;
    private float timeState = 0;

    public BossChaos(){
        stayTexture = new Texture("BossStage//BossChaos//stay.png");
        position = new Vector2();

        attackTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//attackAnimation.txt"));
        attackAnimation = new Animation(1/15f, attackTexture.getRegions());

        rebornTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//rebornAnimation.txt"));
        rebornAnimation = new Animation(1/2f, rebornTexture.getRegions());

        posPlatformY = new Vector3(80, 260, 440);

        state = EnumState.Stay;

    }

    public void update(float dt, Vector3 posCamera){
        elapsedTimes += dt;
        position.set(posCamera.x, posCamera.y);
        if (timeState >= MAX_TIMESTATE){
            timeState = 0;
        }

    }

    private void AttackRender(SpriteBatch sb, Vector3 posCamera){
        for (int i = 0; i < 8; i++){
            sb.draw(attackAnimation.getKeyFrame(elapsedTimes, true), posCamera.x - SonicRGame.WIDTH / 2 + 150 * i, posPlatformY.y);
        }
    }

    public void render(SpriteBatch sb, Vector3 posCamera){
        if (state == EnumState.Stay){
            sb.draw(stayTexture, position.x, position.y);
        }
        if (state == EnumState.Reborn){
            sb.draw(rebornAnimation.getKeyFrame(elapsedTimes, true), position.x, position.y);
        }
        if (state == EnumState.Attack){
            sb.draw(stayTexture, position.x, position.y);
            AttackRender(sb, posCamera);
        }
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
}
