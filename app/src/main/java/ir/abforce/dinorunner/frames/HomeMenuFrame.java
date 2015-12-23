package ir.abforce.dinorunner.frames;

import android.content.Intent;
import android.net.Uri;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.pool.RunnablePoolItem;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseElasticOut;

import ir.abforce.dinorunner.custom.SAnimatedSprite;
import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.helpers.SwarmHelper;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.GameManager;
import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.managers.SceneManager;
import ir.abforce.dinorunner.objects.DinoQuote;
import ir.abforce.dinorunner.objects.MainMenuButton;

/**
 * Created by Ali Reza on 9/2/15.
 */
public class HomeMenuFrame extends Entity implements IMenuFrame {

    private static final String HI_SCORE_FORMAT = "YOUR HI SCORE\n%d";
    private static float Y_CAMERA_HOME;
    private static float Y_CAMERA_ABOUT;

    private Scene mScene;
    private Sprite mSpLogo;
    private AnimatedSprite mSpDino;
    private Text mTextHiScore;
    private MainMenuButton mBtnLeaderboard;
    private MainMenuButton mBtnRateMe;
    private MainMenuButton mBtnAbout;
    private MainMenuButton mBtnSwarm;
    private DinoQuote mDinoQuote;
    private AboutFrame mAboutFrame;
    private boolean mIsCameraOnAboutFrame = false;

