package com.alekseyvecshev.sonicr.Bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 27.04.2016.
 */
public class ElementInterface {
    String name;
    Sprite sprite;
    Vector2 position;
    Vector2 realPos;
    Vector2 size;
    int MAX_INFO;

    public ElementInterface(Sprite sp, String _name, int x, int y, int info) {
        position = new Vector2(x, y);
        sprite = new Sprite(sp);
        realPos = new Vector2();
        size = new Vector2();
        name = new String(_name);
        MAX_INFO = info;

    }

    public void update(float dt, Vector3 posCamera, float info){
        realPos.x = posCamera.x + position.x;
        realPos.y = posCamera.y + position.y;
        if (info < 0){
            info = 0;
        }
        size.x = sprite.getWidth() * (info / MAX_INFO);
        size.y = sprite.getHeight();



    }

    public void render(SpriteBatch sb){
        sb.draw(sprite, realPos.x, realPos.y, size.x, size.y);
    }
    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}