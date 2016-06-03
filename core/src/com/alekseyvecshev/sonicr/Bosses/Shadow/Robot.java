package com.alekseyvecshev.sonicr.Bosses.Shadow;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Алексей on 03.06.2016.
 */
public class Robot {
    private static final int SPEED = 6;
    private static final int CHANGE_DT = 100;
    private float elapsedTimeDie = 0;

    private Vector2 position;
    private Rectangle collision;
    private Random rand;
    private boolean isDie;

    public Robot(int i, int posSonic, float size) {
        rand = new Random();
        position = new Vector2((i + 3000 + posSonic + size),
                (130 + rand.nextInt(3) * 180));
        collision = new Rectangle();
        isDie = false;
    }

    public void update(float dt){
        if (isDie()){
            elapsedTimeDie += dt;
        }
        position.add(-dt * SPEED * CHANGE_DT, 0);
        if (isDie) {
            collision.set(0,0, 0, 0);
        }
    }


    public Vector2 getPosition() {
        return position;
    }

    public void setCollision(Rectangle collision) {
        this.collision = collision;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public boolean isDie() {
        return isDie;
    }

    public void setIsDie(boolean isDie) {
        this.isDie = isDie;
    }

    public float getElapsedTimeDie() {
        return elapsedTimeDie;
    }

}
