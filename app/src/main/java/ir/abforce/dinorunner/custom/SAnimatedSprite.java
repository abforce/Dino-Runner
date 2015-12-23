package ir.abforce.dinorunner.custom;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 5/28/15.
 */
public class SAnimatedSprite extends AnimatedSprite {
    public SAnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion) {
        super(pX, pY, pTiledTextureRegion, RM.VBO);
        setScale(RM.S);
    }
}
