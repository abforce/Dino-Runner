package ir.abforce.dinorunner.frames;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.ease.EaseElasticOut;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.helpers.SwarmHelper;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.GameManager;
import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.managers.SceneManager;
import ir.abforce.dinorunner.objects.MainMenuButton;

/**
 * Created by Ali Reza on 9/2/15.
 */

@SuppressWarnings("deprecation")
public class GameOverMenuFrame extends Entity implements IMenuFrame {
    private Scene mScene;
    private SSprite mSpGameOver;
    private MainMenuButton mBtnSubmit;

    public GameOverMenuFrame(Scene scene) {
        mScene = scene;
        float x, y;
        String text;
        int score = GameManager.getInstance().getCurrentScore();
        int hiScore = GameManager.getInstance().getHiScore();

        // Game Over
        x = RM.CW / 2;
        mSpGameOver = new SSprite(x, GameConstants.Y_OFF_CAMERA, RM.txGameOver);
        this.attachChild(mSpGameOver);

        // Button: Submit
        mBtnSubmit = new MainMenuButton(GameConstants.X_OFF_CAMERA, 50 * RM.S, "Submit", RM.txSwarm, new Runnable() {
            @Override
            public void run() {
                SwarmHelper.submitScore(GameManager.getInstance().getHiScore());
            }
        });
        scene.registerTouchArea(mBtnSubmit);
        this.attachChild(mBtnSubmit);

        // Arrow
        x = RM.CW - mBtnSubmit.getWidthScaled() / 2;
        y = 100 * RM.S + RM.txArrow.getHeight() * RM.S / 2;
        this.attachChild(new SSprite(x, y, RM.txArrow));

        // Text submit
        y += 100 * RM.S;
        text = "Submit your\nhigh score\nto Swarm";
        Text textSubmit = new Text(x, y, RM.fMain, text, new TextOptions(HorizontalAlign.CENTER), RM.VBO);
        textSubmit.setColor(0.33f, 0.33f, 0.33f);
        textSubmit.setScale(RM.S);
        this.attachChild(textSubmit);

        // Text scores
        x = RM.CW / 2;
        y = 60 * RM.S;
        text = String.format("Score: %d\n\nHigh Score: %d", score, hiScore);
        Text textScores = new Text(x, y, RM.fMain, text, new TextOptions(HorizontalAlign.CENTER), RM.VBO);
        textScores.setColor(0.33f, 0.33f, 0.33f);
        textScores.setScale(RM.S);
        this.attachChild(textScores);

        // Text new high score
        if(GameManager.getInstance().isNewHighScore()) {
            x = RM.CW / 2;
            y = 125 * RM.S;
            text = "*** New High Score ***";
            final Text textNewHighScore = new Text(x, y, RM.fMain, text, new TextOptions(HorizontalAlign.CENTER), RM.VBO);
            textNewHighScore.setColor(0.33f, 0.33f, 0.33f);
            textNewHighScore.setScale(RM.S);
            textNewHighScore.registerUpdateHandler(new IUpdateHandler() {
                float mTotalElapsed;
                @Override
                public void onUpdate(float pSecondsElapsed) {
                    mTotalElapsed += pSecondsElapsed;
                    if(mTotalElapsed >= 0.5f){
                        mTotalElapsed = 0;
                        textNewHighScore.setVisible(!textNewHighScore.isVisible());
                    }
                }

                @Override
                public void reset() {

                }
            });
            this.attachChild(textNewHighScore);
        }

        // Scene touch listener
        scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                return mBtnSubmit.onSceneTouched(pSceneTouchEvent);
            }
        });
    }

    @Override
    public void animateObjectsIn() {
        float fromY, toY, fromX, toX;

        // Game Over
        fromY = RM.CH + RM.txGameOver.getHeight() * RM.S / 2;
        toY = RM.CH - RM.txGameOver.getHeight() * RM.S / 2 - 30 * RM.S;
        mSpGameOver.registerEntityModifier(new MoveYModifier(0.5f, fromY, toY, EaseElasticOut.getInstance()));

        // Button: Submit
        fromX = RM.CW + mBtnSubmit.getWidthScaled() / 2;
        toX = RM.CW - mBtnSubmit.getWidthScaled() / 2;
        mBtnSubmit.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(0.5f),
                new MoveXModifier(1, fromX, toX, EaseElasticOut.getInstance())
        ));
    }

    @Override
    public void animateObjectsOut() {
        float fromY, toY;

        // Game Over
        fromY = mSpGameOver.getY();
        toY = RM.CH + RM.txGameOver.getHeight() * RM.S / 2;
        mSpGameOver.registerEntityModifier(new MoveYModifier(0.5f, fromY, toY));
    }

    @Override
    public void onMainButtonClicked() {
        SceneManager.restart();
    }

    @Override
    public ITextureRegion getMainButtonTextureRegion() {
        return RM.txRePlay;
    }

    @Override
    public void clearObjectsAnimations() {
        if(mSpGameOver.getEntityModifierCount() > 0){
            mSpGameOver.clearEntityModifiers();
        }
        if(mBtnSubmit.getEntityModifierCount() > 0){
            mBtnSubmit.clearEntityModifiers();
        }
    }

    @Override
    public void cleanUp() {
        mScene.setOnSceneTouchListener(null);
        mScene.unregisterTouchArea(mBtnSubmit);
        this.dispose();
    }
}
