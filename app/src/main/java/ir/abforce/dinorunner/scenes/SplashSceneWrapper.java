package ir.abforce.dinorunner.scenes;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.util.modifier.IModifier;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.managers.SceneManager;

/**
 * Created by Ali Reza on 5/28/15.
 */
public class SplashSceneWrapper extends SceneWrapper {
    protected static SplashSceneWrapper ourInstance;

    public static Scene build(){
        ourInstance = new SplashSceneWrapper();
        return ourInstance.getScene();
    }

    public static SplashSceneWrapper getInstance(){
        return ourInstance;
    }

    protected SplashSceneWrapper() {
        super();
        SSprite splash = new SSprite(RM.CW / 2, RM.CH / 2, RM.txSplash);
        splash.setVisible(false);
        splash.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(1),
                new AlphaModifier(1, 0, 1, new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                        pItem.setVisible(true);
                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

                    }
                }),
                new DelayModifier(1),
                new AlphaModifier(1, 1, 0, new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        SceneManager.goHomeMenu();
                        disposeScene();
                        RM.unloadSplashSceneResources();
                    }
                })));

        this.mScene.attachChild(splash);
    }
}
