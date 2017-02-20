package com.alekseyvecshev.sonicr.Heroes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Алексей on 22.04.2016.
 */
public class Sonic {
    //String sonic;
    // анимация бега
    public String strSonic;
    public String textureAtlas;
    private String animation;

    // анимация спиндеша
    public String textureAtlasSpinDash;
    private String animationSpinDash;

    public String getSonic() {
        return strSonic;
    }

    public String getTextureAtlas() {
        return textureAtlas;
    }

    public String getTextureAtlasSpinDash() {
        return textureAtlasSpinDash;
    }

    public String getAnimationSpinDash() {
        return animationSpinDash;
    }

    public String getAnimation() {
        return animation;
    }

    public void Sonic(){
        strSonic = new String("gameScr\\Sonic\\sonicX2.png");
        textureAtlas = new String("gameScr\\Sonic\\runSonic.txt");
        textureAtlasSpinDash = new String("gameScr\\Sonic\\SpinDash.txt");

        //animation = new String(1/15f, textureAtlas.getRegions());
        //animationSpinDash = new String(1/15f, textureAtlasSpinDash.getRegions());
    }

    public void dispose(){
       // sonic.dispose();
        //textureAtlas.dispose();
        //textureAtlasSpinDash.dispose();
    }
}
