package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.TextureRegion;

import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/6/15.
 */
public class ButtonDuck extends ControllingButton {

    public ButtonDuck(Scene scene, ButtonStateChangeListener listener) {
        super(scene, listener);
    }

    @Override
    public TextureRegion getTexture() {
        return RM.txDown;
    }

    @Override
    public float getButtonX() {
        return RM.CW - 3 * getWidth() / 2 - 2 * GameConstants.BUTTON_MARGIN;
    }

    @Override
    public float getButtonY() {
        return getHeight() / 2 + GameConstants.BUTTON_MARGIN;
    }
}
