package com.alekseyvecshev.sonicr.Bosses.Chaos;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 05.06.2016.
 */
public class ArrayBullet {
    Queue<Bullet> bullets;
    private static final int BULLET_QUANTITY = 15;
    private TextureAtlas moveTexture;
    private TextureAtlas dieTexture;
    private Animation moveAnimation;
    private Animation dieAnimation;
    private HelpersTool helpersTool;
    private float elapsedTime = 0;

    public ArrayBullet(){
        bullets = new Queue<Bullet>();
        helpersTool = new HelpersTool();
        moveTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//moveBullet.txt"));
        dieTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossChaos//deiBullet.txt"));
        moveAnimation = new Animation(1/15f, moveTexture.getRegions());
        dieAnimation = new Animation(1/12f, dieTexture.getRegions());

    }
    public void createBullet(Vector2 posSonic){
        for (int i = 0; i < BULLET_QUANTITY; i++){
            bullets.addLast(new Bullet(i * 500, (int) posSonic.x,  moveTexture.getRegions().get(0).getRegionWidth()));
        }
    }

    public void updateBullet(float dt, Vector2 posSonic){
        elapsedTime += dt;
        if (bullets.size > 0){
            if ((posSonic.x) > (+SonicRGame.HEIGHT + bullets.first().getPosition().x)) {
                bullets.removeFirst();
            }
            for (Bullet bullet : bullets){
                bullet.setCollision(helpersTool.setCollision(moveAnimation, elapsedTime, bullet.getPosition()));
                bullet.update(dt);
            }
        }
    }
    public void render(SpriteBatch sb){
        for (Bullet bullet : bullets) {
            if (bullet.isDie()) {
                sb.draw(dieAnimation.getKeyFrame(bullet.getElapsedTimeDie(), false), bullet.getPosition().x, bullet.getPosition().y);
            } else {
                sb.draw(moveAnimation.getKeyFrame(elapsedTime, true), bullet.getPosition().x, bullet.getPosition().y);
            }
        }
    }

    public Queue<Bullet> getBullets() {
        return bullets;
    }
    public int getSizeArrayBullets(){
        return bullets.size;
    }
}
