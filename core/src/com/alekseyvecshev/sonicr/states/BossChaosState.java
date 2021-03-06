package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.Bosses.Chaos.ArrayBullet;
import com.alekseyvecshev.sonicr.Bosses.Chaos.BossChaos;
import com.alekseyvecshev.sonicr.Bosses.Chaos.Tails;
import com.alekseyvecshev.sonicr.Bosses.Interface;
import com.alekseyvecshev.sonicr.Bosses.SonicHero;
import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.ArrayPlatforms;
import com.alekseyvecshev.sonicr.Sprites.EndLevel;
import com.alekseyvecshev.sonicr.Tool.Button;
import com.alekseyvecshev.sonicr.Tool.SoundMusic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
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
    Tails tails;
    Texture bg;
    private Button buttons;
    SoundMusic soundMusic;
    private Sound spinDashSound;
    private Sound bulletSound;
    private Sound boomSound;

    public BossChaosState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("BossStage//BossChaos//bg.png");
        spinDashSound =  Gdx.audio.newSound(Gdx.files.internal("Sound\\Music\\effects\\spindash.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("Sound\\Music\\effects\\Chaos\\bullet.wav"));
        boomSound = Gdx.audio.newSound(Gdx.files.internal("Sound\\Music\\effects\\Chaos\\boom.wav"));
        sonic = new SonicHero(200, 120);
        tails = new Tails();
        GestureDetector gestureDetector;
        chaos = new BossChaos();
        arrayPlatforms = new ArrayPlatforms();
        resultCollision = new Rectangle();
        arrayBullet = new ArrayBullet();
        endLevel = new EndLevel();
        buttons = new Button();
        anInterface = new Interface(sonic.getMaxHp(), chaos.getMaxHp());
        soundMusic = new SoundMusic();


        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);

        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    protected void handleInput() {

    }

    private void CheckCollision() {
        for (int i = 0; i < arrayBullet.getBullets().size; i++) {
            if (Intersector.intersectRectangles(sonic.getCollision(), arrayBullet.getBullets().get(i).getCollision(), resultCollision)) {
                if (sonic.getTimeSpinDash() == 0) {
                    sonic.setHp(sonic.getHp() - 4);
                    arrayBullet.getBullets().get(i).setIsDie(true);
                    bulletSound.play();
                }
            }
        }
        if ((chaos.getPosAttack().y + 40 == sonic.getPosition().y) && chaos.isAttackSetDamage()){
            sonic.setHp(sonic.getHp() - 1);
            chaos.setIsAttackSetDamage(false);
        }
    }

    private void EndLevelSet(){
        gsm.set(new SelectLevelState(gsm));
        soundMusic.StopChaosOst();
    }

    @Override
    public void update(float dt) {
        handleInput();
        soundMusic.PlayChaosOst();
        camera.position.x = sonic.getPosition().x + 300;
        sonic.update(dt);
        chaos.update(dt, camera.position);
        CheckCollision();
        tails.update(dt, camera.position, chaos.getPosition());
        if (tails.isDamage()){
            tails.setIsDamage(false);
            chaos.setHp(chaos.getHp() - 10);
            chaos.setIsDie(true);
            boomSound.play();
        }
        arrayPlatforms.update(camera.position.x, camera.viewportWidth, dt);

        if (chaos.isStateBullet() && arrayBullet.getSizeArrayBullets() == 0){
            arrayBullet.createBullet(sonic.getPosition());
        }
        arrayBullet.updateBullet(dt, sonic.getPosition());
        anInterface.update(dt, camera.position, sonic.getHp(), chaos.getHp(), sonic.getLevelSpinDash());
        boolean isEnd = endLevel.CheckEndLevel(dt, sonic.getHp(), chaos.getHp());
        if (isEnd){
            EndLevelSet();
        }
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
        tails.render(sb);
        anInterface.render(sb);
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
        tails.setIsAttack(true);
        return true;
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
