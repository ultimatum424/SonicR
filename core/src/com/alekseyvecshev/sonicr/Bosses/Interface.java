package com.alekseyvecshev.sonicr.Bosses;

import com.alekseyvecshev.sonicr.states.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Алексей on 27.04.2016.
 */
public class Interface {
    private TextureAtlas textureAtlas;
    private Array<ElementInterface> sprites;
    private static final String HP_SONIC = "hpSonic";
    private static final String HP_BOSS = "hpBoss";
    private static final String SPINDASH = "spindash";

    public Interface(int hpSonic, int hpBoss){
        sprites = new Array<ElementInterface>();
        textureAtlas = new TextureAtlas(Gdx.files.internal("BossStage\\interfaceBosses.txt"));
        sprites.add(new ElementInterface(textureAtlas.createSprite("iconhpsonic"), "iconHpSonic", 280, 310, 1));
        sprites.add(new ElementInterface(textureAtlas.createSprite("iconhpboss"), "iconHpBoss", -320, 310, 1));
        sprites.add(new ElementInterface(textureAtlas.createSprite("iconspindash"), "iconSpindash", 275, 245, 1));

        sprites.add(new ElementInterface(textureAtlas.createSprite("hpsonic"), "hpSonic", 332, 329, hpSonic));
        sprites.add(new ElementInterface(textureAtlas.createSprite("hpboss"), "hpBoss", -628, 329, hpBoss));
        sprites.add(new ElementInterface(textureAtlas.createSprite("spindash"), "spindash", 332, 264, 2));

        sprites.add(new ElementInterface(textureAtlas.createSprite("bk"), "bkHpSonic", 330, 325, 1)); // sonic
        sprites.add(new ElementInterface(textureAtlas.createSprite("bk"), "bkHpBoss", -630, 325, 1)); // boss
        sprites.add(new ElementInterface(textureAtlas.createSprite("bk"), "bkSpinDash", 330, 260, 1));  //spindash


    }
    public void update(float dt, Vector3 posCamera, int hpSonic, int hpBoss, double spinDashTime){

        for (ElementInterface sprite : sprites){
            if (sprite.getName().equals(HP_SONIC)){
                sprite.update(dt, posCamera, hpSonic);
            }
            else if (sprite.getName().equals(HP_BOSS)){
                sprite.update(dt, posCamera, hpBoss);
            }
            else if (sprite.getName().equals(SPINDASH)){
                sprite.update(dt, posCamera, (int)spinDashTime);
            }
            else {
                sprite.update(dt, posCamera, 1);
            }
        }
    }

    public void render(SpriteBatch sb){

        for (ElementInterface sprite : sprites){
            sprite.render(sb);
        }
    }
    public void dispose(){
        textureAtlas.dispose();
    }
}
