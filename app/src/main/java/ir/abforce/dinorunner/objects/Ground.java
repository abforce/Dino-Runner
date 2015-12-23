package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.Scene;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class Ground extends GameObject {

    public Ground(Scene scene) {
        super(scene);
        this.mSprite = new SSprite(0, 0, RM.txGround);
    }

    @Override
    public void reset() {
        super.reset();
        setY(GameConstants.BASE_Y + getHeight() / 2);
    }
}
