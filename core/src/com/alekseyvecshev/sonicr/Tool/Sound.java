package com.alekseyvecshev.sonicr.Tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Алексей on 04.06.2016.
 */
public class Sound {
    Music firstLevelOst;
    Music secondLevelOst;
    Music chaosLevelOst;
    Music menuLevelOst;
    Music fireLevelOst;
    Music shaodwLevelOst;

    boolean isPlayFirst;
    boolean isPlaySecond;
    boolean isPlayChaos;
    boolean isPlayMenu;
    boolean isPlayFire;
    boolean isPlayShadow;

    public Sound(){
        firstLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\1.mp3"));
        secondLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\2.mp3"));
        chaosLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\chaos.mp3"));
        menuLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\menu.mp3"));
        fireLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\fire.mp3"));
        shaodwLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\shadow.mp3"));

        isPlayFirst = false;
        isPlaySecond = false;
        isPlayChaos = false;
        isPlayMenu = false;
        isPlayFire = false;
        isPlayShadow = false;
    }

    private void PlayFirstOst(){
        if (!isPlayFirst){
            firstLevelOst.play();
            firstLevelOst.setLooping(true);
            isPlayFirst = true;
        }

    }

    private void PlaySecondOst(){
        if (!isPlaySecond){
            secondLevelOst.play();
            secondLevelOst.setLooping(true);
            isPlaySecond = true;
        }

    }

    private void StopFirstOst(){
        if (isPlayFirst){
            firstLevelOst.stop();
            isPlayFirst = false;
        }

    }

    private void StopSecondOst(){
        if (isPlaySecond) {
            secondLevelOst.stop();
            isPlaySecond = false;
        }
    }


    public void PlayChaosOst(){
        if (!isPlayChaos) {
            chaosLevelOst.play();
            chaosLevelOst.setLooping(true);
            isPlayChaos = true;
        }
    }

    public void StopChaosOst(){
        if (isPlayChaos) {
            chaosLevelOst.stop();
            isPlayChaos = false;
        }
    }


    public void PlayMenuOst(){
        if (!isPlayMenu) {
            menuLevelOst.play();
            isPlayMenu = true;
        }
    }

    public void StopMenuOst(){
        if (isPlayMenu){
            menuLevelOst.stop();
            isPlayMenu = false;
        }

    }

    public void PlayShadowOst(){
        if (!isPlayShadow) {
            shaodwLevelOst.play();
            isPlayShadow = true;
        }
    }

    public void StopShadowOst(){
        if (isPlayShadow){
            shaodwLevelOst.stop();
            isPlayShadow = false;
        }

    }

    public void PlayFireOst(){
        if (!isPlayFire) {
            fireLevelOst.play();
            isPlayFire = true;
        }
    }

    public void StopFireOst(){
        if (isPlayFire){
            fireLevelOst.stop();
            isPlayFire = false;
        }

    }

    public void StartPlay(){
        Preferences prefs = Gdx.app.getPreferences("Level");
        if (prefs.getInteger("number") == 1) {
            PlayFirstOst();
        }
        if (prefs.getInteger("number") == 2) {
            PlaySecondOst();
        }
    }

    public void StopPlay(){
        Preferences prefs = Gdx.app.getPreferences("Level");
        if (prefs.getInteger("number") == 1) {
            StopFirstOst();
        }
        if (prefs.getInteger("number") == 2) {
            StopSecondOst();
        }
    }
}
