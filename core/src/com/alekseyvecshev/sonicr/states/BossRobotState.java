package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.Bosses.Interface;
import com.alekseyvecshev.sonicr.Bosses.Robot.ArrayBullets;
import com.alekseyvecshev.sonicr.Bosses.Robot.BossRobot;
import com.alekseyvecshev.sonicr.Bosses.Robot.FireBk;
import com.alekseyvecshev.sonicr.Bosses.SonicHero;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.ArrayPlatforms;
import com.alekseyvecshev.sonicr.Sprites.GameOver;
import com.alekseyvecshev.sonicr.Sprites.LevelComplete;
import com.alekseyvecshev.sonicr.Sprites.Platform;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Алексей on 24.04.2016.
 */
public class BossRobotState extends State implements GestureDetector.GestureListener {
    Rectangle resultCollision;
    private Array<Platform> platforms;
    ArrayPlatforms arrayPlatforms;
    BossRobot robot;
    SonicHero sonic;
    ArrayBullets arrayBullets;
    Interface anInterface;
    GameOver gameOver;
    LevelComplete levelComplete;
    GestureDetector gestureDetector;
    Texture bk;
    FireBk fireBk;

    public BossRobotState(GameStateManager gsm) {
        super(gsm);
        robot = new BossRobot();
        sonic = new SonicHero(200, 120);
        arrayBullets = new ArrayBullets();
        resultCollision = new Rectangle();
        anInterface = new Interface(sonic.getMaxHp(), robot.getMaxHp());
        gameOver = new GameOver();
        levelComplete = new LevelComplete();
        fireBk = new FireBk();

        bk = new Texture("BossStage//BossRobot//bk.png");
        arrayPlatforms = new ArrayPlatforms();

        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);

        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }


    @Override
    protected void handleInput() {
    }

    private void checkCollision(){
        for (int i = 0; i < arrayBullets.getBullets().size; i++) {
            if (Intersector.intersectRectangles(sonic.getCollision(), arrayBullets.getBullets().get(i).getCollision(), resultCollision)) {
                if (sonic.getTimeSpinDash() == 0){
                    sonic.setHp(sonic.getHp() - 1);
                    arrayBullets.getBullets().get(i).setIsDie(true);
                }
                else {
                    arrayBullets.getBullets().get(i).setIsDie(true);
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
        arrayPlatforms.update(camera.position.x, camera.viewportWidth, dt);
        robot.update(dt, camera.position, arrayBullets.getSizeArrayBullets());
        fireBk.update(dt);
        if (robot.IsStateBullet() && (arrayBullets.getSizeArrayBullets() == 0)){
            arrayBullets.createBullet(sonic.getPosition());
        }
        arrayBullets.updateBullet(dt, sonic.getPosition());

        checkCollision();

        anInterface.update(dt, camera.position, sonic.getHp(), robot.getHp(), sonic.getLevelSpinDash());

        if (((sonic.getHp() <= 0) && (robot.getHp() > 0)) && (gameOver.getTimerGameOver() == 0)) {
            gameOver.setTimerGameOver(3);
        }
        if (gameOver.getTimerGameOver() > 0){
            gameOver.setTimerGameOver(gameOver.getTimerGameOver() - dt);
        }
        if (gameOver.getTimerGameOver() < 0){
                gsm.set(new SelectLevelState(gsm));
        }
        if (((sonic.getHp() > 0) && (robot.getHp() <= 0)) && (levelComplete.getTimer() == 0)) {
            levelComplete.setTimer(3);
        }
        if (levelComplete.getTimer() > 0){
            levelComplete.setTimer(levelComplete.getTimer() - dt);
        }
        if (levelComplete.getTimer() < 0){
            gsm.set(new SelectLevelState(gsm));
        }
        camera.update();
    }

    private void renderGame(SpriteBatch sb){
        if (robot.IsStateBullet()) {
            arrayBullets.render(sb);
        }
        robot.render(sb);
        sonic.render(sb);
        anInterface.render(sb);
    }
    private void renderLoseGame(SpriteBatch sb){
        gameOver.render(sb, camera.position);
    }
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(bk, camera.position.x - SonicRGame.WIDTH / 2, 0);
        arrayPlatforms.render(sb);
        if (sonic.getHp() > 0) {
            renderGame(sb);
            fireBk.render(sb, camera.position);
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
