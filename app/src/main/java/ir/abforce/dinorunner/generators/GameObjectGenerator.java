package ir.abforce.dinorunner.generators;

import org.andengine.entity.scene.Scene;
import org.andengine.util.adt.pool.GenericPool;

import java.util.ArrayList;
import java.util.List;

import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.objects.GameObject;

/**
 * Created by Ali Reza on 8/5/15.
 */
public abstract class GameObjectGenerator<T extends GameObject> {

    protected Scene mScene;
    protected GameObjectPool mPool;
    protected List<T> mOnTheSceneGameObjects;
    protected float mRate;
    protected float mSpeed;
    protected boolean mEnabled;
    protected float mGenerateInterval = 0;
    protected float mTotalElapsed = 0;
    protected boolean mContinuous = false;
    protected float mContGap;

    public GameObjectGenerator(Scene scene){
        this.mScene = scene;
        this.mPool = this.new GameObjectPool();
        this.mOnTheSceneGameObjects = new ArrayList<T>();
        this.mContGap = 3 * RM.S;
    }

    public void init(){
        if(this.mEnabled){
            populateFirstFrame();
        }
    }

    public void onGameStep(float elapsed){
        mTotalElapsed += elapsed;
        mGenerateInterval += elapsed;

        // Move objects
        for(T object : this.mOnTheSceneGameObjects){
            object.decreaseXBy(this.mSpeed);
        }

        // Recycle off-screen objects
        for(int i = 0; i < this.mOnTheSceneGameObjects.size(); i += 1){
            T object = this.mOnTheSceneGameObjects.get(i);
            if(object.isOutOfScene()){
                this.mPool.recyclePoolItem(object);
                i -= 1;
            }
        }

        if(this.mEnabled) {
            if (this.mContinuous) {
                if (this.mOnTheSceneGameObjects.size() > 0) {
                    T lastObject = this.mOnTheSceneGameObjects.get(this.mOnTheSceneGameObjects.size() - 1);
                    if (lastObject.getMarginRight() >= this.mContGap) {
                        T obj = this.mPool.obtainPoolItem();
                        obj.decreaseXBy(lastObject.getMarginRight() - this.mContGap);
                        obj.attachToScene();
                        randomiseSettings();
                    }
                } else {
                    generateNewObject();
                }
            } else {
                if (mGenerateInterval >= 1f / mRate) {
                    mGenerateInterval = 0;
                    generateNewObject();
                }
            }
        }
    }

    protected void generateNewObject(){
        this.mPool.obtainPoolItem().attachToScene();
        randomiseSettings();
    }

    public void setInitialGenerateRate(float rate){
        this.mRate = rate;
    }

    public void setSpeed(float speed){
        this.mSpeed = speed;
    }

    public void setEnabled(boolean enabled){
        this.mEnabled = enabled;
    }

    public List<T> getOnTheSceneGameObjects(){
        return this.mOnTheSceneGameObjects;
    }

    protected abstract T instantiateObject();
    protected abstract void populateFirstFrame();

    protected void randomiseSettings(){}

    protected void onHandleNewGeneratedObject(T object){}
    protected void onHandleRecycleObject(T object){}

    protected class GameObjectPool extends GenericPool<T>{

        @Override
        protected T onAllocatePoolItem() {
            return instantiateObject();
        }

        @Override
        protected void onHandleObtainItem(T pItem) {
            pItem.reset();
            onHandleNewGeneratedObject(pItem);
            GameObjectGenerator.this.mOnTheSceneGameObjects.add(pItem);
        }

        @Override
        protected void onHandleRecycleItem(T pItem) {
            onHandleRecycleObject(pItem);
            GameObjectGenerator.this.mOnTheSceneGameObjects.remove(pItem);
        }
    }

}
