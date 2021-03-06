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

    Rectangle matchUp;
    Rectangle matchDown;
    Rectangle matchSp;


    public Button(){
        buttonsTexture = new TextureAtlas(Gdx.files.internal("gameScr\\button.txt"));
        buttonUp = new Sprite(buttonsTexture.createSprite("up"));
        buttonDown = new Sprite(buttonsTexture.createSprite("down"));
        buttonSp = new Sprite(buttonsTexture.createSprite("sp"));

        matchUp = new Rectangle(37 , 30, 208, 180);
        matchSp = new Rectangle(20 , 245, 276, 208);
        matchDown = new Rectangle(37 , 500, 208, 180);

    }

    public void render(SpriteBatch sb, Vector3 cameraPos){
        sb.draw(buttonUp, cameraPos.x - 603,  cameraPos.y + 150);
        sb.draw(buttonSp, cameraPos.x - 620, cameraPos.y - 93);
        sb.draw(buttonDown, cameraPos.x - 603, cameraPos.y  - 320);
    }

    public Rectangle getMatchUp() {
        return matchUp;
    }

    public Rectangle getMatchDown() {
        return matchDown;
    }

    public Rectangle getMatchSp() {
        return matchSp;
    }
}
