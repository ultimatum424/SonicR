package com.alekseyvecshev.sonicr.Tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Алексей on 18.04.2016.
 */
public class ScoreTable {

    private int score;
    Preferences preferences;

    public void ScoreTable() {

        preferences = getScoreTable();
    }

    private Preferences getScoreTable() {
        if (preferences == null) {
            preferences = Gdx.app.getPreferences("ScoreTable");
        }
        preferences.flush();
        return Gdx.app.getPreferences("ScoreTable");
    }

    public void addBesetScore(int bestScore) {
        preferences = getScoreTable();
        if ((getBestScore()) < bestScore) {
            preferences.putInteger("score", bestScore);
        }
        preferences.flush();
    }

    public int getBestScore() {
        return preferences.getInteger("score");
    }
}
