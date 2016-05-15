package com.alekseyvecshev.sonicr.Bosses.Robot;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 04.05.2016.
 */
public class ArrayBullets {
    Queue<Bullet> bullets;
    private static final int BULLET_QUANTITY = 30;


    public ArrayBullets(){
        bullets = new Queue<Bullet>();

    }
    public void createBullet(Vector2 posSonic){
        for (int i = 0; i < BULLET_QUANTITY; i++){
            bullets.addLast(new Bullet(i * 500, (int) posSonic.x));
        }
    }

    public void updateBullet(float dt, Vector2 posSonic){
        if (bullets.size > 0){
            if ((posSonic.x) > (+SonicRGame.HEIGHT + bullets.first().getPosition().x)) {
                bullets.removeFirst();
            }
            for (Bullet bullet : bullets){
                bullet.update(dt);
            }
        }
    }
    public void render(SpriteBatch sb){
        for (Bullet bullet : bullets) {
            if (bullet.isDie()) {
                sb.draw(bullet.getDieAnimation().getKeyFrame(bullet.getElapsedTimeDie(), false), bullet.getPosition().x, bullet.getPosition().y);
            } else {
                sb.draw(bullet.getMoveAnimation().getKeyFrame(bullet.getElapsedTime(), true), bullet.getPosition().x, bullet.getPosition().y);
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
