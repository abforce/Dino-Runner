package ir.abforce.dinorunner.managers;

/**
 * Created by Ali Reza on 5/20/15.
 */
public class SoundManager {
    private static final SoundManager ourInstance = new SoundManager();

    public static SoundManager getInstance(){
        return ourInstance;
    }

    private SoundManager(){

    }

    public void playJumpSound(){
        RM.sJump.play();
    }

    public void playScoreSound(){
        RM.sScore.play();
    }

    public void playHitSound(){
        RM.sHit.play();
    }
}
