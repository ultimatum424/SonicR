package com.alekseyvecshev.sonicr.Bosses.Robot;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 04.05.2016.
 */
public class FireBk {
    TextureAtlas fireAtlas;
    Animation fireAnimation;
    float elapsedTime = 0;

    public FireBk(){
        fireAtlas = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//bkAnimation.txt"));
        fireAnimation = new Animation(1/15f, fireAtlas.getRegions());
    }
    public void update(float dt){
        elapsedTime += dt;
    }
    public void render(SpriteBatch sb, Vector3 cameraPos){
        sb.draw(fireAnimation.getKeyFrame(elapsedTime, true),cameraPos.x - SonicRGame.WIDTH / 2, -122);
        sb.draw(fireAnimation.getKeyFrame(elapsedTime, true), cameraPos.x - SonicRGame.WIDTH / 2 + 260, -122);
        sb.draw(fireAnimation.getKeyFrame(elapsedTime, true), cameraPos.x - SonicRGame.WIDTH / 2 + 520, -122);
        sb.draw(fireAnimation.getKeyFrame(elapsedTime, true), cameraPos.x - SonicRGame.WIDTH / 2 + 780, -122);
        sb.draw(fireAnimation.getKeyFrame(elapsedTime, true), cameraPos.x - SonicRGame.WIDTH / 2 + 1040, -122);
    }
}
