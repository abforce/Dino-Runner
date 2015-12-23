package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.math.MathUtils;

import ir.abforce.dinorunner.custom.SPixelPerfectAnimatedSprite;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class Bird extends GameObject {
    public Bird(Scene scene) {
        super(scene);
        this.mSprite = new SPixelPerfectAnimatedSprite(0, 0, RM.txBird);
        ((AnimatedSprite) this.mSprite).animate(180);
    }

    @Override
    public void reset() {
        super.reset();
        float h = 0;
        switch (MathUtils.random(0, 2)){
            case 0:
                h = RM.txGround.getHeight() + RM.txBird.getHeight() / 2 - 10;
                break;
            case 1:
                h = RM.txGround.getHeight() + RM.txBird.getHeight() / 2 + 40;
                break;
            case 2:
                h = RM.txGround.getHeight() + RM.txDinoNormal.getHeight() + RM.txBird.getHeight() / 2;
                break;
        }
        setY(GameConstants.BASE_Y + h * RM.S);
    }
}
