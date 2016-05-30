package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.ArrayObstacles;
import com.alekseyvecshev.sonicr.Sprites.ArrayPlatforms;
import com.alekseyvecshev.sonicr.Sprites.ArrayRings;
import com.alekseyvecshev.sonicr.Sprites.GameOver;
import com.alekseyvecshev.sonicr.Sprites.Sonic;
import com.alekseyvecshev.sonicr.Tool.ScoreTable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


/**
 * Created by Алексей on 11.04.2016.
 */
public class PlayState extends State implements GestureDetector.GestureListener {

    private float timeGameOver;
    private int counterScore;
    private ArrayPlatforms arrayPlatforms;
    private Sonic sonic;
    private Texture bk;
    private Texture textureScore;
    private GameOver gameOver;
    private ScoreTable scoreTable;
    private ArrayObstacles obstacles;
    private ArrayRings rings;
    private Rectangle resultCollision;
    private BitmapFont font;
    private Random rand;

    GestureDetector gestureDetector;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        sonic = new Sonic(100, 120);
        arrayPlatforms = new ArrayPlatforms();
        font = new BitmapFont();
        Preferences prefs = Gdx.app.getPreferences("Level");
        if (prefs.getInteger("number") == 1) {
            bk = new Texture("gameScr\\bk1.png");
        }
        if (prefs.getInteger("number") == 2) {
            bk = new Texture("gameScr\\bk2.png");
        }
        if (prefs.getInteger("number") == 3) {
            bk = new Texture("gameScr\\bk3.png");
        }
        textureScore = new Texture("gameScr\\Ring\\r_ring.png");
        gameOver = new GameOver();

        scoreTable = new ScoreTable();
        rand = new Random();


        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);
        obstacles = new ArrayObstacles();
        rings = new ArrayRings();
        resultCollision = new Rectangle();

        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    protected void handleInput() {
    }

    private void updateObstacles(){
        if (obstacles.update(sonic.getPosition().x, (float) sonic.timeSpinDash, sonic.getCollision())) {
            gameOver.setTimerGameOver(3);
            gameOver.setSonicDie(true);
            scoreTable.addBesetScore(counterScore);
        }
    }
    private void isLevelEnd()
    {
        if (rings.getRingsCount() >= rings.RINGS_FOR_END)
        {
            gameOver.setTimerGameOver(3);
            gameOver.setIsComplete(true);
            scoreTable.addBesetScore(counterScore);
        }
    }
    @Override
    public void update(float dt) {
        handleInput();
        if ((gameOver.isSonicDie()) || ( gameOver.isComplete())) {
            gameOver.setTimerGameOver(gameOver.getTimerGameOver() - dt);
            if (gameOver.getTimerGameOver() < 0) {
                gsm.set(new SelectLevelState(gsm));
            }
        }
        else {
            sonic.update(dt);
            counterScore++;
            camera.position.x = sonic.getPosition().x;
            arrayPlatforms.update(camera.position.x, camera.viewportWidth, dt);
            updateObstacles();
            rings.update(dt, sonic.getPosition().x, sonic.getCollision());
            isLevelEnd();
        }

        camera.update();
    }

    private void renderGame(SpriteBatch sb) {

        sb.draw(bk, camera.position.x - (camera.viewportWidth / 2), 0);
        arrayPlatforms.render(sb);
        obstacles.render(sb);
        rings.render(sb);
        if (sonic.timeSpinDash == 0) {
            sb.draw(sonic.getAnimation().getKeyFrame(sonic.getElapsedTime(), true), sonic.getPosition().x, sonic.getPosition().y);
        } else {
            sb.draw(sonic.getAnimationSpinDash().getKeyFrame(sonic.getElapsedTime(), true), sonic.getPosition().x, sonic.getPosition().y);
        }
        sb.draw(textureScore, camera.position.x + 500, camera.position.y + 280);
        font.draw(sb, "" + rings.getRingsCount(), camera.position.x + 550, camera.position.y + 320);
        sb.draw(sonic.getBkStatusBar(), camera.position.x + 250, camera.position.y + 300);
        sb.draw(sonic.getStatusBar(), camera.position.x + 251, camera.position.y + 301,
                (float) (sonic.getStatusBar().getWidth() * (sonic.levelSpinDash / sonic.MAX_LEVEL_SPINDASH)), sonic.getStatusBar().getHeight());
        sb.draw(sonic.getIconStatusBar(), camera.position.x + 195, camera.position.y + 287);
    }

    private void renderLoseGame(SpriteBatch sb) {
        gameOver.render(sb, camera.position);
        int temp = scoreTable.getBestScore();
        font.draw(sb, "Best score: " + temp, camera.position.x - 200, camera.position.y - 196);
        font.draw(sb, "Score: " + counterScore, camera.position.x + 100, camera.position.y - 196);
    }
    private void renderCompleted(SpriteBatch sb){
        gameOver.render(sb, camera.position);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        renderGame(sb);
        if (gameOver.isSonicDie()){
            renderLoseGame(sb);
        }
        //System.out.println(Gdx.graphics.getFramesPerSecond());
        if (gameOver.isComplete()){
            renderCompleted(sb);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        bk.dispose();
        sonic.getTextureAtlas().dispose();
        sonic.getTextureAtlasSpinDash().dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (sonic.levelSpinDash >= Sonic.MAX_LEVEL_SPINDASH) {
            sonic.timeSpinDash = 0.75;
         }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if ((velocityY < 0) && (sonic.positionPlatform < 3)) {
            sonic.positionPlatform++;
        }
        if ((velocityY > 0) && (sonic.positionPlatform > 0)) {
            sonic.positionPlatform--;
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
