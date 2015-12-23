package ir.abforce.dinorunner.frames;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.ease.EaseElasticOut;

import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.GameManager;
import ir.abforce.dinorunner.managers.RM;
import ir.abforce.dinorunner.managers.SceneManager;

/**
 * Created by Ali Reza on 9/2/15.
 */
public class PauseMenuFrame extends Entity implements IMenuFrame {
    private SSprite SpGamePaused;

    public PauseMenuFrame() {
        float x, y;
        String text;
        int score = GameManager.getInstance().getCurrentScore();
        int hiScore = GameManager.getInstance().getHiScore();

        // Game Paused
        x = RM.CW / 2;
        SpGamePaused = new SSprite(x, GameConstants.Y_OFF_CAMERA, RM.txGamePaused);
        this.attachChild(SpGamePaused);

        // Text scores
        x = RM.CW / 2;
        y = 60 * RM.S;
        text = String.format("Score: %d\n\nHigh Score: %d", score, hiScore);
        Text textScores = new Text(x, y, RM.fMain, text, new TextOptions(HorizontalAlign.CENTER), RM.VBO);
        textScores.setColor(0.33f, 0.33f, 0.33f);
        textScores.setScale(RM.S);
        this.attachChild(textScores);
    }

    @Override
    public void animateObjectsIn() {
        float fromY, toY;
        fromY = RM.CH + RM.txGamePaused.getHeight() * RM.S / 2;
        toY = RM.CH - RM.txGamePaused.getHeight() * RM.S / 2 - 30 * RM.S;
        SpGamePaused.registerEntityModifier(new MoveYModifier(0.5f, fromY, toY, EaseElasticOut.getInstance()));
    }

    @Override
    public void animateObjectsOut() {
        float fromY, toY;
        fromY = SpGamePaused.getY();
        toY = RM.CH + RM.txGamePaused.getHeight() * RM.S / 2;
        SpGamePaused.registerEntityModifier(new MoveYModifier(0.5f, fromY, toY));
    }

    @Override
    public void onMainButtonClicked() {
        SceneManager.runGame();
    }

    @Override
    public ITextureRegion getMainButtonTextureRegion() {
        return RM.txPlay;
    }

    @Override
    public void clearObjectsAnimations() {
        if(SpGamePaused.getEntityModifierCount() > 0){
            SpGamePaused.clearEntityModifiers();
        }
    }

    @Override
    public void cleanUp() {
        this.dispose();
    }
}
