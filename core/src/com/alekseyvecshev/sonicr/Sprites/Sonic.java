package com.alekseyvecshev.sonicr.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 11.04.2016.
 */
public class Sonic {
    public static final int SPEED = 4;
    public static final int SPEED_BOOST = 2;
    private static final int CHANGE_DT = 100;
    public static final int MAX_LEVEL_SPINDASH = 3;
    public double timeSpinDash = 0;
    public double levelSpinDash = 0;
    public int positionPlatform = 1;
    private float elapsedTime = 0;
    private Vector3 position;
    // анимация бега
    private TextureAtlas textureAtlas;
    private Animation animation;

    // анимация спиндеша
    private TextureAtlas textureAtlasSpinDash;
    private Animation animationSpinDash;

    private TextureAtlas textureAtlasSpinDashInterface;
    private Sprite statusBar;
    private Sprite bkStatusBar;
    private Sprite iconStatusBar;

    private Texture sonic;
    private Rectangle collision;

    public Rectangle getCollision() {
        return collision;
    }

    public Sonic(int x, int y){
        position = new Vector3(x, y, 0);
        sonic = new Texture("gameScr\\Sonic\\sonicX2.png");
        collision = new Rectangle();

        textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Sonic\\runSonic.txt" ));
        //
        //
        animation = new Animation(1/15f, textureAtlas.getRegions());

        textureAtlasSpinDash = new TextureAtlas(Gdx.files.internal("gameScr\\Sonic\\SpinDash.txt"));
        animationSpinDash = new Animation(1/15f, textureAtlasSpinDash.getRegions());

        textureAtlasSpinDashInterface = new TextureAtlas(Gdx.files.internal("gameScr\\Sonic\\statusBarSpinDash.txt"));
        statusBar = new Sprite(textureAtlasSpinDashInterface.createSprite("statusBar"));
        bkStatusBar = new Sprite(textureAtlasSpinDashInterface.createSprite("BkStatusBar"));
        iconStatusBar = new Sprite(textureAtlasSpinDashInterface.createSprite("iconStatusBar"));
    }

    public Vector3 getPosition(){
        return position;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public Animation getAnimation() {
        return animation;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public Texture getSonic(){
        return sonic;
    }

    public Animation getAnimationSpinDash() {
        return animationSpinDash;
    }

    public TextureAtlas getTextureAtlasSpinDash() {
        return textureAtlasSpinDash;
    }

    public Sprite getStatusBar() {
        return statusBar;
    }

    public Sprite getBkStatusBar() {
        return bkStatusBar;
    }

    public Sprite getIconStatusBar() {
        return iconStatusBar;
    }

    public void update(float dt){
        position.add(dt * CHANGE_DT * SPEED, 0, 0);
        if (positionPlatform == 1)
            position.set(getPosition().x, 120, 0);
        if (positionPlatform == 2)
            position.set(getPosition().x, 300, 0);
        if (positionPlatform == 3)
            position.set(getPosition().x, 480, 0);
        elapsedTime += dt;
        if (timeSpinDash > 0){
            timeSpinDash = timeSpinDash - dt;
            position.add(dt * CHANGE_DT * SPEED_BOOST, 0, 0);
            levelSpinDash = 0;
        }
        if (timeSpinDash  <= 0) {
            timeSpinDash = 0;
            if (levelSpinDash < MAX_LEVEL_SPINDASH) {
                levelSpinDash += dt;
            }
            else {
                levelSpinDash = MAX_LEVEL_SPINDASH;
            }
        }
        collision.set(position.x, position.y, sonic.getWidth(), sonic.getHeight());

    }

}
