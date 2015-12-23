package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/5/15.
 */
@SuppressWarnings("deprecation")
public abstract class GameObject {

    protected Scene mScene;
    protected Sprite mSprite;
    protected int mTextureState;
    protected boolean mIsAttached = false;

    public GameObject(Scene scene){
        this.mScene = scene;
    }

    public void setX(float x){
        this.mSprite.setX(x);
    }

    public float getX(){
        return this.mSprite.getX();
    }

    public void setY(float y){
        this.mSprite.setY(y);
    }

    public float getY(){
        return this.mSprite.getY();
    }

    public float getWidth(){
        return this.mSprite.getWidthScaled();
    }

    public float getHeight(){
        return this.mSprite.getHeightScaled();
    }

    public boolean isOutOfScene(){
        return getX() + getWidth() / 2 < 0;
    }

    public float getMarginRight(){
        return RM.CW - getX() - getWidth() / 2;
    }

    public void decreaseXBy(float by){
        setX(getX() - by);
    }

    public void attachToScene(){
        if(this.mIsAttached){
            return;
        }
        this.mScene.attachChild(mSprite);
        this.mScene.sortChildren();
        this.mIsAttached = true;
    }

    public void detachFromScene(){
        mScene.detachChild(mSprite);
        this.mIsAttached = false;
    }

    public void reset(){
        setX(RM.CW + getWidth() / 2);
    }

    public void shuffleTextures(){

    }

    public void setTextureState(int state){
        this.mTextureState = state;
    }

    public boolean collidesWith(GameObject object){
        return this.mSprite.collidesWith(object.mSprite);
    }
}
