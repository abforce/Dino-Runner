package ir.abforce.dinorunner.custom;

import com.makersf.andengine.extension.collisions.entity.sprite.PixelPerfectSprite;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegion;

import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 9/4/15.
 */
public class SPixelPerfectSprite extends PixelPerfectSprite {
    public SPixelPerfectSprite(float pX, float pY, PixelPerfectTextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion, RM.VBO);
        setScale(RM.S);
    }
}
