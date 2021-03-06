package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.Bosses.Interface;
import com.alekseyvecshev.sonicr.Bosses.Robot.ArrayBullets;
import com.alekseyvecshev.sonicr.Bosses.Robot.BossRobot;
import com.alekseyvecshev.sonicr.Bosses.Robot.FireBk;
import com.alekseyvecshev.sonicr.Bosses.SonicHero;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.ArrayPlatforms;
import com.alekseyvecshev.sonicr.Sprites.EndLevel;
import com.alekseyvecshev.sonicr.Tool.Button;
import com.alekseyvecshev.sonicr.Tool.SoundMusic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Алексей on 24.04.2016.
 */
public class BossRobotState extends State implements GestureDetector.GestureListener {
    Rectangle resultCollision;
    ArrayPlatforms arrayPlatforms;
    BossRobot robot;
    SonicHero sonic;
    ArrayBullets arrayBullets;
    Interface anInterface;
    EndLevel endLevel;
    GestureDetector gestureDetector;
    Texture bk;
    FireBk fireBk;
    private Button buttons;
    SoundMusic soundMusic;
    private Sound spinDashSound;
    private Sound fireSound;

    public BossRobotState(GameStateManager gsm) {
        super(gsm);
        robot = new BossRobot();
        sonic = new SonicHero(200, 120);
        spinDashSound =  Gdx.audio.newSound(Gdx.files.internal("Sound\\Music\\effects\\spindash.wav"));
        fireSound = Gdx.audio.newSound(Gdx.files.internal("Sound\\Music\\effects\\Robot\\fire.wav"));
        arrayBullets = new ArrayBullets();
        resultCollision = new Rectangle();
        anInterface = new Interface(sonic.getMaxHp(), robot.getMaxHp());
        endLevel = new EndLevel();
        fireBk = new FireBk();
        soundMusic = new SoundMusic();
        buttons = new Button();

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
                    sonic.setHp(sonic.getHp() - 3);
                    arrayBullets.getBullets().get(i).setIsDie(true);
                    fireSound.play();
                }
                else {
                    arrayBullets.getBullets().get(i).setIsDie(true);
                    fireSound.play();
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

    private void EndLevelSet(){
        Preferences prefs2 = Gdx.app.getPreferences("LevelOpen");
        if (!endLevel.isSonicDie()) {
            if (prefs2.getInteger("level") < 2){
                prefs2.putInteger("level", 2);
            }
        }
        prefs2.flush();
        gsm.set(new SelectLevelState(gsm));
        soundMusic.StopFireOst();
    }

    @Override
    public void update(float dt) {
        handleInput();
        soundMusic.PlayFireOst();
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

        boolean isEnd = endLevel.CheckEndLevel(dt, sonic.getHp(), robot.getHp());
        if (isEnd){
            EndLevelSet();
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

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(bk, camera.position.x - SonicRGame.WIDTH / 2, 0);
        arrayPlatforms.render(sb);
        renderGame(sb);
        fireBk.render(sb, camera.position);
        buttons.render(sb, camera.position);
        endLevel.renderEndGame(sb, camera.position);
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
        if (x < 313){
            if (buttons.getMatchDown().contains(x, y)){
                if (sonic.getPositionPlatform() > 0) {
                    sonic.setPositionPlatform(sonic.getPositionPlatform() - 1);
                }
            }
            if (buttons.getMatchUp().contains(x, y)){
                if  (sonic.getPositionPlatform() < 3) {
                    sonic.setPositionPlatform(sonic.getPositionPlatform() + 1);
                }
            }
            if (buttons.getMatchSp().contains(x, y)){
                if (sonic.getLevelSpinDash() >= sonic.getMaxLevelSpindash()) {
                    sonic.setTimeSpinDash(0.75);
                    spinDashSound.play();
                }
            }
        }
        else {
            if (sonic.getLevelSpinDash() >= sonic.getMaxLevelSpindash()) {
                sonic.setTimeSpinDash(0.75);
                spinDashSound.play();
            }
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
