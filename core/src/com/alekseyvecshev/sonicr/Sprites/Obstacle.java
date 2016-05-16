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
    private Array<Sprite> sprites;
    private Texture obstacle;
    private Vector2 position;
    private TextureAtlas textureAtlas;
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

    public Array<Sprite> getSprites() {
        return sprites;
    }

    public Sprite getRandSprite() {
        return randSprite;
    }

    public Obstacle(int i) {
        Preferences prefs = Gdx.app.getPreferences("Level");
        if (prefs.getInteger("number") == 1) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Enemy\\obstacles.txt"));
        }
        sprites = textureAtlas.createSprites();
        rand = new Random();
        randSprite = sprites.random();
        position = new Vector2((i + 150 + randSprite.getRegionWidth()), (120 + rand.nextInt(3) * 180));
        collision = new Rectangle(position.x, position.y, randSprite.getWidth(), randSprite.getHeight());
    }

    public void update() {


    }

    public void dispose() {
        textureAtlas.dispose();
    }

    public void reposition(float x) {
        position.set(x, position.y);
        collision.setPosition(position);
    }
}

