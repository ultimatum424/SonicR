package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.Bosses.Interface;
import com.alekseyvecshev.sonicr.Bosses.Shadow.ArrayRobots;
import com.alekseyvecshev.sonicr.Bosses.Shadow.BossShadow;
import com.alekseyvecshev.sonicr.Bosses.SonicHero;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.ArrayPlatforms;
import com.alekseyvecshev.sonicr.Sprites.EndLevel;
import com.alekseyvecshev.sonicr.Sprites.LevelComplete;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Алексей on 15.05.2016.
 */
public class BossShadowState extends State implements GestureDetector.GestureListener  {
    Rectangle resultCollision;
    ArrayPlatforms arrayPlatforms;
    ArrayRobots arrayRobots;
    SonicHero sonic;
    BossShadow shadow;
    Interface anInterface;
    LevelComplete levelComplete;
    EndLevel endLevel;
    GestureDetector gestureDetector;
    Texture bg;


    public BossShadowState(GameStateManager gsm) {
        super(gsm);
        sonic = new SonicHero(200, 120);
        shadow = new BossShadow();
        arrayPlatforms = new ArrayPlatforms();
        arrayRobots = new ArrayRobots();
        resultCollision = new Rectangle();
        endLevel = new EndLevel();
        levelComplete = new LevelComplete();
        anInterface = new Interface(sonic.getMaxHp(), shadow.getMaxHp());
        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);
        bg = new Texture("gameScr\\bk3.png");
        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    protected void handleInput() {
    }

    private void checkCollision(){
        if (Intersector.intersectRectangles(sonic.getCollision(), shadow.getCollision(), resultCollision)){
            if (sonic.getTimeSpinDash() == 0){
                sonic.setHp(sonic.getHp() - 1);
            }
            else{
                shadow.setHp(shadow.getHp() - 1);
            }
        }
        shadow.setIsHitRobot(false);
        for (int i = 0; i < arrayRobots.getRobots().size; i++) {
            if (Intersector.intersectRectangles(sonic.getCollision(), arrayRobots.getRobots().get(i).getCollision(), resultCollision)) {
                if (sonic.getTimeSpinDash() == 0) {
                    sonic.setHp(sonic.getHp() - 1);
                    arrayRobots.getRobots().get(i).setIsDie(true);
                }
            }
            if (Intersector.intersectRectangles(shadow.getCollision(), arrayRobots.getRobots().get(i).getCollision(), resultCollision)) {
                shadow.setHp(shadow.getHp() - 1);
                arrayRobots.getRobots().get(i).setIsDie(true);
                shadow.setIsHitRobot(true);
            }
        }
    }

    private boolean CheckCollision(Rectangle firstCollision, Rectangle secondCollision) {
        if (Intersector.intersectRectangles(firstCollision, secondCollision, resultCollision)) {
            return true;
        }
        return false;
    }
    @Override
    public void update(float dt) {
        handleInput();
        sonic.update(dt);
        camera.position.x = sonic.getPosition().x + 300;
        shadow.update(dt, camera.position, sonic.getPosition(), CheckCollision(sonic.getCollision(), shadow.getCollision()), true);
        checkCollision();
        arrayPlatforms.update(camera.position.x, camera.viewportWidth, dt);

        if  (arrayRobots.getSizeArrayRobots() == 0 && arrayRobots.getTimeRespawn() >= arrayRobots.getMaxTimeRespawn()){
            arrayRobots.createRobot(sonic.getPosition());
            arrayRobots.setTimeRespawn(0);
        }
        arrayRobots.updateRobot(dt, sonic.getPosition());

        anInterface.update(dt, camera.position, sonic.getHp(), shadow.getHp(), sonic.getLevelSpinDash());
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        arrayPlatforms.render(sb);
        shadow.render(sb);
        sonic.render(sb);
        arrayRobots.render(sb);
        anInterface.render(sb);
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
