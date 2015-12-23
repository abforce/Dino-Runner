package ir.abforce.dinorunner.frames;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.text.Text;

import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 9/3/15.
 */
public class ScoresFrame extends Entity {
    private Text mTextHighScore;
    private Text mTextScore;
    private boolean mIsBlinking = false;

    public ScoresFrame() {
        float x, y;

        // High score
        x = RM.CW - 100 * RM.S;
        y = RM.CH - 20 * RM.S;
        mTextHighScore = new Text(x, y, RM.fMain, "", 100, RM.VBO);
        mTextHighScore.setScale(RM.S);
        mTextHighScore.setColor(0.33f, 0.33f, 0.33f);

        // Score
        x = RM.CW - 100 * RM.S;
        y = RM.CH - 50 * RM.S;
        mTextScore = new Text(x, y, RM.fMain, "", 100, RM.VBO);
        mTextScore.setScale(RM.S);
        mTextScore.setColor(0.33f, 0.33f, 0.33f);

        this.attachChild(mTextHighScore);
        this.attachChild(mTextScore);
    }

    public void setHighScore(int score){
        mTextHighScore.setText(String.format("HI: %d", score));
    }

    public void setScore(int score){
        if(!mIsBlinking) {
            mTextScore.setText(String.format("SCORE: %d", score));
        }
    }

    public void blink(){
        if(mIsBlinking){
            return;
        }
        mIsBlinking = true;
        this.registerUpdateHandler(new BlinkUpdateHandler());
    }

    private class BlinkUpdateHandler implements IUpdateHandler{
        private int count = 8;
        private float elapsed;

        @Override
        public void onUpdate(float pSecondsElapsed) {
            elapsed += pSecondsElapsed;
            if(elapsed >= 0.1f){
                elapsed = 0;
                count -= 1;
                mTextScore.setVisible(!mTextScore.isVisible());
            }
            if(count == 0){
                ScoresFrame.this.unregisterUpdateHandler(this);
                mIsBlinking = false;
            }
        }

        @Override
        public void reset() {

        }
    }
}
