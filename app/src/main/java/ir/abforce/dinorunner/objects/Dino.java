package ir.abforce.dinorunner.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import ir.abforce.dinorunner.custom.SPixelPerfectAnimatedSprite;
import ir.abforce.dinorunner.custom.SPixelPerfectSprite;
import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 5/29/15.
 */
public class Dino extends GameObject{

    private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0, 10f);

    public static final int TEXTURE_STATE_STOPPED = 0;
    public static final int TEXTURE_STATE_RUNNING = 1;
    public static final int TEXTURE_STATE_DUCKING = 2;
    public static final int TEXTURE_STATE_DEAD = 3;

    private Body mBody;
    private PhysicsConnector mPhysicsConnector;
    private PhysicsWorld mPhysicsWorld;

    public Dino(Scene scene, PhysicsWorld physicsWorld) {
        super(scene);
        this.mPhysicsWorld = physicsWorld;
        this.mSprite = new SPixelPerfectSprite(0, 0, RM.txDinoNormal);
        setX(GameConstants.DINO_MARGIN_LEFT + getWidth() / 2);
        setY(GameConstants.BASE_Y + getHeight() / 2);
        this.mTextureState = TEXTURE_STATE_STOPPED;
        this.mSprite.setZIndex(1);
        this.mBody = PhysicsFactory.createBoxBody(physicsWorld, this.mSprite, BodyDef.BodyType.DynamicBody, FIXTURE_DEF);
        this.mPhysicsConnector = new PhysicsConnector(this.mSprite, this.mBody, true, false);
        physicsWorld.registerPhysicsConnector(this.mPhysicsConnector);
    }

    @Override
    public void setTextureState(int state) {
        super.setTextureState(state);
        float x = this.mSprite.getX();
        float y = this.mSprite.getY();

        if(state == TEXTURE_STATE_STOPPED){
            if(this.mIsAttached){
                detachFromScene();
                this.mSprite.dispose();
                this.mSprite = new SPixelPerfectSprite(x, y, RM.txDinoNormal);
                setY(GameConstants.BASE_Y + getHeight() / 2);
                this.mSprite.setZIndex(1);
                attachToScene();
            } else {
                this.mSprite.dispose();
                this.mSprite = new SPixelPerfectSprite(x, y, RM.txDinoNormal);
                setY(GameConstants.BASE_Y + getHeight() / 2);
                this.mSprite.setZIndex(1);
            }
        }

        if(state == TEXTURE_STATE_DEAD){
            if(this.mIsAttached){
                detachFromScene();
                this.mSprite.dispose();
                this.mSprite = new SSprite(x, y, RM.txDinoHit);
                this.mSprite.setZIndex(1);
                attachToScene();
            } else {
                this.mSprite.dispose();
                this.mSprite = new SSprite(x, y, RM.txDinoHit);
                this.mSprite.setZIndex(1);
            }
        }

        if(state == TEXTURE_STATE_RUNNING){
            if(this.mIsAttached){
                detachFromScene();
                this.mSprite.dispose();
                this.mSprite = new SPixelPerfectAnimatedSprite(x, y, RM.txDinoRun);
                setY(GameConstants.BASE_Y + getHeight() / 2);
                ((AnimatedSprite) this.mSprite).animate(100);
                this.mSprite.setZIndex(1);
                attachToScene();
            } else {
                this.mSprite.dispose();
                this.mSprite = new SPixelPerfectAnimatedSprite(x, y, RM.txDinoRun);
                setY(GameConstants.BASE_Y + getHeight() / 2);
                ((AnimatedSprite) this.mSprite).animate(100);
                this.mSprite.setZIndex(1);
            }
        }

        if(state == TEXTURE_STATE_DUCKING){
            if(this.mIsAttached){
                detachFromScene();
                this.mSprite.dispose();
                this.mSprite = new SPixelPerfectAnimatedSprite(x, y, RM.txDinoDuck);
                setY(GameConstants.BASE_Y + getHeight() / 2);
                ((AnimatedSprite) this.mSprite).animate(100);
                this.mSprite.setZIndex(1);
                attachToScene();
            } else {
                this.mSprite.dispose();
                this.mSprite = new SPixelPerfectAnimatedSprite(x, y, RM.txDinoDuck);
                setY(GameConstants.BASE_Y + getHeight() / 2);
                ((AnimatedSprite) this.mSprite).animate(100);
                this.mSprite.setZIndex(1);
            }
        }

        this.mPhysicsWorld.unregisterPhysicsConnector(this.mPhysicsConnector);
        this.mPhysicsWorld.destroyBody(this.mBody);
        this.mBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, this.mSprite, BodyDef.BodyType.DynamicBody, FIXTURE_DEF);
        this.mPhysicsConnector = new PhysicsConnector(this.mSprite, this.mBody, true, false);
        this.mPhysicsWorld.registerPhysicsConnector(this.mPhysicsConnector);
    }

    public Body getBody(){
        return this.mBody;
    }
}
