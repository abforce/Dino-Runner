package ir.abforce.dinorunner.generators;

import org.andengine.entity.scene.Scene;

import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.objects.Cloud;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class CloudGenerator extends GameObjectGenerator<Cloud> {

    public CloudGenerator(Scene scene) {
        super(scene);
    }

    @Override
    protected Cloud instantiateObject() {
        return new Cloud(this.mScene);
    }

    @Override
    protected void populateFirstFrame() {
        Cloud cloud;

        cloud = this.mPool.obtainPoolItem();
        cloud.setX(RM.CW / 4 + 20 * RM.S);
        cloud.attachToScene();

        cloud = this.mPool.obtainPoolItem();
        cloud.setX(RM.CW / 2);
        cloud.attachToScene();

        cloud = this.mPool.obtainPoolItem();
        cloud.setX(2 * RM.CW / 3);
        cloud.attachToScene();

        cloud = this.mPool.obtainPoolItem();
        cloud.setX(5 * RM.CW / 6);
        cloud.attachToScene();
    }
}
