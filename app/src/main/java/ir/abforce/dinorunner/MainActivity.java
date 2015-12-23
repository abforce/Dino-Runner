package ir.abforce.dinorunner;

import android.os.Bundle;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import ir.abforce.dinorunner.helpers.SwarmHelper;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.managers.SceneManager;

/**
 * Created by Ali Reza on 5/20/15.
 */

public class MainActivity extends BaseGameActivity {
    int camWidth;
    int camHeight;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        SwarmHelper.onActivityCreate();
        SwarmHelper.init(this);
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        camWidth = getResources().getDisplayMetrics().widthPixels;
        camHeight = getResources().getDisplayMetrics().heightPixels;

        SmoothCamera smoothCamera = new SmoothCamera(0, 0, camWidth, camHeight, 0, 0, 0.8f);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), smoothCamera);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        engineOptions.getAudioOptions().setNeedsSound(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
        RM.init(this, getEngine(), camWidth, camHeight, getVertexBufferObjectManager());
        RM.loadSplashSceneResources();
        GameConstants.init();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        pOnCreateSceneCallback.onCreateSceneFinished(SceneManager.onCreateScene());
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SwarmHelper.onActivityPause();
    }

    @Override
    public synchronized void onPauseGame() {
        super.onPauseGame();
        SceneManager.onGamePaused();
    }

    @Override
    protected synchronized void onResume() {
        super.onResume();
        SwarmHelper.onActivityResume();
    }

    @Override
    public void onBackPressed() {
        if(SceneManager.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
