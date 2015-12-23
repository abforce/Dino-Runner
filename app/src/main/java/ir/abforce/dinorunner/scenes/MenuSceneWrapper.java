package ir.abforce.dinorunner.scenes;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.frames.GameOverMenuFrame;
import ir.abforce.dinorunner.frames.HomeMenuFrame;
import ir.abforce.dinorunner.frames.IMenuFrame;
import ir.abforce.dinorunner.frames.PauseMenuFrame;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/6/15.
 */
public class MenuSceneWrapper extends SceneWrapper {
    protected static MenuSceneWrapper ourInstance;

    private static float X_MASK_VISIBLE;
    private static float X_MASK_INVISIBLE;

    private Rectangle mMask;
    private Sprite mSpMainButton;
    private IMenuFrame mMenuFrame;

    public static Scene build(){
        ourInstance = new MenuSceneWrapper();
        return ourInstance.getScene();
    }

    public static MenuSceneWrapper getInstance(){
        return ourInstance;
    }

    protected MenuSceneWrapper(){
        super();
        mScene.setBackgroundEnabled(false);

        X_MASK_VISIBLE = GameConstants.DINO_MARGIN_LEFT + (RM.txDinoNormal.getWidth() * RM.S + RM.CW ) / 2;
        X_MASK_INVISIBLE = (3 * RM.CW - RM.txDinoNormal.getWidth() * RM.S) / 2 - GameConstants.DINO_MARGIN_LEFT;

        mMask = new Rectangle(GameConstants.X_OFF_CAMERA, RM.CH / 2, X_MASK_INVISIBLE - X_MASK_VISIBLE, RM.CH, RM.VBO);
        mMask.setColor(GameConstants.GAME_BG_COLOR);
        mScene.attachChild(mMask);
    }

    public void prepareForHome(){
        cleanUp();
        mMenuFrame = new HomeMenuFrame(this.mScene);
        prepare();
    }

    public void prepareForPause(){
        cleanUp();
        mMenuFrame = new PauseMenuFrame();
        prepare();
        mMask.registerEntityModifier(new MoveXModifier(0.5f, X_MASK_INVISIBLE, X_MASK_VISIBLE));
    }

    public void prepareForGameOver(){
        cleanUp();
        mMenuFrame = new GameOverMenuFrame(this.mScene);
        prepare();
        mMask.registerEntityModifier(new MoveXModifier(0.5f, X_MASK_INVISIBLE, X_MASK_VISIBLE));
    }

    private void prepare(){
        mMenuFrame.animateObjectsIn();
        this.mScene.attachChild((IEntity) mMenuFrame);
        attachMainButton(mMenuFrame.getMainButtonTextureRegion());
    }

    private void cleanUp() {
        if(mMenuFrame != null){
            mMenuFrame.cleanUp();
            this.mScene.detachChild((IEntity) mMenuFrame);
        }
        if(mSpMainButton != null) {
            mSpMainButton.dispose();
            mSpMainButton.detachSelf();
            mSpMainButton = null;
        }
        mMask.setX(X_MASK_VISIBLE);
    }

    private void attachMainButton(ITextureRegion textureRegion){
        mSpMainButton = new SSprite(RM.CW / 2, RM.CH / 2, textureRegion){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionDown()){
                    onButtonHit();
                    return true;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        mSpMainButton.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(1, new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        mSpMainButton.setVisible(true);
                    }
                }),
                new ParallelEntityModifier(
                        new AlphaModifier(0.2f, 0, 1),
                        new ScaleModifier(0.2f, 2 * RM.S, RM.S)))
        );
        mSpMainButton.setVisible(false);
        mScene.registerTouchArea(mSpMainButton);
        mScene.attachChild(mSpMainButton);
    }

    private void onButtonHit(){
        if(mSpMainButton.getEntityModifierCount() > 0){
            mSpMainButton.clearEntityModifiers();
        }
        mSpMainButton.registerEntityModifier(
                new ParallelEntityModifier(
                        new AlphaModifier(0.5f, 1, 0),
                        new ScaleModifier(0.5f, RM.S, 2 * RM.S)));

        if(mMask.getEntityModifierCount() > 0){
            mMask.clearEntityModifiers();
        }
        mMask.registerEntityModifier(new MoveXModifier(0.5f, X_MASK_VISIBLE, X_MASK_INVISIBLE, new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                GameSceneWrapper.getInstance().getScene().clearChildScene();
                mMenuFrame.onMainButtonClicked();
            }
        }));
        mMenuFrame.clearObjectsAnimations();
        mMenuFrame.animateObjectsOut();
    }

    public IMenuFrame getMenuFrame(){
        return mMenuFrame;
    }
}
