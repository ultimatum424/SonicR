package com.alekseyvecshev.sonicr.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 20.04.2016.
 */
public class Ring {
    private static final int COUNT_RING = 10;

    private Texture texture;


    private TextureAtlas textureAtlas;
    private Animation animation;

    private Vector2 position;
    private Rectangle collision;


    public Animation getAnimation() {
        return animation;
    }

    public Texture getTexture() {
        return texture;
    }

    public static int getCOUNT_RING() {
        return COUNT_RING;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public Ring(float i, float y){
        texture = new Texture("gameScr\\Ring\\oneRing.png");
        textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Ring\\ring.txt"));
        animation = new Animation(1/8f, textureAtlas.getRegions());
        position = new Vector2(i + 10, y);
        collision = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());

    }


    public void dispose(){
        texture.dispose();
        textureAtlas.dispose();
    }
}

