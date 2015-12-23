package ir.abforce.dinorunner.objects;

import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseCubicOut;
import org.andengine.util.modifier.ease.EaseQuadInOut;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/31/15.
 */
public class MainMenuButton extends SSprite {
    private static final float BOUNCE_INTERVAL = 5f;
    private Sprite mSpIcon;
    private boolean mBouncyIcon;
    private float mElapsed;
    private boolean mDown = false;
    private Runnable mCallback;

    public MainMenuButton(float pX, float pY, CharSequence pLabel, ITextureRegion pIconTexture, Runnable callback) {
        this(pX, pY, pLabel, pIconTexture, callback, false, false);
    }

    public MainMenuButton(float pX, float pY, CharSequence pLabel, ITextureRegion pIconTexture, Runnable callback, boolean pBouncyIcon) {
        this(pX, pY, pLabel, pIconTexture, callback, pBouncyIcon, false);
    }

    public MainMenuButton(float pX, float pY, CharSequence pLabel, ITextureRegion pIconTexture, Runnable callback, boolean pBouncyIcon, boolean flipped) {
        super(pX, pY, RM.txButtonBg);
        mCallback = callback;
        float x, y;

        // Icon
        if(flipped){
            x = pIconTexture.getWidth() / 2;
        } else {
            x = getWidth() - pIconTexture.getWidth() / 2;
        }
        y = getHeight() / 2;
        mSpIcon = new Sprite(x, y, pIconTexture, RM.VBO);
        this.attachChild(mSpIcon);

        // Label
        if(flipped){
            x = pIconTexture.getWidth() + (getWidth() - pIconTexture.getWidth()) / 2 - 3;
        } else {
            x = (getWidth() - pIconTexture.getWidth()) / 2 + 3;
        }
        y = getHeight() / 2;
        Text label = new Text(x, y, RM.fMain, pLabel, RM.VBO);
        label.setColor(0.33f, 0.33f, 0.33f);
        this.attachChild(label);

        this.mBouncyIcon = pBouncyIcon;
        setFlippedHorizontal(flipped);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if(mBouncyIcon){
            mElapsed += pSecondsElapsed;
            if(mElapsed >= BOUNCE_INTERVAL){
                mElapsed = 0;
                bounceIcon();
            }
        }
    }

    public void bounceIcon(){
        float yBase = mSpIcon.getY();
        float yHigh = yBase + 20 * RM.S;
        mSpIcon.registerEntityModifier(new ParallelEntityModifier(
                new RotationModifier(0.7f, 0, 360, EaseQuadInOut.getInstance()),
                new SequenceEntityModifier(
                        new MoveYModifier(0.35f, yBase, yHigh, EaseCubicOut.getInstance()),
                        new MoveYModifier(0.35f, yHigh, yBase, EaseBounceOut.getInstance())
                )
        ));
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(pSceneTouchEvent.isActionDown()){
            mDown = true;
            applyEffectsDown();
            return true;
        }
        if(mDown && pSceneTouchEvent.isActionUp()){
            mDown = false;
            applyEffectsUp();
            mCallback.run();
            return true;
        }
        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    public boolean onSceneTouched(TouchEvent pSceneTouchEvent){
        if(pSceneTouchEvent.isActionUp() && mDown){
            mDown = false;
            applyEffectsUp();
        }
        return false;
    }

    private void applyEffectsDown(){
        setColor(0.6f, 0.6f, 0.6f);
    }

    private void applyEffectsUp(){
        setColor(1f, 1f, 1f);
    }
}
