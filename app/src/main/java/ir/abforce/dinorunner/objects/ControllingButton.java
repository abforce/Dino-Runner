package ir.abforce.dinorunner.objects;

import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/6/15.
 */
public abstract class ControllingButton extends GameObject {

    private ButtonStateChangeListener mListener;
    private boolean mDown = false;

    public ControllingButton(Scene scene, final ButtonStateChangeListener listener) {
        super(scene);
        this.mListener = listener;
        this.mSprite = new SSprite(0, 0, getTexture());
        setX(getButtonX());
        setY(getButtonY());
        scene.registerTouchArea(this.mSprite);
    }

    public boolean onSceneAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea){
        if(pTouchArea == this.mSprite) {
            if (pSceneTouchEvent.isActionDown()) {
                this.mListener.onButtonHeld();
                applyEffectsDown();
                this.mDown = true;
            }
            if (pSceneTouchEvent.isActionUp()) {
                if(this.mDown) {
                    this.mListener.onButtonReleased();
                    applyEffectsUp();
                    this.mDown = false;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean onSceneTouched(TouchEvent pSceneTouchEvent){
        if(this.mDown && pSceneTouchEvent.isActionUp()){
            this.mListener.onButtonReleased();
            applyEffectsUp();
            this.mDown = false;
        }
        return false;
    }

    public void applyEffectsDown(){
        this.mSprite.registerEntityModifier(new ScaleModifier(0.08f, RM.S, RM.S * 0.75f));
    }

    public void applyEffectsUp(){
        this.mSprite.registerEntityModifier(new ScaleModifier(0.08f, RM.S * 0.75f, RM.S));
    }

    public abstract TextureRegion getTexture();
    public abstract float getButtonX();
    public abstract float getButtonY();

    public interface ButtonStateChangeListener{
        void onButtonHeld();
        void onButtonReleased();
    }
}
