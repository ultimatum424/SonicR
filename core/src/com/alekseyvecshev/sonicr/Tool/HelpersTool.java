package com.alekseyvecshev.sonicr.Tool;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Алексей on 27.04.2016.
 */
public class HelpersTool {

    public Rectangle setCollision(Animation animation, float elapsedTime, Vector2 position){
        TextureRegion textureRegion = animation.getKeyFrame(elapsedTime);
        Vector2 size;
        size = new Vector2(textureRegion.getRegionWidth(),textureRegion.getRegionHeight());
        Rectangle collision;
        collision = new Rectangle(position.x, position.y, size.x, size.y);
        return collision;
    }
}
