package com.alekseyvecshev.sonicr.Sprites;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import java.util.Random;

/**
 * Created by Алексей on 15.05.2016.
 */
public class ArrayObstacles {
    private Queue<Obstacle> obstacles;
    private TextureAtlas textureAtlas;
    private Array<Sprite> sprites;
    private Random rand;
    private Rectangle resultCollision;
    public ArrayObstacles(){
        Preferences prefs = Gdx.app.getPreferences("Level");
        if (prefs.getInteger("number") == 1) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("gameScr\\Enemy\\obstacles.txt"));
        }
        sprites = textureAtlas.createSprites();
        obstacles = new Queue<Obstacle>();
        resultCollision = new Rectangle();
        for (int i = 1; i <= Obstacle.getObstaclesNumbers(); i++) {
            rand = new Random();
            obstacles.addLast(new Obstacle((int) (i * 500), sprites.random()));
        }

    }

    public boolean update(float sonicPosX, float timeSpinDash, Rectangle sonicCollision){
        if ((sonicPosX) > (+SonicRGame.HEIGHT + obstacles.first().getPosition().x)) {
            obstacles.removeFirst();
            rand = new Random();
            obstacles.addLast(new Obstacle((int) (obstacles.last().getPosition().x),  sprites.random()));
    }
        for (Obstacle obstacle : obstacles) {
            if (timeSpinDash == 0) {
                if (Intersector.intersectRectangles(sonicCollision, obstacle.getCollision(), resultCollision)) {
                    obstacles.clear();
                    return true;
                }
            }
        }
        return false;
    }

    public void render(SpriteBatch sb){
        for (Obstacle obstacle : obstacles) {
            sb.draw(obstacle.getRandSprite(), obstacle.getPosition().x, obstacle.getPosition().y);
        }
    }
    public void dispose()
    {
        textureAtlas.dispose();
    }
}
