package ir.abforce.dinorunner.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 9/3/15.
 */
public class PreferenceHelper {
    private static PreferenceHelper ourInstance = new PreferenceHelper();

    private static final String KEY_HI_SCORE = "hi-score";

    private SharedPreferences mSharedPreferences;

    public static PreferenceHelper getInstance() {
        return ourInstance;
    }

    private PreferenceHelper() {
        mSharedPreferences = RM.CONTEXT.getSharedPreferences("dino-prefs", Context.MODE_PRIVATE);
    }

    public void setHiScore(int score){
        mSharedPreferences.edit().putInt(KEY_HI_SCORE, score).commit();
    }

    public int getHiScore(){
        return mSharedPreferences.getInt(KEY_HI_SCORE, 0);
    }
}
