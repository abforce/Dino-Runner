package ir.abforce.dinorunner.custom;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.level.LevelLoader;

import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 5/28/15.
 */
public class SSprite extends Sprite {

    public SSprite(float pX, float pY, ITextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion, RM.VBO);
        setScale(RM.S);
    }
}
