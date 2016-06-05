package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.Bosses.Chaos.ArrayBullet;
import com.alekseyvecshev.sonicr.Bosses.Chaos.BossChaos;
import com.alekseyvecshev.sonicr.Bosses.Interface;
import com.alekseyvecshev.sonicr.Bosses.SonicHero;
import com.alekseyvecshev.sonicr.Heroes.Sonic;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.ArrayPlatforms;
import com.alekseyvecshev.sonicr.Sprites.EndLevel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Алексей on 04.06.2016.
 */
public class BossChaosState extends State implements GestureDetector.GestureListener {

    BossChaos chaos;
    SonicHero sonic;
    Interface anInterface;
    EndLevel endLevel;
    Rectangle resultCollision;
    ArrayPlatforms arrayPlatforms;
    ArrayBullet arrayBullet;
    Texture bg;

    public BossChaosState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("BossStage//BossChaos//bg.png");
        sonic = new SonicHero(200, 120);
        GestureDetector gestureDetector;
        chaos = new BossChaos();
        arrayPlatforms = new ArrayPlatforms();
        resultCollision = new Rectangle();
        arrayBullet = new ArrayBullet();
        endLevel = new EndLevel();
        anInterface = new Interface(sonic.getMaxHp(), chaos.getMaxHp());


        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);

        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        camera.position.x = sonic.getPosition().x + 300;
        sonic.update(dt);
        chaos.update(dt, camera.position);
        arrayPlatforms.update(camera.position.x, camera.viewportWidth, dt);

        if (chaos.isStateBullet() && arrayBullet.getSizeArrayBullets() == 0){
            arrayBullet.createBullet(sonic.getPosition());
        }
        arrayBullet.updateBullet(dt, sonic.getPosition());
        anInterface.update(dt, camera.position, sonic.getHp(), chaos.getHp(), sonic.getLevelSpinDash());
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(bg, camera.position.x - SonicRGame.WIDTH / 2, 0);
        arrayPlatforms.render(sb);
        chaos.render(sb, camera.position);
        sonic.render(sb);
        if (chaos.isStateBullet() || arrayBullet.getSizeArrayBullets() != 0){
            arrayBullet.render(sb);
        }

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
