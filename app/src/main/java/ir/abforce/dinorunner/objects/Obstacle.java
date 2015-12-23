package ir.abforce.dinorunner.objects;

import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegion;

import org.andengine.entity.scene.Scene;
import org.andengine.util.math.MathUtils;

import ir.abforce.dinorunner.custom.SPixelPerfectSprite;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/5/15.
 */
public class Obstacle extends GameObject {

    public Obstacle(Scene scene) {
        super(scene);
        this.mSprite = new SPixelPerfectSprite(0, 0, RM.txCactus1);
        setY(GameConstants.BASE_Y + getHeight() / 2);
    }

    @Override
    public void shuffleTextures() {
        int idx = MathUtils.random(1, 6);
        PixelPerfectTextureRegion tx = null;
        switch (idx){
            case 1:
                tx = RM.txCactus1;
                break;
            case 2:
                tx = RM.txCactus2;
                break;
            case 3:
                tx = RM.txCactus3;
                break;
            case 4:
                tx = RM.txCactus4;
                break;
            case 5:
                tx = RM.txCactus5;
                break;
            case 6:
                tx = RM.txCactus6;
                break;
        }
        float x = this.mSprite.getX();
        float y = this.mSprite.getY();

        if(this.mIsAttached){
            detachFromScene();
            this.mSprite.dispose();
            this.mSprite = new SPixelPerfectSprite(x, y, tx);
            attachToScene();
        } else {
            this.mSprite.dispose();
            this.mSprite = new SPixelPerfectSprite(x, y, tx);
        }

        setY(GameConstants.BASE_Y + getHeight() / 2);
    }

}
