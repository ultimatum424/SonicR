package com.alekseyvecshev.sonicr.Rings;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Алексей on 22.04.2016.
 */
public class Array {
    Vector2 position;
    Rectangle collision;

    public Array(float i, float y, Vector2 size){
        position = new Vector2(i + 10, y);
        collision = new Rectangle(position.x, position.y, size.x, size.y);
    }
}
