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
public class EndLevel {
    private TextureAtlas textureAtlas;
    private Sprite gameOver;
    private Sprite progressBar;
    private Sprite progressBarMask;
    private Sprite levelComplete;
    private boolean isComplete;
    private Texture bg;
    private float timerGameOver;
    private boolean sonicDie;


    public EndLevel(){
        sonicDie = false;
        isComplete = false;
        timerGameOver = 0;
        bg = new Texture("gameScr\\gameOver\\bg.png");
        textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\gameOver\\GameEnd.txt"));
        gameOver = textureAtlas.createSprite("gameOver");
        progressBar = textureAtlas.createSprite("progressBar");
        progressBarMask = textureAtlas.createSprite("progressBarMask");
        levelComplete = textureAtlas.createSprite("complete");
    }

    public void render(SpriteBatch sb, Vector3 centre){
        System.out.println((levelComplete.getTexture().getWidth()));
        sb.draw(bg, centre.x - (SonicRGame.WIDTH / 2), 0);
        if (!isComplete) {
            sb.draw(gameOver, centre.x - 360, centre.y - 35, gameOver.getWidth(), gameOver.getHeight());
        }
        else if (isComplete){
            sb.draw(levelComplete, centre.x - 150, centre.y - 70, levelComplete.getWidth(), levelComplete.getHeight());
        }
        sb.draw(progressBarMask, centre.x - 280, centre.y -  215, progressBarMask.getWidth(), progressBarMask.getHeight());
        sb.draw(progressBar,  centre.x - 280 + 3, centre.y -  215 + 5, (progressBar.getTexture().getWidth() * timerGameOver / 3) / 2, progressBar.getHeight());
    }

    public float getTimerGameOver() {
        return timerGameOver;
    }

    public void setTimerGameOver(float timerGameOver) {
        this.timerGameOver = timerGameOver;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean isSonicDie() {
        return sonicDie;
    }

    public void setSonicDie(boolean sonicDie) {
        this.sonicDie = sonicDie;
    }

    public boolean isComplete() { return isComplete; }
}
