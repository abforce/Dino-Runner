package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.TextureRegion;

import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/6/15.
 */
public class ButtonJump extends ControllingButton {

    public ButtonJump(Scene scene, ButtonStateChangeListener listener) {
        super(scene, listener);
    }

    @Override
    public TextureRegion getTexture() {
        return RM.txUp;
    }

    @Override
    public float getButtonX() {
        return RM.CW - getWidth() / 2 - GameConstants.BUTTON_MARGIN;
    }

    @Override
    public float getButtonY() {
        return getHeight() / 2 + GameConstants.BUTTON_MARGIN;
    }
}
