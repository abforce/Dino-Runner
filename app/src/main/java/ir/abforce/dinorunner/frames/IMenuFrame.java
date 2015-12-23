package ir.abforce.dinorunner.frames;

import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by Ali Reza on 9/2/15.
 */
public interface IMenuFrame {
    void animateObjectsIn();
    void animateObjectsOut();
    void onMainButtonClicked();
    ITextureRegion getMainButtonTextureRegion();
    void clearObjectsAnimations();
    void cleanUp();
}
