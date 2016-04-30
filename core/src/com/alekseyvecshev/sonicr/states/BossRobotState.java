package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.Bosses.Interface;
import com.alekseyvecshev.sonicr.Bosses.Robot.BossRobot;
import com.alekseyvecshev.sonicr.Bosses.Robot.Bullet;
import com.alekseyvecshev.sonicr.Bosses.SonicHero;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.GameOver;
import com.alekseyvecshev.sonicr.Sprites.Platform;
import com.alekseyvecshev.sonicr.states.GameStateManager;
import com.alekseyvecshev.sonicr.states.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

/**
 * Created by Алексей on 24.04.2016.
 */
public class BossRobotState extends State implements GestureDetector.GestureListener {
    Rectangle resultCollision;
    private Array<Platform> platforms;
    BossRobot robot;
    SonicHero sonic;
    Queue<Bullet> bullets;
    Interface anInterface;
    GameOver gameOver;
    GestureDetector gestureDetector;
    Texture bk;
    TextureAtlas fireAtlas;
    Animation fireAnimation;

    public BossRobotState(GameStateManager gsm) {
        super(gsm);
        robot = new BossRobot();
        sonic = new SonicHero(200, 120);
        bullets = new Queue<Bullet>();
        platforms = new Array<Platform>();
        resultCollision = new Rectangle();
        anInterface = new Interface(sonic.getMaxHp(), robot.getMaxHp());
        gameOver = new GameOver();

        bk = new Texture("BossStage//BossRobot//bk.png");
        fireAtlas = new TextureAtlas(Gdx.files.internal("BossStage//BossRobot//bkAnimation.txt"));
        fireAnimation = new Animation(1/15f, fireAtlas.getRegions());

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
            bullets.addLast(new Bullet(i * 500, (int) sonic.getPosition().x));
        }
    }
    private void updateBullet(float dt){
        if (bullets.size > 0){
            if ((sonic.getPosition().x) > (+SonicRGame.HEIGHT + bullets.first().getPosition().x)) {
                bullets.removeFirst();
            }
            for (Bullet bullet : bullets){
                bullet.update(dt);
            }
        }
    }

    private void checkCollision(){
        for (Bullet bullet: bullets) {
            if (Intersector.intersectRectangles(sonic.getCollision(), bullet.getCollision(), resultCollision)) {
                if (sonic.getTimeSpinDash() == 0){
                    sonic.setHp(sonic.getHp() - 1);
                }
                else {
                    bullet.setIsDie(true);
                }
            }
        }
        if (Intersector.intersectRectangles(sonic.getCollision(), robot.getCollision(), resultCollision)){
            if (sonic.getTimeSpinDash() == 0) {
                sonic.setHp(sonic.getHp() - 1);
            }
            else{
                robot.setHp(robot.getHp() - 1);
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
        checkCollision();
        anInterface.update(dt, camera.position, sonic.getHp(), robot.getHp(), sonic.getLevelSpinDash());
        if (((sonic.getHp() <= 0) || (robot.getHp() <= 0)) && (gameOver.getTimerGameOver() == 0)) {
            gameOver.setTimerGameOver(3);
        }
        if (gameOver.getTimerGameOver() > 0){
            gameOver.setTimerGameOver(gameOver.getTimerGameOver() - dt);
        }
        if (gameOver.getTimerGameOver() < 0){
                gsm.set(new MenuState(gsm));
        }
        System.out.println(gameOver.getTimerGameOver());
        camera.update();
    }

    private void renderGame(SpriteBatch sb){
        for (Platform platform : platforms) {
            sb.draw(platform.getBottomPlatform(), platform.getPosBottomPlatform().x, platform.getPosBottomPlatform().y);
            sb.draw(platform.getCentralPlatform(), platform.getPosCentralPlatform().x, platform.getPosCentralPlatform().y);
            sb.draw(platform.getTopPlatform(), platform.getPosTopPlatform().x, platform.getPosTopPlatform().y);
        }
        sb.draw(fireAnimation.getKeyFrame(robot.getElapsedTime(), true), camera.position.x - SonicRGame.WIDTH / 2, -122);
        sb.draw(fireAnimation.getKeyFrame(robot.getElapsedTime(), true), camera.position.x - SonicRGame.WIDTH / 2 + 260, -122);
        sb.draw(fireAnimation.getKeyFrame(robot.getElapsedTime(), true), camera.position.x - SonicRGame.WIDTH / 2 + 520, -122);
        sb.draw(fireAnimation.getKeyFrame(robot.getElapsedTime(), true), camera.position.x - SonicRGame.WIDTH / 2 + 780, -122);
        sb.draw(fireAnimation.getKeyFrame(robot.getElapsedTime(), true), camera.position.x - SonicRGame.WIDTH / 2 + 1040, -122);
        if (robot.IsStateBullet()) {
            for (Bullet bullet : bullets) {
                if (bullet.isDie()) {
                    sb.draw(bullet.getDieAnimation().getKeyFrame(robot.getElapsedTime(), true), bullet.getPosition().x, bullet.getPosition().y);
                } else {
                    sb.draw(bullet.getMoveAnimation().getKeyFrame(robot.getElapsedTime(), true), bullet.getPosition().x, bullet.getPosition().y);
                }
            }
        }
        robot.render(sb);
        sonic.render(sb);
        anInterface.render(sb);
    }
    private void renderLoseGame(SpriteBatch sb){
        gameOver.render(sb, gameOver.getTimerGameOver() / 3, camera.position);
    }
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(bk, camera.position.x - SonicRGame.WIDTH / 2, 0);
        if ((sonic.getHp() > 0) && (robot.getHp() > 0)) {
            renderGame(sb);
        }
        else {
            renderLoseGame(sb);
        }
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
