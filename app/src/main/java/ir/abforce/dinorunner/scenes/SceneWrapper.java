package ir.abforce.dinorunner.scenes;

import org.andengine.entity.scene.Scene;

/**
 * Created by Ali Reza on 8/30/15.
 */
public abstract class SceneWrapper {
    protected Scene mScene;

    protected SceneWrapper(){
        this.mScene = new Scene();
    }

    public Scene getScene(){
        return this.mScene;
    }

    public void disposeScene(){
        this.mScene.dispose();
    }
}
