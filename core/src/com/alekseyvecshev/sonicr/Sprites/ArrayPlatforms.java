package com.alekseyvecshev.sonicr.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Алексей on 15.05.2016.
 */
public class ArrayPlatforms {

    private Array<Platform> platforms;
    private TextureAtlas textureAtlas;
    private Animation animationPlatform;
    private float elapsedTime = 0;

    public ArrayPlatforms(){
        platforms = new Array<Platform>();
        Preferences prefs = Gdx.app.getPreferences("Level");
        if (prefs.getInteger("number") == 1) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Platform\\1.txt"));
        }
        if (prefs.getInteger("number") == 2) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Platform\\2.txt"));
        }
        if (prefs.getInteger("number") == 3) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Platform\\3.txt"));
        }
        animationPlatform = new Animation(1/16f, textureAtlas.getRegions());
        for (int i = 0; i < Platform.getPlatformCount(); i++) {
            platforms.add(new Platform(i * textureAtlas.getRegions().get(0).getRegionWidth()));
        }
    }
    public void update(float x, float viewportWidth, float dt){
        elapsedTime += dt;
        for (Platform platform : platforms) {
            if (x - viewportWidth > platform.getPosCentralPlatform().x) {
                platform.reposition(platform.getPosBottomPlatform().x + (textureAtlas.getRegions().get(0).getRegionWidth() * Platform.getPlatformCount()));
            }
        }
    }
    public void render(SpriteBatch sb){
        for (Platform platform : platforms) {
            sb.draw(animationPlatform.getKeyFrame(elapsedTime, true), platform.getPosBottomPlatform().x, platform.getPosBottomPlatform().y);
            sb.draw(animationPlatform.getKeyFrame(elapsedTime, true), platform.getPosCentralPlatform().x, platform.getPosCentralPlatform().y);
            sb.draw(animationPlatform.getKeyFrame(elapsedTime, true), platform.getPosTopPlatform().x, platform.getPosTopPlatform().y);
        }
    }
}
