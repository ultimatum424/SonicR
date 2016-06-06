package com.alekseyvecshev.sonicr.Tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 06.06.2016.
 */
public class Button {
    TextureAtlas buttonsTexture;
    Sprite buttonUp;
    Sprite buttonDown;
    Sprite buttonSp;
    

    public Button(){
        buttonsTexture = new TextureAtlas(Gdx.files.internal("gameScr\\buttons.txt"));
        buttonUp = new Sprite(buttonsTexture.createSprite("up"));
        buttonDown = new Sprite(buttonsTexture.createSprite("down"));
        buttonSp = new Sprite(buttonsTexture.createSprite("sp"));
    }

    public void update(Vector3 cameraPos){
        buttonUp.setPosition(cameraPos.x, cameraPos.y);
        buttonDown.setPosition(cameraPos.x, cameraPos.y);
        buttonSp.setPosition(cameraPos.x, cameraPos.y);
    }
    public void render(SpriteBatch sb){
        sb.draw(buttonUp, buttonUp.getX(),  buttonUp.getY());
        sb.draw(buttonSp, buttonSp.getX(),  buttonSp.getY());
        sb.draw(buttonDown, buttonDown.getX(),  buttonDown.getY());
    }
}
