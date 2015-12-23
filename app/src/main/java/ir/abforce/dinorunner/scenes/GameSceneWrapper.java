package ir.abforce.dinorunner.scenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;

import ir.abforce.dinorunner.frames.ScoresFrame;
import ir.abforce.dinorunner.generators.BirdGenerator;
import ir.abforce.dinorunner.generators.CloudGenerator;
import ir.abforce.dinorunner.generators.GroundGenerator;
import ir.abforce.dinorunner.generators.ObstacleGenerator;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.GameManager;
import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.managers.SceneManager;
import ir.abforce.dinorunner.objects.ButtonsController;
import ir.abforce.dinorunner.objects.DinoController;
import ir.abforce.dinorunner.objects.GameObject;
import ir.abforce.dinorunner.utils.MathUtils;

/**
 * Created by Ali Reza on 5/29/15.
 */
public class GameSceneWrapper extends SceneWrapper {
    protected static GameSceneWrapper ourInstance;

    private static final FixtureDef FIXTURE_WALL = PhysicsFactory.createFixtureDef(0, 0, 0);
    private ScoresFrame mScores;
    private GroundGenerator mGroundGenerator;
    private CloudGenerator mCloudGenerator;
    private ObstacleGenerator mObstacleGenerator;
    private BirdGenerator mBirdGenerator;
    private DinoController mDinoController;
    private ButtonsController mButtonsController;
    private boolean mRunning = false;
    private float mSpeed;
    private float mJumpVelocity;
    private PhysicsWorld mPhysicsWorld;

    public static Scene build(){
        ourInstance = new GameSceneWrapper();
        return ourInstance.getScene();
    }

    public static GameSceneWrapper getInstance(){
        return ourInstance;
    }

