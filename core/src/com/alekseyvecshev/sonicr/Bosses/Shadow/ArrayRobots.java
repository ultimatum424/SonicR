package com.alekseyvecshev.sonicr.Bosses.Shadow;

import com.alekseyvecshev.sonicr.Bosses.Robot.Bullet;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Tool.HelpersTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 03.06.2016.
 */
public class ArrayRobots {

    Queue<Robot> robots;
    private static final int ROBOT_QUANTITY = 15;
    private static final float MAX_TIME_RESPAWN = 7;
    private TextureAtlas moveTexture;
    private TextureAtlas dieTexture;
    private Animation moveAnimation;
    private Animation dieAnimation;
    private HelpersTool helpersTool;
    private float elapsedTime = 0;
    private float timeRespawn = 0;

    public ArrayRobots(){
        robots = new Queue<Robot>();
        helpersTool = new HelpersTool();
        moveTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//moveBullet.txt"));
        dieTexture = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//dieBullet.txt"));
        moveAnimation = new Animation(1/15f, moveTexture.getRegions());
        dieAnimation = new Animation(1/12f, dieTexture.getRegions());
    }

    public void createRobot(Vector2 posSonic){
        for (int i = 0; i < ROBOT_QUANTITY; i++){
            robots.addLast(new Robot(i * 500, (int) posSonic.x,  moveTexture.getRegions().get(0).getRegionWidth()));
        }
    }

    public void updateRobot(float dt, Vector2 posSonic){
        timeRespawn += dt;
        elapsedTime += dt;
        if (robots.size > 0){
            if ((posSonic.x) > (+SonicRGame.HEIGHT + robots.first().getPosition().x)) {
                robots.removeFirst();
            }
            for (Robot robot : robots){
                robot.setCollision(helpersTool.setCollision(moveAnimation, elapsedTime, robot.getPosition()));
                robot.update(dt);
            }
        }
    }

    public void render(SpriteBatch sb){
        for (Robot robot : robots) {
            if (robot.isDie()) {
                sb.draw(dieAnimation.getKeyFrame(robot.getElapsedTimeDie(), false), robot.getPosition().x, robot.getPosition().y);
            } else {
                sb.draw(moveAnimation.getKeyFrame(elapsedTime, true), robot.getPosition().x, robot.getPosition().y);
            }
        }
    }

    public static float getMaxTimeRespawn() {
        return MAX_TIME_RESPAWN;
    }

    public int getSizeArrayRobots(){
        return robots.size;
    }

    public Queue<Robot> getRobots() {
        return robots;
    }

    public float getTimeRespawn() {
        return timeRespawn;
    }

    public void setTimeRespawn(float timeRespawn) {
        this.timeRespawn = timeRespawn;
    }
}
