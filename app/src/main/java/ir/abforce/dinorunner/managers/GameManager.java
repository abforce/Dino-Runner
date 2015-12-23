package ir.abforce.dinorunner.managers;

import ir.abforce.dinorunner.helpers.PreferenceHelper;
import ir.abforce.dinorunner.scenes.GameSceneWrapper;

/**
 * Created by Ali Reza on 9/3/15.
 */
public class GameManager {
    private static GameManager ourInstance = new GameManager();

    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    private float mTotalGameTimeElapsed;
    private int mHiScore;
    private int mCurrentScore;
    private float mScoreStepTimeElapsed;
    private boolean mIsNewHighScore;

    public static GameManager getInstance() {
        return ourInstance;
    }

    private GameManager() {
    }

    public void reset(){
        mTotalGameTimeElapsed = 0;
        mHiScore = PreferenceHelper.getInstance().getHiScore();
        mCurrentScore = 0;
        mScoreStepTimeElapsed = 0;
        mIsNewHighScore = false;
    }

    public void onGameStep(float pSecondsElapsed){
        mTotalGameTimeElapsed += pSecondsElapsed;

        // Score
        mScoreStepTimeElapsed += pSecondsElapsed;
        if (mScoreStepTimeElapsed >= 1 / GameConstants.SCORE_PER_SECOND){
            mScoreStepTimeElapsed = 0;
            mCurrentScore += GameConstants.SCORE_STEP;
            GameSceneWrapper.getInstance().showScore(mCurrentScore);
            if(mCurrentScore % 10 == 0){
                GameSceneWrapper.getInstance().blinkScore();
                SoundManager.getInstance().playScoreSound();
            }
        }
    }

    public int getGameDifficulty(){
        if(mTotalGameTimeElapsed < GameConstants.TIME_MEDIUM){
            return DIFFICULTY_EASY;
        }
        if(mTotalGameTimeElapsed >= GameConstants.TIME_MEDIUM && mTotalGameTimeElapsed < GameConstants.TIME_HARD){
            return DIFFICULTY_MEDIUM;
        }
        return DIFFICULTY_HARD;
    }

    public float getRatioFromLastGameDifficulty(){
        if(getGameDifficulty() == DIFFICULTY_EASY){
            return mTotalGameTimeElapsed / GameConstants.TIME_MEDIUM;
        }
        if(getGameDifficulty() == DIFFICULTY_MEDIUM){
            return (mTotalGameTimeElapsed - GameConstants.TIME_MEDIUM) / (GameConstants.TIME_HARD - GameConstants.TIME_MEDIUM);
        }
        return 0;
    }

    public int getCurrentScore(){
        return mCurrentScore;
    }

    public int getHiScore(){
        return mHiScore;
    }

    public void onGameFinished(){
        if(mCurrentScore > mHiScore){
            mHiScore = mCurrentScore;
            PreferenceHelper.getInstance().setHiScore(mCurrentScore);
            mIsNewHighScore = true;
        }
    }

    public boolean isNewHighScore(){
        return this.mIsNewHighScore;
    }
}
