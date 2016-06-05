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
    Music thirdLevelOst;

    boolean isPlayFirst;
    boolean isPlaySecond;
    boolean isPlayThird;

    public Sound(){
        firstLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\1.mp3"));
        secondLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\2.mp3"));
        thirdLevelOst = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Music\\3.mp3"));

        isPlayFirst = false;
        isPlaySecond = false;
        isPlayThird = false;
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

    private void PlayThirdOst(){
        thirdLevelOst.play();
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

    private void StopThirdOst(){
        thirdLevelOst.stop();
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
