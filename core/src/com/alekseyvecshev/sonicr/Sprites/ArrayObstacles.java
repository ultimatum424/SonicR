package com.alekseyvecshev.sonicr.Sprites;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 15.05.2016.
 */
public class ArrayObstacles {
    private Queue<Obstacle> obstacles;
    private Rectangle resultCollision;
    public ArrayObstacles(){
        obstacles = new Queue<Obstacle>();
        resultCollision = new Rectangle();
        for (int i = 1; i <= Obstacle.getObstaclesNumbers(); i++) {
            obstacles.addLast(new Obstacle((int) (i * 500)));
        }

    }

    public boolean update(float sonicPosX, float timeSpinDash, Rectangle sonicCollision){
        if ((sonicPosX) > (+SonicRGame.HEIGHT + obstacles.first().getPosition().x)) {
            obstacles.removeFirst();
            obstacles.addLast(new Obstacle((int) (obstacles.last().getPosition().x)));
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
}
