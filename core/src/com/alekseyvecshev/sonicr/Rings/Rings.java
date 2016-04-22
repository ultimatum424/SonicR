package com.alekseyvecshev.sonicr.Rings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 22.04.2016.
 */
public class Rings {
    private static final int COUNT_RING = 10;
    private Queue<Array> array;
    private Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public Rings(){
        texture = new Texture("gameScr\\Ring\\oneRing.png");
    }
}
