package ir.abforce.dinorunner.helpers;

import android.app.Activity;
import android.widget.Toast;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.SwarmLeaderboard;
import com.swarmconnect.SwarmLoginManager;
import com.swarmconnect.delegates.SwarmLoginListener;

import ir.abforce.dinorunner.managers.GameConstants;
import ir.abforce.dinorunner.managers.RM;

/**
 * Created by Ali Reza on 9/3/15.
 */
public class SwarmHelper {
    private static final int REQUEST_NONE = 0;
    private static final int REQUEST_SHOW_LEADER_BOARD = 1;
    private static final int REQUEST_SUBMIT_SCORE = 2;
    private static final int REQUEST_SHOW_DASHBOARD = 3;

    private static Activity activity;
    private static int request = REQUEST_NONE;
    private static int score_to_be_submitted;

    private static final SwarmLoginListener SWARM_LOGIN_LISTENER = new SwarmLoginListener() {
        @Override
        public void loginStarted() {

        }

        @Override
        public void loginCanceled() {

        }

        @Override
        public void userLoggedIn(SwarmActiveUser swarmActiveUser) {
            if(request == REQUEST_SHOW_LEADER_BOARD){
                showLeaderBoard();
            }
            if(request == REQUEST_SUBMIT_SCORE){
                submitScore(score_to_be_submitted);
            }
            if(request == REQUEST_SHOW_DASHBOARD){
                showDashboard();
            }
        }

        @Override
        public void userLoggedOut() {

        }
    };

    public static void init(Activity activity){
        SwarmHelper.activity = activity;
        SwarmLoginManager.addLoginListener(SWARM_LOGIN_LISTENER);
    }

    public static void onActivityPause(){
        Swarm.setInactive(activity);
    }

    public static void onActivityResume(){
        Swarm.setActive(activity);
    }

    public static void onActivityCreate(){
        Swarm.setActive(activity);
    }

    public static void showLeaderBoard(){
        if(Swarm.isInitialized()){
            if(Swarm.isLoggedIn()) {
                SwarmLeaderboard.showLeaderboard(GameConstants.SWARM_LEADERBOARD_ID, SwarmLeaderboard.DateRange.ALL);
            } else {
                toast("You'll need an Internet connection");
                request = REQUEST_SHOW_LEADER_BOARD;
                Swarm.showLogin();
            }
        } else {
            toast("You'll need an Internet connection");
            request = REQUEST_SHOW_LEADER_BOARD;
            Swarm.init(activity, GameConstants.SWARM_APP_ID, GameConstants.SWARM_APP_KEY);
        }
    }

    public static void submitScore(int score){
        if(Swarm.isInitialized()){
            if(Swarm.isLoggedIn()) {
                SwarmLeaderboard.submitScore(GameConstants.SWARM_LEADERBOARD_ID, score);
            } else {
                toast("You'll need an Internet connection");
                request = REQUEST_SUBMIT_SCORE;
                score_to_be_submitted = score;
                Swarm.showLogin();
            }
        } else {
            toast("You'll need an Internet connection");
            request = REQUEST_SUBMIT_SCORE;
            score_to_be_submitted = score;
            Swarm.init(activity, GameConstants.SWARM_APP_ID, GameConstants.SWARM_APP_KEY);
        }
    }

    public static void showDashboard(){
        if(Swarm.isInitialized()){
            if(Swarm.isLoggedIn()) {
                Swarm.showDashboard();
            } else {
                toast("You'll need an Internet connection");
                request = REQUEST_SHOW_DASHBOARD;
                Swarm.showLogin();
            }
        } else {
            toast("You'll need an Internet connection");
            request = REQUEST_SHOW_DASHBOARD;
            Swarm.init(activity, GameConstants.SWARM_APP_ID, GameConstants.SWARM_APP_KEY);
        }
    }

    private static void toast(final String text){
        RM.ACTIVITY.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RM.CONTEXT, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}