    private GameSceneWrapper(){
        super();
        mScene.setBackground(new Background(GameConstants.GAME_BG_COLOR));
        mScene.setOnAreaTouchListener(new IOnAreaTouchListener() {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return mButtonsController.onSceneAreaTouched(pSceneTouchEvent, pTouchArea);
            }
        });
        mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                return mButtonsController.onSceneTouched(pSceneTouchEvent);
            }
        });

        // Prepare menu scene
        MenuSceneWrapper.build();

        // Scores frame
        mScores = new ScoresFrame();
        mScores.setHighScore(GameManager.getInstance().getHiScore());
        mScores.setScore(GameManager.getInstance().getCurrentScore());
        mScene.attachChild(mScores);

        // Physics
        mPhysicsWorld = new PhysicsWorld(new Vector2(0, GameConstants.GRAVITY_EASY), true);
        mScene.registerUpdateHandler(mPhysicsWorld);

        // Ground
        mGroundGenerator = new GroundGenerator(mScene);
        mGroundGenerator.setEnabled(true);
        mGroundGenerator.init();

        // Clouds
        mCloudGenerator = new CloudGenerator(mScene);
        mCloudGenerator.setInitialGenerateRate(0.3f);
        mCloudGenerator.setEnabled(true);
        mCloudGenerator.init();

        // Obstacles
        mObstacleGenerator = new ObstacleGenerator(mScene);
        mObstacleGenerator.setInitialGenerateRate(0.9f);
        mObstacleGenerator.setEnabled(true);
        mObstacleGenerator.init();

        // Flying Birds
        mBirdGenerator = new BirdGenerator(mScene);
        mBirdGenerator.setInitialGenerateRate(0.1f);
        mBirdGenerator.setEnabled(true);
        mBirdGenerator.init();

        // Our lovely Dino !
        mDinoController = new DinoController(mScene, mPhysicsWorld);

        // Controlling buttons
        mButtonsController = new ButtonsController(mScene, new ButtonsController.OnButtonClickListener() {
            @Override
            public void onButtonJumpHeld() {
                mDinoController.jump();
            }

            @Override
            public void onButtonJumpReleased() {

            }

            @Override
            public void onButtonDuckHeld() {
                mDinoController.duckStart();
            }

            @Override
            public void onButtonDuckReleased() {
                mDinoController.duckEnd();
            }
        });

        // Physician Ground
        PhysicsFactory.createBoxBody(mPhysicsWorld, RM.CW / 2, GameConstants.BASE_Y, RM.CW, 0, BodyDef.BodyType.StaticBody, FIXTURE_WALL);

        // Main game update handler
        mScene.registerUpdateHandler(new IUpdateHandler() {

            @Override
            public void onUpdate(float pSecondsElapsed) {
                if(!mRunning){
                    return;
                }
                GameManager.getInstance().onGameStep(pSecondsElapsed);

                // Move game objects
                mDinoController.onGameStep();
                mGroundGenerator.onGameStep(pSecondsElapsed);
                mCloudGenerator.onGameStep(pSecondsElapsed);
                mObstacleGenerator.onGameStep(pSecondsElapsed);
                mBirdGenerator.onGameStep(pSecondsElapsed);

                // Collision
                if(dinoIsHit()){
                    mDinoController.die();
                    SceneManager.gameOver();
                }

                // Easy
                float r = GameManager.getInstance().getRatioFromLastGameDifficulty();
                if(GameManager.getInstance().getGameDifficulty() == GameManager.DIFFICULTY_EASY){
                    mSpeed = MathUtils.middleValue(GameConstants.SPEED_EASY, GameConstants.SPEED_MEDIUM, r);
                    updateGeneratorsSpeed();
                    mJumpVelocity = MathUtils.middleValue(GameConstants.JUMP_VELOCITY_EASY, GameConstants.JUMP_VELOCITY_MEDIUM, r);
                    float gravity = MathUtils.middleValue(GameConstants.GRAVITY_EASY, GameConstants.GRAVITY_MEDIUM, r);
                    Vector2 vGravity = Vector2Pool.obtain(0, gravity);
                    mPhysicsWorld.setGravity(vGravity);
                    Vector2Pool.recycle(vGravity);
                }

                // Medium
                if(GameManager.getInstance().getGameDifficulty() == GameManager.DIFFICULTY_MEDIUM){
                    mSpeed = MathUtils.middleValue(GameConstants.SPEED_MEDIUM, GameConstants.SPEED_HARD, r);
                    updateGeneratorsSpeed();
                    mJumpVelocity = MathUtils.middleValue(GameConstants.JUMP_VELOCITY_MEDIUM, GameConstants.JUMP_VELOCITY_HARD, r);
                    float gravity = MathUtils.middleValue(GameConstants.GRAVITY_MEDIUM, GameConstants.GRAVITY_HARD, r);
                    Vector2 vGravity = Vector2Pool.obtain(0, gravity);
                    mPhysicsWorld.setGravity(vGravity);
                    Vector2Pool.recycle(vGravity);
                }
            }

            @Override
            public void reset() {

            }
        });
    }

    private void updateGeneratorsSpeed(){
        mGroundGenerator.setSpeed(mSpeed);
        mCloudGenerator.setSpeed(mSpeed / 5);
        mObstacleGenerator.setSpeed(mSpeed);
        mBirdGenerator.setSpeed(mSpeed / 1.2f);
    }

    private boolean dinoIsHit(){
        for(GameObject object : mObstacleGenerator.getOnTheSceneGameObjects()){
            if(mDinoController.collidesWith(object)){
                return true;
            }
        }
        for(GameObject object : mBirdGenerator.getOnTheSceneGameObjects()){
            if(mDinoController.collidesWith(object)){
                return true;
            }
        }
        return false;
    }

    public float getJumpVelocity(){
        return this.mJumpVelocity;
    }

    public ObstacleGenerator getObstacleGenerator(){
        return this.mObstacleGenerator;
    }

    public void showScore(int score){
        mScores.setScore(score);
    }

    public void blinkScore(){
        mScores.blink();
    }

    public void gameOver(){
        MenuSceneWrapper.getInstance().prepareForGameOver();
        mScene.setChildScene(MenuSceneWrapper.getInstance().getScene(), false, true, true);
        mRunning = false;
    }

    public void pauseGame(){
        MenuSceneWrapper.getInstance().prepareForPause();
        mScene.setChildScene(MenuSceneWrapper.getInstance().getScene(), false, true, true);
        mRunning = false;
    }

    public void showHomeMenu(){
        MenuSceneWrapper.getInstance().prepareForHome();
        mScene.setChildScene(MenuSceneWrapper.getInstance().getScene(), false, true, true);
        mRunning = false;
    }

    public void runGame(){
        mDinoController.run();
        mRunning = true;
    }
}