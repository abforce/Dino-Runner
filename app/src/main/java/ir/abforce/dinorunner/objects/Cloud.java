package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.Scene;
import org.andengine.util.math.MathUtils;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class Cloud extends GameObject {
    public Cloud(Scene scene) {
        super(scene);
        this.mSprite = new SSprite(0, 0, RM.txCloud);
    }

    @Override
    public void reset() {
        super.reset();
        float y = 0;
        switch (MathUtils.random(0, 3)){
            case 0:
                y = RM.CH - (RM.CH - GameConstants.BASE_Y) / 2;
                break;
            case 1:
                y = RM.CH - (RM.CH - GameConstants.BASE_Y) / 3;
                break;
            case 2:
                y = RM.CH - (RM.CH - GameConstants.BASE_Y) / 4;
                break;
            case 3:
                y = RM.CH - (RM.CH - GameConstants.BASE_Y) / 5;
                break;
        }
        setY(y);
    }
}
