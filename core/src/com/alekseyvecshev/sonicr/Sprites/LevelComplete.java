package com.alekseyvecshev.sonicr.Sprites;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 14.05.2016.
 */
public class LevelComplete {
    private Texture bg;
    private TextureAtlas textureAtlas;
    private Sprite level;

    float timer = 0;

    public LevelComplete(){

        bg = new Texture("gameScr\\gameOver\\bg.png");
    }
    public void update(float dt){

    }
    public void render(SpriteBatch sb, Vector3 centre){
        sb.draw(bg, centre.x - (SonicRGame.WIDTH / 2), 0);
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

}
