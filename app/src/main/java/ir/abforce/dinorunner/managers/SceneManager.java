package ir.abforce.dinorunner.managers;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.ScreenCapture;
import org.andengine.util.modifier.IModifier;

import ir.abforce.dinorunner.frames.HomeMenuFrame;
import ir.abforce.dinorunner.scenes.GameSceneWrapper;
import ir.abforce.dinorunner.scenes.MenuSceneWrapper;
import ir.abforce.dinorunner.scenes.SplashSceneWrapper;

/**
 * Created by Ali Reza on 5/20/15.
 */
public class SceneManager {

    private static final int ST_SPLASH = 0;
    private static final int ST_MENU_HOME = 1;
    private static final int ST_GAME_PAUSED = 2;
    private static final int ST_GAME_RUNNING = 3;
    private static final int ST_GAME_OVER = 4;

    private static final State STATE = new State();

    public static void goHomeMenu(){
        GameManager.getInstance().reset();
        GameSceneWrapper oldScene = GameSceneWrapper.getInstance();
        changeScene(GameSceneWrapper.build());
        GameSceneWrapper.getInstance().showHomeMenu();
        STATE.mCurrentState = ST_MENU_HOME;
        if(oldScene != null){
            oldScene.disposeScene();
        }
    }

    public static void restart(){
        GameManager.getInstance().reset();
        GameSceneWrapper oldScene = GameSceneWrapper.getInstance();
        changeScene(GameSceneWrapper.build());
        runGame();
        oldScene.disposeScene();
    }

    public static void gameOver(){
        GameManager.getInstance().onGameFinished();
        GameSceneWrapper.getInstance().gameOver();
        STATE.mCurrentState = ST_GAME_OVER;
    }

    public static void runGame(){
        GameSceneWrapper.getInstance().runGame();
        STATE.mCurrentState = ST_GAME_RUNNING;
    }

    public static void pauseGame(){
        GameSceneWrapper.getInstance().pauseGame();
        STATE.mCurrentState = ST_GAME_PAUSED;
    }

    public static Scene onCreateScene(){
        RM.loadGameSceneResources();
        STATE.mCurrentState = ST_SPLASH;
        return SplashSceneWrapper.build();
    }

    public static boolean onBackPressed(){
        int state = STATE.mCurrentState;
        if(state == ST_MENU_HOME){
            HomeMenuFrame frame =(HomeMenuFrame) MenuSceneWrapper.getInstance().getMenuFrame();
            if(frame.isCameraOnAboutFrame()){
                frame.toggleAbout();
                return false;
            } else {
                return true;
            }
        }
        if(state == ST_SPLASH){
            return true;
        }
        if(state == ST_GAME_RUNNING){
            pauseGame();
            return false;
        }
        if(state == ST_GAME_PAUSED || state == ST_GAME_OVER){
            goHomeMenu();
            return false;
        }
        return true;
    }

    public static void onGamePaused(){
        if(STATE.mCurrentState == ST_GAME_RUNNING){
            pauseGame();
        }
    }

    private static void changeScene(final Scene scene){
        if(STATE.mHUD == null){
            STATE.mHUD = new HUD();
            Rectangle rect = new Rectangle(RM.CW / 2, RM.CH / 2, RM.CW, RM.CH, RM.VBO);
            rect.setColor(0, 0, 0);
            rect.setAlpha(0);
            STATE.mHUD.attachChild(rect);
        }
        final SmoothCamera camera = (SmoothCamera) RM.ENGINE.getCamera();
        camera.setHUD(STATE.mHUD);
        camera.setZoomFactor(1.1f);
        final IEntity mask = STATE.mHUD.getFirstChild();
        mask.registerEntityModifier(new AlphaModifier(0.2f, 0, 1, new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                RM.ENGINE.setScene(scene);
                mask.registerEntityModifier(new AlphaModifier(0.2f, 1, 0, new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        RM.ENGINE.getCamera().setHUD(null);
                        camera.setZoomFactor(1f);
                    }
                }));
            }
        }));
    }

    public static class State{
        int mCurrentState;
        HUD mHUD;
    }
}
