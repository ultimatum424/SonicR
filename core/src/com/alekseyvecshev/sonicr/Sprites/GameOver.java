package com.alekseyvecshev.sonicr.Sprites;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 18.04.2016.
 */
public class GameOver {
    private TextureAtlas textureAtlas;
    private Sprite gameOver;
    private Sprite progressBar;
    private Sprite progressBarMask;
    private Texture bg;
    float timerGameOver = 0;


    public GameOver(){
        bg = new Texture("gameScr\\gameOver\\bg.png");
        textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\gameOver\\gameover.txt"));
        gameOver = textureAtlas.createSprite("gameOver");
        progressBar = textureAtlas.createSprite("progressBar");
        progressBarMask = textureAtlas.createSprite("progressBarMask");
    }

    public void render(SpriteBatch sb, float procentBar, Vector3 centre){
        sb.draw(bg, centre.x - (SonicRGame.WIDTH / 2), 0);
        sb.draw(gameOver, centre.x - 360, centre.y - 35, gameOver.getWidth(), gameOver.getHeight());
        sb.draw(progressBarMask, centre.x - 280, centre.y -  215, progressBarMask.getWidth(), progressBarMask.getHeight());
        sb.draw(progressBar,  centre.x - 280 + 3, centre.y -  215 + 5, (progressBar.getTexture().getWidth() * procentBar), progressBar.getHeight());
    }

    public float getTimerGameOver() {
        return timerGameOver;
    }

    public void setTimerGameOver(float timerGameOver) {
        this.timerGameOver = timerGameOver;
    }
}
