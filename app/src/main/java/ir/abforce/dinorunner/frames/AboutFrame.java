package ir.abforce.dinorunner.frames;

import android.opengl.GLES20;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.BatchedPseudoSpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.TickerText;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;

import ir.abforce.dinorunner.custom.ExpireOnGroundParticleModifier;
import ir.abforce.dinorunner.custom.RegisterXSwingEntityModifierInitializer;
import ir.abforce.dinorunner.custom.SAnimatedSprite;
import ir.abforce.dinorunner.custom.SSprite;
import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 8/31/15.
 */
@SuppressWarnings({"deprecation", "SpellCheckingInspection"})
public class AboutFrame extends Rectangle {

    private static float BASE_Y;

    public AboutFrame(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight, RM.VBO);
        this.setColor(0.89f, 0.89f, 0.89f);

        Line line = new Line(0, getHeight(), getWidth(), getHeight(), 5 * RM.S, RM.VBO);
        line.setColor(0.33f, 0.33f, 0.33f);
        this.attachChild(line);
        BASE_Y = 15 * RM.S;
        prepareLandscape();
        prepareTexts();
    }

    private void prepareLandscape(){
        float x, y;

        // Ground
        float coveredX = 0;
        while (coveredX < getWidth()){
            x = coveredX + RM.txGround.getWidth() * RM.S / 2;
            y = BASE_Y + RM.txGround.getHeight() * RM.S / 2;
            SSprite ground = new SSprite(x, y, RM.txGround);
            this.attachChild(ground);
            coveredX += ground.getWidthScaled();
        }

        // Cactus
        x = getWidth() - RM.txCactus7.getWidth() * RM.S / 2 - 100 * RM.S;
        y = BASE_Y + RM.txCactus7.getHeight() * RM.S / 2;
        SSprite cactus = new SSprite(x, y, RM.txCactus7);
        this.attachChild(cactus);

        // Logo
        x = getWidth() / 2;
        y = BASE_Y + 16 * RM.S + RM.txLogo.getHeight() * RM.S / 2;
        SSprite logo = new SSprite(x, y, RM.txLogo);
        this.attachChild(logo);

        // Dino
        y = BASE_Y + RM.txDinoRun.getHeight() * RM.S / 2;
        SAnimatedSprite dino = new SAnimatedSprite(GameConstants.X_OFF_CAMERA, y, RM.txDinoRun);
        float fromX = - RM.txDinoRun.getWidth() * RM.S / 2;
        final float toX = getWidth() / 2 - (RM.txLogo.getWidth() + RM.txDinoRun.getWidth()) * RM.S / 2 - 20 * RM.S;
        dino.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(1),
                new MoveXModifier(1.5f, fromX, toX, new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        pItem.detachSelf();
                        pItem.dispose();
                        SSprite dinoNormal = new SSprite(toX, BASE_Y + RM.txDinoNormal.getHeight() * RM.S / 2, RM.txDinoNormal);
                        AboutFrame.this.attachChild(dinoNormal);
                    }
                })
        ));
        dino.animate(175);
        this.attachChild(dino);

        // Snow fall
        x = getWidth() / 2;
        y = getHeight();
        RectangleParticleEmitter emitter = new RectangleParticleEmitter(x, y, getWidth(), 0.1f);
        BatchedPseudoSpriteParticleSystem particleSystem = new BatchedPseudoSpriteParticleSystem(emitter, 4, 6, 200, RM.txFlask, RM.VBO);
        particleSystem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        particleSystem.addParticleInitializer(new VelocityParticleInitializer<Entity>(- 3 * RM.S, 3 * RM.S, - 20 * RM.S, -40 * RM.S));
        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Entity>(-3, 3, -3, -5));
        particleSystem.addParticleInitializer(new ScaleParticleInitializer<Entity>(0.6f * RM.S, 0.9f * RM.S));
        particleSystem.addParticleInitializer(new RegisterXSwingEntityModifierInitializer<Entity>(10f, 0f, (float) Math.PI * 8, 3f * RM.S, 25f * RM.S, true));

        particleSystem.addParticleModifier(new AlphaParticleModifier<Entity>(6f, 8f, 1.0f, 0.0f));
        particleSystem.addParticleModifier(new ExpireOnGroundParticleModifier(BASE_Y + 18 * RM.S));
        this.attachChild(particleSystem);
    }

    private void prepareTexts(){
        String str = "Dino Runner v1.0 (C)\n\n" +
                "Credits to The Chromium Authors\n" +
                "Programmer: Ali Reza Barkhordari\n" +
                "Engine Powered by: AndEngine GLES2.0\n" +
                "Back-end Powered by: Swarm\n\n" +
                "2015";
        final TickerText text = new TickerText(getWidth() / 2, getHeight() / 2 + RM.txLogo.getHeight() * RM.S / 2, RM.fMain, str, new TickerText.TickerTextOptions(HorizontalAlign.CENTER, 10), RM.VBO);
        text.setScale(RM.S);
        text.setColor(0.33f, 0.33f, 0.33f);
        registerUpdateHandler(new TimerHandler(1, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                AboutFrame.this.attachChild(text);
                unregisterUpdateHandler(pTimerHandler);
            }
        }));

    }
}
