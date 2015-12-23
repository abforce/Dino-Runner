package ir.abforce.dinorunner.custom;

import com.makersf.andengine.extension.collisions.entity.sprite.PixelPerfectAnimatedSprite;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;

import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 9/4/15.
 */
public class SPixelPerfectAnimatedSprite extends PixelPerfectAnimatedSprite {
    public SPixelPerfectAnimatedSprite(float pX, float pY, PixelPerfectTiledTextureRegion pTiledTextureRegion) {
        super(pX, pY, pTiledTextureRegion, RM.VBO);
        setScale(RM.S);
    }
}
