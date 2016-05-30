package com.alekseyvecshev.sonicr.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by Алексей on 16.04.2016.
 */
public class Obstacle {
    private static final int OBSTACLES_NUMBERS = 10;
    private Vector2 position;
    private Sprite randSprite;
    private Rectangle collision;
    private Random rand;


    public static int getObstaclesNumbers() {
        return OBSTACLES_NUMBERS;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getRandSprite() {
        return randSprite;
    }

    public Obstacle(int i, Sprite sprite) {
        rand = new Random();
        randSprite = new Sprite(sprite);
        position = new Vector2((i + 150 + randSprite.getRegionWidth()), (120 + rand.nextInt(3) * 180));
        collision = new Rectangle(position.x, position.y, randSprite.getWidth(), randSprite.getHeight());
    }

    public void update() {


    }

    public void dispose() {

    }

    public void reposition(float x) {
        position.set(x, position.y);
        collision.setPosition(position);
    }
}

