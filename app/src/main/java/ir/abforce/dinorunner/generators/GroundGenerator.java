package ir.abforce.dinorunner.generators;

import org.andengine.entity.scene.Scene;

import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.objects.Ground;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class GroundGenerator extends GameObjectGenerator<Ground> {

    public GroundGenerator(Scene scene) {
        super(scene);
        this.mContinuous = true;
        this.mContGap = 0;
    }

    @Override
    protected Ground instantiateObject() {
        return new Ground(this.mScene);
    }

    @Override
    protected void populateFirstFrame() {
        float coveredX = 0;
        while (coveredX < RM.CW){
            Ground ground = this.mPool.obtainPoolItem();
            ground.setX(coveredX + ground.getWidth() / 2);
            ground.attachToScene();
            coveredX += ground.getWidth();
        }
    }
}
