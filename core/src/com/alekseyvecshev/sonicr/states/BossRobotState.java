package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.Bosses.Robot.BossRobot;
import com.alekseyvecshev.sonicr.Bosses.Robot.Bullet;
import com.alekseyvecshev.sonicr.Bosses.SonicHero;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.Platform;
import com.alekseyvecshev.sonicr.states.GameStateManager;
import com.alekseyvecshev.sonicr.states.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 24.04.2016.
 */
public class BossRobotState extends State implements GestureDetector.GestureListener {
    private Array<Platform> platforms;
    BossRobot robot;
    SonicHero sonic;
    Queue<Bullet> bullets;
    GestureDetector gestureDetector;

    public BossRobotState(GameStateManager gsm) {
        super(gsm);
        robot = new BossRobot();
        sonic = new SonicHero(100, 200);
        bullets = new Queue<Bullet>();
        platforms = new Array<Platform>();

        for (int i = 0; i < Platform.getPlatformCount(); i++) {
            platforms.add(new Platform(i * Platform.getSizePlatformX()));
        }
        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);

        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }


    @Override
    protected void handleInput() {

    }

    void updatePlatform(){
        for (Platform platform : platforms) {
            if (camera.position.x - camera.viewportWidth > platform.getPosCentralPlatform().x) {
                platform.reposition(platform.getPosBottomPlatform().x + (Platform.getSizePlatformX() * Platform.getPlatformCount()));
            }
        }
    }

    private void createBullet(){
        for (int i = 0; i < Bullet.getBulletQuantity(); i++){
            bullets.addLast(new Bullet(i * 300, (int) sonic.getPosition().x));
        }
    }
    private void updateBullet(float dt){
        if (bullets.size > 0){
            if ((sonic.getPosition().x) > (+SonicRGame.HEIGHT + bullets.first().getPosition().x)) {
                bullets.removeFirst();
            }
            for (Bullet bullet : bullets){
                bullet.update(dt);
                //System.out.println(bullet.getPosition().y);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        sonic.update(dt);
        camera.position.x = sonic.getPosition().x + 300;
        updatePlatform();
        robot.update(dt, camera.position, bullets.size);
        if (robot.IsStateBullet() && (bullets.size == 0)){

            createBullet();
        }
        updateBullet(dt);
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        for (Platform platform : platforms) {
            sb.draw(platform.getBottomPlatform(), platform.getPosBottomPlatform().x, platform.getPosBottomPlatform().y);
            sb.draw(platform.getCentralPlatform(), platform.getPosCentralPlatform().x, platform.getPosCentralPlatform().y);
            sb.draw(platform.getTopPlatform(), platform.getPosTopPlatform().x, platform.getPosTopPlatform().y);
        }
        if (robot.IsStateBullet()) {
            for (Bullet bullet : bullets) {
                sb.draw(bullet.getMoveAnimation().getKeyFrame(robot.getElapsedTime(), true), bullet.getPosition().x, bullet.getPosition().y);
            }
        }
        robot.render(sb);
        sonic.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (sonic.getLevelSpinDash() >= sonic.getMaxLevelSpindash()) {
            sonic.setTimeSpinDash(0.75);
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if ((velocityY < 0) && (sonic.getPositionPlatform() < 3)) {
            sonic.setPositionPlatform(sonic.getPositionPlatform() + 1);
        }
        if ((velocityY > 0) && (sonic.getPositionPlatform() > 0)) {
            sonic.setPositionPlatform(sonic.getPositionPlatform() - 1);
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
