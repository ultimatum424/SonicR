package com.alekseyvecshev.sonicr.Sprites;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Queue;

import java.util.Random;

/**
 * Created by Алексей on 15.05.2016.
 */
public class ArrayRings {
    Queue<Ring> rings;
    private Random rand;
    private float elapsedTime = 0;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private Rectangle resultCollision;
    private int ringsCount;
    public final static int RINGS_FOR_END = 15;

    public ArrayRings(){
        rings = new  Queue<Ring>();
        rand = new Random();
        resultCollision = new Rectangle();
        textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Ring\\ring.txt"));
        animation = new Animation(1/8f, textureAtlas.getRegions());
    }
    private void createRings(float sonicPos){
        if ((int) (sonicPos % 3000) < 7){
            int j = (120 + rand.nextInt(3) * 180);
            for (int i = 0; i < Ring.getCOUNT_RING(); i++){
                rings.addLast(new Ring((int) (i * 100 + sonicPos + 1000), j));
            }
        }
    }
    public void update(float dt, float sonicPosX, Rectangle sonicCollision){
        elapsedTime += dt;
        if (rings.size > 0) {
            if ((sonicPosX - SonicRGame.HEIGHT ) > (rings.first().getPosition().x)) {
                rings.removeFirst();
            }
        }
        createRings(sonicPosX);
        for (int i = 0; i < rings.size; i++){
            if (Intersector.intersectRectangles(sonicCollision, rings.get(i).getCollision(), resultCollision)) {
                rings.removeIndex(i);
                ringsCount++;
            }
        }
    }
    public void render(SpriteBatch sb){
        for (Ring ring : rings){
            sb.draw(animation.getKeyFrame(elapsedTime, true), ring.getPosition().x, ring.getPosition().y);
        }

    }

    public int getRingsCount() {
        return ringsCount;
    }

    public void setRingsCount(int ringsCount) {
        this.ringsCount = ringsCount;
    }
}
