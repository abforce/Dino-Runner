package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by Ali Reza on 8/6/15.
 */
public class ButtonsController {

    private ButtonJump mButtonJump;
    private ButtonDuck mButtonDuck;

    public ButtonsController(Scene scene, final OnButtonClickListener listener){
        mButtonJump = new ButtonJump(scene, new ControllingButton.ButtonStateChangeListener() {
            @Override
            public void onButtonHeld() {
                listener.onButtonJumpHeld();
            }

            @Override
            public void onButtonReleased() {
                listener.onButtonJumpReleased();
            }
        });
        mButtonDuck = new ButtonDuck(scene, new ControllingButton.ButtonStateChangeListener() {
            @Override
            public void onButtonHeld() {
                listener.onButtonDuckHeld();
            }

            @Override
            public void onButtonReleased() {
                listener.onButtonDuckReleased();
            }
        });

        mButtonJump.attachToScene();
        mButtonDuck.attachToScene();
    }

    public boolean onSceneAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea){
        boolean jumpHandled = mButtonJump.onSceneAreaTouched(pSceneTouchEvent, pTouchArea);
        boolean duckHandled = mButtonDuck.onSceneAreaTouched(pSceneTouchEvent, pTouchArea);
        return  jumpHandled || duckHandled;
    }

    public boolean onSceneTouched(TouchEvent pSceneTouchEvent){
        boolean jumpHandled = mButtonJump.onSceneTouched(pSceneTouchEvent);
        boolean duckHandled = mButtonDuck.onSceneTouched(pSceneTouchEvent);
        return  jumpHandled || duckHandled;
    }

    public interface OnButtonClickListener{
        void onButtonJumpHeld();
        void onButtonJumpReleased();
        void onButtonDuckHeld();
        void onButtonDuckReleased();
    }

}
