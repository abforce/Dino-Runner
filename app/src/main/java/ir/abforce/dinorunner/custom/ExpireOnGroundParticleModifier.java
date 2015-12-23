package ir.abforce.dinorunner.custom;

import org.andengine.entity.Entity;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.modifier.IParticleModifier;

/**
 * Created by Ali Reza on 9/1/15.
 */
public class ExpireOnGroundParticleModifier implements IParticleModifier<Entity> {
    private float mBaseY;

    public ExpireOnGroundParticleModifier(float pBaseY) {
        this.mBaseY = pBaseY;
    }

    @Override
    public void onUpdateParticle(Particle<Entity> pParticle) {
        if(pParticle.getEntity().getY() <= this.mBaseY){
            pParticle.setExpired(true);
        }
    }

    @Override
    public void onInitializeParticle(Particle<Entity> pParticle) {

    }
}