    public HomeMenuFrame(Scene scene) {
        this.mScene = scene;
        float x, y;

        Y_CAMERA_HOME = RM.CH / 2;
        Y_CAMERA_ABOUT = - RM.CH / 2 + RM.txButtonBg.getHeight() * RM.S / 2 + 55 * RM.S;

        // About Frame
        x = RM.CW / 2;
        y = (Y_CAMERA_ABOUT - Y_CAMERA_HOME) / 2;
        float height = RM.CH - (Y_CAMERA_HOME + Y_CAMERA_ABOUT);
        mAboutFrame = new AboutFrame(x, y, RM.CW, height);
        mAboutFrame.setIgnoreUpdate(true);
        this.attachChild(mAboutFrame);

        // Logo
        y = RM.CH - RM.txLogo.getHeight() * RM.S / 2 - 30 * RM.S;
        mSpLogo = new SSprite(GameConstants.X_OFF_CAMERA, y, RM.txLogo);
        this.attachChild(mSpLogo);

        // Hi Score
        x = RM.CW / 2;
        mTextHiScore = new Text(x, GameConstants.Y_OFF_CAMERA, RM.fMain, "", "YOUR HI SCORE\nXXXX".length(), new TextOptions(HorizontalAlign.CENTER), RM.VBO);
        mTextHiScore.setScale(RM.S);
        mTextHiScore.setColor(0.33f, 0.33f, 0.33f);
        this.attachChild(mTextHiScore);

        // Dino quote frame
        x = GameConstants.DINO_MARGIN_LEFT + RM.txDinoNormal.getWidth() * RM.S + 20 * RM.S;
        y = GameConstants.BASE_Y + RM.txDinoNormal.getHeight() * RM.S + (RM.txQuoteFrame.getHeight() / 2 + 10) * RM.S;
        mDinoQuote = new DinoQuote(x, y);
        mDinoQuote.setScale(0);
        this.attachChild(mDinoQuote);

        // Button: Swarm
        mBtnSwarm = new MainMenuButton(GameConstants.X_OFF_CAMERA, 50 * RM.S, "My Swarm", RM.txSwarm, new RunnablePoolItem() {
            @Override
            public void run() {
                SwarmHelper.showDashboard();
            }
        }, false, true);
        scene.registerTouchArea(mBtnSwarm);
        this.attachChild(mBtnSwarm);

        // Button: Leader board
        mBtnLeaderboard = new MainMenuButton(GameConstants.X_OFF_CAMERA, 210 * RM.S, "Scores", RM.txLeaderboard, new RunnablePoolItem() {
            @Override
            public void run() {
                SwarmHelper.showLeaderBoard();
            }
        });
        scene.registerTouchArea(mBtnLeaderboard);
        this.attachChild(mBtnLeaderboard);

        // Button: Rate Me!
        mBtnRateMe = new MainMenuButton(GameConstants.X_OFF_CAMERA, 130 * RM.S, "Rate Me!", RM.txIconStar, new RunnablePoolItem() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(Uri.parse("bazaar://details?id=" + RM.CONTEXT.getPackageName()));
                intent.setPackage("com.farsitel.bazaar");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RM.CONTEXT.startActivity(intent);
            }
        }, true);
        scene.registerTouchArea(mBtnRateMe);
        this.attachChild(mBtnRateMe);

        // Button: About
        mBtnAbout = new MainMenuButton(GameConstants.X_OFF_CAMERA, 50 * RM.S, "About", RM.txIconAbout, new RunnablePoolItem() {
            @Override
            public void run() {
                toggleAbout();
            }
        });
        scene.registerTouchArea(mBtnAbout);
        this.attachChild(mBtnAbout);

        scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                return mBtnRateMe.onSceneTouched(pSceneTouchEvent) ||
                        mBtnAbout.onSceneTouched(pSceneTouchEvent) ||
                        mBtnLeaderboard.onSceneTouched(pSceneTouchEvent) ||
                        mBtnSwarm.onSceneTouched(pSceneTouchEvent);
            }
        });
    }

    public void toggleAbout(){
        SmoothCamera camera = ((SmoothCamera) RM.ENGINE.getCamera());
        camera.setMaxVelocityY(1800 * RM.S);
        if (mIsCameraOnAboutFrame) {
            mIsCameraOnAboutFrame = false;
            camera.setCenter(camera.getCenterX(), Y_CAMERA_HOME);
            mAboutFrame.setIgnoreUpdate(true);
        } else {
            mIsCameraOnAboutFrame = true;
            camera.setCenter(camera.getCenterX(), Y_CAMERA_ABOUT);
            mAboutFrame.setIgnoreUpdate(false);
        }
    }

    public boolean isCameraOnAboutFrame(){
        return this.mIsCameraOnAboutFrame;
    }

    @Override
    public void animateObjectsIn() {
        float fromY, toY, fromX, toX;

        // Logo
        fromX = RM.CW + RM.txLogo.getWidth() * RM.S / 2;
        toX = RM.CW / 2;
        mSpLogo.registerEntityModifier(new MoveXModifier(3, fromX, toX));

        // Dino
        if(mSpDino != null){
            mSpDino.dispose();
            mSpDino.detachSelf();
        }
        fromY = mSpLogo.getY() - mSpLogo.getHeightScaled() / 2 + RM.txDinoDuck.getHeight() * RM.S / 2;
        mSpDino = new SAnimatedSprite(GameConstants.X_OFF_CAMERA, fromY, RM.txDinoDuck);
        mSpDino.setFlippedHorizontal(true);
        this.attachChild(mSpDino);
        fromX = fromX + RM.txLogo.getWidth() * RM.S / 2 + RM.txDinoDuck.getWidth() * RM.S / 2;
        toX = toX + RM.txLogo.getWidth() * RM.S / 2 + RM.txDinoDuck.getWidth() * RM.S / 2;
        mSpDino.registerEntityModifier(new MoveXModifier(3, fromX, toX, new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                mSpDino.dispose();
                mSpDino.detachSelf();
                float x = RM.CW / 2 + RM.txLogo.getWidth() * RM.S / 2 + RM.txDinoBlink.getWidth() * RM.S / 2;
                float y = mSpLogo.getY() - mSpLogo.getHeightScaled() / 2 + RM.txDinoBlink.getHeight() * RM.S / 2;
                mSpDino = new SAnimatedSprite(x, y, RM.txDinoBlink);
                mSpDino.setFlippedHorizontal(true);
                mSpDino.animate(new long[]{3000, 150});
                HomeMenuFrame.this.attachChild(mSpDino);
            }
        }));
        mSpDino.animate(180);

        // Hi score
        mTextHiScore.setText(String.format(HI_SCORE_FORMAT, GameManager.getInstance().getHiScore()));
        fromY = - mTextHiScore.getHeightScaled() / 2;
        toY = mTextHiScore.getHeightScaled() / 2 + 20 * RM.S;
        mTextHiScore.registerEntityModifier(new MoveYModifier(1f, fromY, toY, EaseElasticOut.getInstance()));

        // Button: Swarm
        fromX = - mBtnSwarm.getWidthScaled() / 2;
        toX = mBtnSwarm.getWidthScaled() / 2;
        mBtnSwarm.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(1.5f),
                new MoveXModifier(1, fromX, toX, EaseElasticOut.getInstance())
        ));

        // Button: Leader board
        fromX = RM.CW + mBtnLeaderboard.getWidthScaled() / 2;
        toX = RM.CW - mBtnLeaderboard.getWidthScaled() / 2;
        mBtnLeaderboard.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(1.5f),
                new MoveXModifier(1, fromX, toX, EaseElasticOut.getInstance())
        ));

        // Button: Rate Me!
        fromX = RM.CW + mBtnRateMe.getWidthScaled() / 2;
        toX = RM.CW - mBtnRateMe.getWidthScaled() / 2;
        mBtnRateMe.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(2),
                new MoveXModifier(1, fromX, toX, EaseElasticOut.getInstance())
        ));

        // Button: About
        fromX = RM.CW + mBtnAbout.getWidthScaled() / 2;
        toX = RM.CW - mBtnAbout.getWidthScaled() / 2;
        mBtnAbout.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(2.5f),
                new MoveXModifier(1, fromX, toX, EaseElasticOut.getInstance())
        ));

        // Dino quote
        mDinoQuote.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(4),
                new ScaleModifier(1.5f, 0, RM.S, new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        mDinoQuote.start();
                    }
                }, EaseElasticOut.getInstance())
        ));
    }

    @Override
    public void animateObjectsOut() {
        float fromY, toY, fromX, toX;

        // Logo
        fromY = mSpLogo.getY();
        toY = RM.CH + mSpLogo.getHeightScaled() / 2;
        mSpLogo.registerEntityModifier(new MoveYModifier(0.3f, fromY, toY));

        // Dino
        fromY = mSpDino.getY();
        toY = RM.CH + mSpDino.getHeightScaled() / 2;
        mSpDino.registerEntityModifier(new MoveYModifier(0.5f, fromY, toY));

        // Hi score
        fromY = mTextHiScore.getY();
        toY = - mTextHiScore.getHeightScaled() / 2;
        mTextHiScore.registerEntityModifier(new MoveYModifier(0.5f, fromY, toY));

        // Button: Swarm
        fromX = mBtnSwarm.getX();
        toX = - mBtnSwarm.getWidthScaled() / 2;
        mBtnSwarm.registerEntityModifier(new MoveXModifier(0.5f, fromX, toX));

        // Button: Leader board
        fromX = mBtnLeaderboard.getX();
        toX = RM.CW + mBtnLeaderboard.getWidthScaled() / 2;
        mBtnLeaderboard.registerEntityModifier(new MoveXModifier(0.5f, fromX, toX));

        // Button: Rate Me!
        mBtnRateMe.registerEntityModifier(new MoveXModifier(0.5f, fromX, toX));

        // Button: About
        mBtnAbout.registerEntityModifier(new MoveXModifier(0.5f, fromX, toX));

        // Dino quote
        mDinoQuote.registerEntityModifier(new ScaleModifier(0.5f, RM.S, 0));
    }

    @Override
    public void onMainButtonClicked() {
        SceneManager.runGame();
    }

    @Override
    public ITextureRegion getMainButtonTextureRegion() {
        return RM.txPlay;
    }

    @Override
    public void clearObjectsAnimations() {
        // Logo
        if(mSpLogo.getEntityModifierCount() > 0){
            mSpLogo.clearEntityModifiers();
        }

        // Dino
        if(mSpDino.getEntityModifierCount() > 0){
            mSpDino.clearEntityModifiers();
        }

        // Dino Quote
        if(mDinoQuote.getEntityModifierCount() > 0){
            mDinoQuote.clearEntityModifiers();
        }

        // Button: Leader board
        if(mBtnLeaderboard.getEntityModifierCount() > 0){
            mBtnLeaderboard.clearEntityModifiers();
        }

        // Button: Rate Me!
        if(mBtnRateMe.getEntityModifierCount() > 0){
            mBtnRateMe.clearEntityModifiers();
        }

        // Button: About
        if(mBtnAbout.getEntityModifierCount() > 0){
            mBtnAbout.clearEntityModifiers();
        }

        // Hi score
        if(mTextHiScore.getEntityModifierCount() > 0){
            mTextHiScore.clearEntityModifiers();
        }
    }

    @Override
    public void cleanUp() {
        this.mScene.setOnSceneTouchListener(null);
        this.mScene.unregisterTouchArea(mBtnSwarm);
        this.mScene.unregisterTouchArea(mBtnLeaderboard);
        this.mScene.unregisterTouchArea(mBtnRateMe);
        this.mScene.unregisterTouchArea(mBtnAbout);
        this.dispose();
    }
}
