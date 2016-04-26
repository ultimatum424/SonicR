package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.alekseyvecshev.sonicr.Sprites.Obstacle;
import com.alekseyvecshev.sonicr.Tool.ScoreTable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;

/**
 * Created by Алексей on 11.04.2016.
 */
public class MenuState extends State {

    private Texture background;
    private Texture playBtn;
    private TextureAtlas startBt;
    private Sprite startIn;
    private Sprite startPress;


    public MenuState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("menu/bg.png");
        playBtn = new Texture("menu//playbtn.png");
        startBt = new TextureAtlas(Gdx.files.internal("menu//StartBt.txt"));
        startIn = new Sprite(startBt.createSprite("start"));
        startPress = new Sprite(startBt.createSprite("startPress"));
        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);
    }



    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()){
            if ((490 < Gdx.input.getX() && (Gdx.input.getX() < (490 + startIn.getTexture().getWidth()))))
                if ((445 < Gdx.input.getY() && (Gdx.input.getY() < (445 + startIn.getTexture().getHeight()))))
                    gsm.set(new BossRobotState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(background, 0, 0, SonicRGame.WIDTH, SonicRGame.HEIGHT);
        sb.draw(startIn, 490, 150);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
