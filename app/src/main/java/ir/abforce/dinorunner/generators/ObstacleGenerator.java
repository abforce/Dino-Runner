package ir.abforce.dinorunner.generators;

import org.andengine.entity.scene.Scene;
import org.andengine.util.math.MathUtils;

import ir.abforce.dinorunner.managers.GameManager;
import ir.abforce.dinorunner.objects.Obstacle;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class ObstacleGenerator extends GameObjectGenerator<Obstacle> {

    private int cont = 0;

    public ObstacleGenerator(Scene scene) {
        super(scene);
    }

    @Override
    protected Obstacle instantiateObject() {
        return new Obstacle(this.mScene);
    }

    @Override
    protected void populateFirstFrame() {

    }

    @Override
    protected void randomiseSettings() {
        int diff = GameManager.getInstance().getGameDifficulty();

        switch (diff){
            case GameManager.DIFFICULTY_EASY:
                this.mRate = MathUtils.random(0.4f, 0.8f);
                this.mContinuous = MathUtils.random(0, 5) == 0;
                break;

            case GameManager.DIFFICULTY_MEDIUM:
                this.mRate = MathUtils.random(0.8f, 1.5f);
                this.mContinuous = MathUtils.random(0, 4) == 0;
                break;

            case GameManager.DIFFICULTY_HARD:
                this.mRate = MathUtils.random(1.3f, 1.9f);
                this.mContinuous = MathUtils.random(0, 2) == 0;
                break;
        }

        if(this.mContinuous){
            cont += 1;
        } else {
            cont = 0;
        }

        if(diff == GameManager.DIFFICULTY_EASY && cont == 1 ||
                diff == GameManager.DIFFICULTY_MEDIUM && cont == 2 ||
                diff == GameManager.DIFFICULTY_HARD && cont == 3){
            cont = 0;
            this.mContinuous = false;
        }
    }

    @Override
    protected void onHandleNewGeneratedObject(Obstacle obstacle) {
        obstacle.shuffleTextures();
    }
}
