package com.alekseyvecshev.sonicr.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Алексей on 15.05.2016.
 */
public class ArrayPlatforms {

    private Array<Platform> platforms;

    public ArrayPlatforms(){
        platforms = new Array<Platform>();

        for (int i = 0; i < Platform.getPlatformCount(); i++) {
            platforms.add(new Platform(i * Platform.getSizePlatformX()));
        }
    }
    public void update(float x, float viewportWidth){
        for (Platform platform : platforms) {
            if (x - viewportWidth > platform.getPosCentralPlatform().x) {
                platform.reposition(platform.getPosBottomPlatform().x + (Platform.getSizePlatformX() * Platform.getPlatformCount()));
            }
        }
    }
    public void render(SpriteBatch sb){
        for (Platform platform : platforms) {
            sb.draw(platform.getBottomPlatform(), platform.getPosBottomPlatform().x, platform.getPosBottomPlatform().y);
            sb.draw(platform.getCentralPlatform(), platform.getPosCentralPlatform().x, platform.getPosCentralPlatform().y);
            sb.draw(platform.getTopPlatform(), platform.getPosTopPlatform().x, platform.getPosTopPlatform().y);
        }
    }
}
