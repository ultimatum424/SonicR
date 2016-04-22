package com.alekseyvecshev.sonicr.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by Алексей on 12.04.2016.
 */
public class Platform {

    private static final int SIZE_PLATFORM_X = 165;
    private static final int PLATFORM_COUNT = 20;

    private Texture centralPlatform, topPlatform, bottomPlatform;
    private Vector2 posCentralPlatform, posTopPlatform, posBottomPlatform;
    private Vector3 posPlatformY;

    public Texture getCentralPlatform() {
        return centralPlatform;
    }

    public Texture getTopPlatform() {
        return topPlatform;
    }

    public static int getSizePlatformX() {
        return SIZE_PLATFORM_X;
    }

    public static int getPlatformCount() {
        return PLATFORM_COUNT;
    }

    public Texture getBottomPlatform() {
        return bottomPlatform;
    }

    public Vector2 getPosCentralPlatform() {
        return posCentralPlatform;
    }

    public Vector2 getPosTopPlatform() {
        return posTopPlatform;
    }

    public Vector2 getPosBottomPlatform() {
        return posBottomPlatform;
    }

    public Platform(float x){
        posPlatformY = new Vector3(80, 260, 440);

        topPlatform = new Texture("gameScr\\platform2.png");
        centralPlatform = new Texture("gameScr\\platform2.png");
        bottomPlatform = new Texture("gameScr\\platform2.png");

        posTopPlatform = new Vector2(x, posPlatformY.z);
        posCentralPlatform = new Vector2(x, posPlatformY.y);
        posBottomPlatform = new Vector2(x, posPlatformY.x);
    }
    public void reposition(float x){
        posTopPlatform.set(x, posPlatformY.z);
        posCentralPlatform.set(x, posPlatformY.y);
        posBottomPlatform.set(x, posPlatformY.x);
    }

}
