package ir.abforce.dinorunner.generators;

import org.andengine.entity.scene.Scene;
import org.andengine.util.math.MathUtils;

import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.objects.Bird;
import ir.abforce.dinorunner.scenes.GameSceneWrapper;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class BirdGenerator extends GameObjectGenerator<Bird> {

    public BirdGenerator(Scene scene) {
        super(scene);
    }

    @Override
    protected Bird instantiateObject() {
        return new Bird(this.mScene);
    }

    @Override
    protected void populateFirstFrame() {

    }

    @Override
    protected void onHandleNewGeneratedObject(Bird object) {
        if(this.mOnTheSceneGameObjects.size() == 0){
            GameSceneWrapper.getInstance().getObstacleGenerator().setEnabled(false);
        }
    }

    @Override
    protected void onHandleRecycleObject(Bird object) {
        if(this.mOnTheSceneGameObjects.size() == 1){
            GameSceneWrapper.getInstance().getObstacleGenerator().setEnabled(true);
        }
    }

    @Override
    protected void randomiseSettings() {
        boolean medium = this.mTotalElapsed > GameConstants.TIME_MEDIUM && this.mTotalElapsed <= GameConstants.TIME_HARD;
        boolean hard = this.mTotalElapsed > GameConstants.TIME_HARD;

        if(medium){
            this.mRate = MathUtils.random(0.1f, 0.3f);
        }

        if(hard){
            this.mRate = MathUtils.random(0.2f, 0.8f);
        }
    }
}
