package com.alekseyvecshev.sonicr.states;

import com.alekseyvecshev.sonicr.SonicRGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Алексей on 14.05.2016.
 */
public class SelectLevelState extends State{
    TextureAtlas textureAtlas;
    Texture bk;
    Texture black;
    Array<Vector2> posBlack;
    Rectangle lvl10;
    Rectangle lvl11;
    Rectangle lvl20;
    Rectangle lvl21;
    Rectangle lvl30;
    Rectangle lvl31;
    public SelectLevelState(GameStateManager gsm) {
        super(gsm);
        bk = new Texture("SelectLevel/bk.png");
        black = new Texture("SelectLevel/black.png");

        lvl10 = new Rectangle(25 , 460, 305, 240);
        lvl11 = new Rectangle(25 , 160, 305, 240);
        lvl20 = new Rectangle(480 , 460, 305, 240);
        lvl21 = new Rectangle(480 , 160, 305, 240);
        lvl30 = new Rectangle(930 , 460, 305, 240);
        lvl31 = new Rectangle(930 , 160, 305, 240);

        posBlack = new Array<Vector2>();
       // posBlack.addAll( new Vector2(930, 460),new Vector2(480, 160), new Vector2(480, 460), new Vector2(25, 160));
        posBlack.addAll( new Vector2(25, 160),new Vector2(480, 460), new Vector2(480, 160), new Vector2(930, 460));
        camera.setToOrtho(false, SonicRGame.WIDTH, SonicRGame.HEIGHT);
    }

    @Override
    protected void handleInput() {
        Preferences prefs = Gdx.app.getPreferences("Level");
        Preferences prefs2 = Gdx.app.getPreferences("LevelOpen");
        int numberLevel = prefs2.getInteger("level");
         //prefs2.putInteger("level", 2);
        // prefs2.flush();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            System.out.println(touchPos);
            if (lvl10.contains(touchPos.x, touchPos.y) && numberLevel >= 0){
                prefs.putInteger("number", 1);
                prefs.flush();
                gsm.set(new PlayState(gsm));
            }
            if (lvl20.contains(touchPos.x, touchPos.y) && numberLevel >= 2){
                prefs.putInteger("number", 2);
                prefs.flush();
                gsm.set(new PlayState(gsm));
            }
            if (lvl30.contains(touchPos.x, touchPos.y)&& numberLevel >= 4) {
                prefs.putInteger("number", 3);
               prefs.flush();
                gsm.set(new PlayState(gsm));
            }
            if (lvl11.contains(touchPos.x, touchPos.y)&& numberLevel >= 1) {
                prefs.putInteger("number", 1);
                prefs.flush();
                gsm.set(new BossRobotState(gsm));
            }
            if (lvl21.contains(touchPos.x, touchPos.y)&& numberLevel >= 3) {
                prefs.putInteger("number", 2);
                prefs.flush();
                gsm.set(new BossShadowState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    private void renderBlack(SpriteBatch sb){
        Preferences prefs2 = Gdx.app.getPreferences("LevelOpen");
        int numberLevel = prefs2.getInteger("level");
        //System.out.println(numberLevel);
        for (int i = numberLevel; i < 4; i++){
            sb.draw(black, posBlack.get(i).x, posBlack.get(i).y);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(bk, 0 ,0);
        renderBlack(sb);
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
