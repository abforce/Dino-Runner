package ir.abforce.dinorunner.objects;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.managers.SoundManager;
import ir.abforce.dinorunner.scenes.GameSceneWrapper;

/**
 * Created by Ali Reza on 8/6/15.
 */
public class DinoController {

    private static final int STATE_STOP = 0;
    private static final int STATE_JUMP = 1;
    private static final int STATE_RUN = 2;
    private static final int STATE_DUCK = 3;
    private static final int STATE_DEAD = 4;

    private Dino mDino;
    private boolean mJumpFinished = true;
    private float mDinoRunningY;
    private int mState = STATE_STOP;

    public DinoController(Scene scene, PhysicsWorld physicsWorld){
        this.mDino = new Dino(scene, physicsWorld);
        this.mDino.attachToScene();
    }

    public void onGameStep(){
        float y = this.mDino.getY();
        float vy = this.mDino.getBody().getLinearVelocity().y;
        if(!mJumpFinished && y <= mDinoRunningY + 1f * RM.S && vy <= 0){
            mJumpFinished = true;
            onJumpFinished();
        }
    }

    private void onJumpFinished(){
        if(this.mState == STATE_JUMP){
            run();
        }
        if(this.mState == STATE_DUCK){
            this.mDino.setTextureState(Dino.TEXTURE_STATE_DUCKING);
        }
    }

    public void run(){
        if(this.mState == STATE_RUN){
            return;
        }
        this.mDino.setTextureState(Dino.TEXTURE_STATE_RUNNING);
        this.mState = STATE_RUN;
    }

    public void jump(){
        if(this.mState == STATE_JUMP){
            return;
        }
        this.mDino.setTextureState(Dino.TEXTURE_STATE_STOPPED);
        float v = GameSceneWrapper.getInstance().getJumpVelocity();
        this.mDino.getBody().setLinearVelocity(0, v);
        this.mJumpFinished = false;
        this.mDinoRunningY = this.mDino.getY();
        this.mState = STATE_JUMP;
        SoundManager.getInstance().playJumpSound();
    }

    public void duckStart(){
        if(this.mState == STATE_RUN){
            this.mDino.setTextureState(Dino.TEXTURE_STATE_DUCKING);
        }
        if(this.mState == STATE_JUMP){
            float v = GameSceneWrapper.getInstance().getJumpVelocity();
            this.mDino.getBody().setLinearVelocity(0, - v);
        }
        this.mState = STATE_DUCK;
    }

    public void duckEnd(){
        run();
    }

    public void die(){
        this.mState = STATE_DEAD;
        this.mDino.setTextureState(Dino.TEXTURE_STATE_DEAD);
        SoundManager.getInstance().playHitSound();
    }

    public boolean collidesWith(GameObject object){
        return this.mDino.collidesWith(object);
    }
}
