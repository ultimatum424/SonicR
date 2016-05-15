package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Алексей on 14.05.2016.
 */
public class SelectLevelState extends State{
    TextureAtlas textureAtlas;
    Texture bk;
    Rectangle lvl10;
    Rectangle lvl11;
    Rectangle lvl20;
    Rectangle lvl21;
    Rectangle lvl30;
    Rectangle lvl31;
    public SelectLevelState(GameStateManager gsm) {
        super(gsm);
        bk = new Texture("SelectLevel/bk.png");
        lvl10 = new Rectangle(25 , 460, 305, 240);
        lvl11 = new Rectangle(25 , 160, 305, 240);
        lvl20 = new Rectangle(450 , 160, 305, 240);
        lvl21 = new Rectangle(480 , 160, 353, 287);
        lvl30 = new Rectangle(900 , 0, 353, 287);
        lvl31 = new Rectangle(900 , 300, 353, 287);
        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            System.out.println(touchPos);
            if (lvl10.contains(touchPos.x, touchPos.y))
                gsm.set(new PlayState(gsm));
            if (lvl11.contains(touchPos.x, touchPos.y))
                gsm.set(new BossRobotState(gsm));
            if (lvl21.contains(touchPos.x, touchPos.y))
                gsm.set(new BossShadowState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bk, 0 ,0);
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
