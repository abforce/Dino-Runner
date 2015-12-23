package ir.abforce.dinorunner.managers;

import android.hardware.SensorManager;

import org.andengine.util.adt.color.Color;

/**
 * Created by Ali Reza on 8/6/15.
 */
public class GameConstants {
    public static final String SWARM_APP_KEY = "0a5dd708de15c942a00becfe8ed828b5";
    public static final int SWARM_APP_ID = 19181;
    public static final int SWARM_LEADERBOARD_ID = 20003;

    public static final float SCORE_PER_SECOND = 2;
    public static final int SCORE_STEP = 1;

    public static float X_OFF_CAMERA;
    public static float Y_OFF_CAMERA;
    public static float BASE_Y;
    public static float DINO_MARGIN_LEFT;
    public static float BUTTON_MARGIN;
    public static final Color GAME_BG_COLOR = new Color(1f, 1f, 1f);

    public static float GRAVITY_EASY;
    public static float JUMP_VELOCITY_EASY;

    public static float TIME_MEDIUM = 15f;
    public static float TIME_HARD = 40f;

    public static float SPEED_EASY;
    public static final float SPEED_LEVEL_COE = 1.2f;
    public static float SPEED_MEDIUM;
    public static float SPEED_HARD;
    public static final float JUMP_DURATION_LEVEL_COE = 0.9f;
    public static float JUMP_VELOCITY_MEDIUM;
    public static float JUMP_VELOCITY_HARD;
    public static float GRAVITY_MEDIUM;
    public static float GRAVITY_HARD;

    public static void init(){
        BASE_Y = RM.CH / 4;
        DINO_MARGIN_LEFT = RM.CW / 20;
        BUTTON_MARGIN = RM.CW / 30;

        GRAVITY_EASY = - SensorManager.GRAVITY_EARTH * 12 * RM.S;
        JUMP_VELOCITY_EASY = 35 * RM.S;
        SPEED_EASY = 7 * RM.S;

        SPEED_MEDIUM = SPEED_EASY * SPEED_LEVEL_COE;
        SPEED_HARD = SPEED_MEDIUM * SPEED_LEVEL_COE;

        JUMP_VELOCITY_MEDIUM = JUMP_VELOCITY_EASY / JUMP_DURATION_LEVEL_COE;
        JUMP_VELOCITY_HARD = JUMP_VELOCITY_MEDIUM / JUMP_DURATION_LEVEL_COE;

        GRAVITY_MEDIUM = (float) (GRAVITY_EASY / Math.pow(JUMP_DURATION_LEVEL_COE, 2));
        GRAVITY_HARD = (float) (GRAVITY_MEDIUM / Math.pow(JUMP_DURATION_LEVEL_COE, 2));

        X_OFF_CAMERA = Y_OFF_CAMERA = - 500 * RM.S;
    }
}
