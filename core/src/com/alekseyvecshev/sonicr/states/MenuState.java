package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Tool.SoundMusic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Алексей on 11.04.2016.
 */
public class MenuState extends State {

    private Texture background;
    private Texture playBtn;
    private TextureAtlas startBt;
    private Sprite startIn;
    private Sprite startPress;
    private Rectangle playBtnCollision;
    private boolean isPress;
    private SoundMusic soundMusic;


    public MenuState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("menu/bg.png");
        playBtn = new Texture("menu//playbtn.png");
        startBt = new TextureAtlas(Gdx.files.internal("menu//StartBt.txt"));
        startIn = new Sprite(startBt.createSprite("start"));
        startPress = new Sprite(startBt.createSprite("startPress"));
        playBtnCollision = new Rectangle();
        soundMusic = new SoundMusic();
        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);
    }


    @Override
    protected void handleInput() {
        isPress = false;
        soundMusic.PlayMenuOst();
        if (Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (playBtnCollision.contains(touchPos.x, touchPos.y)) {
                isPress = true;
            }
        }
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (playBtnCollision.contains(touchPos.x, touchPos.y)) {
                soundMusic.StopMenuOst();
                gsm.set(new SelectLevelState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        playBtnCollision.set(490, 150, startIn.getWidth(), startIn.getHeight());
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(background, 0, 0, SonicRGame.WIDTH, SonicRGame.HEIGHT);
        if (isPress)
            sb.draw(startPress, 490, 150);
        else
            sb.draw(startIn, 490, 150);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
