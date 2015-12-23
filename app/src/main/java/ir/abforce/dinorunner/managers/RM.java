package ir.abforce.dinorunner.managers;

import android.content.Context;
import android.graphics.Color;

import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegion;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegionFactory;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

/**
 * Created by Ali Reza on 5/20/15.
 */
public class RM {

    private static final float DEV_CAM_WIDTH = 800;
    private static final float DEV_CAM_HEIGHT = 444;

    public static Engine ENGINE;
    public static Context CONTEXT;
    public static BaseGameActivity ACTIVITY;
    public static VertexBufferObjectManager VBO;
    public static float CW;
    public static float CH;
    public static float S;
    public static float SH;

    // ===== SCENE: Splash =====
    public static TextureRegion txSplash;

    // ===== SCENE: Game =====
    public static TiledTextureRegion txDinoBlink;

    public static PixelPerfectTiledTextureRegion txDinoDuck;
    public static PixelPerfectTiledTextureRegion txDinoRun;
    public static PixelPerfectTiledTextureRegion txBird;

    public static PixelPerfectTextureRegion txDinoNormal;
    public static PixelPerfectTextureRegion txCactus1;
    public static PixelPerfectTextureRegion txCactus2;
    public static PixelPerfectTextureRegion txCactus3;
    public static PixelPerfectTextureRegion txCactus4;
    public static PixelPerfectTextureRegion txCactus5;
    public static PixelPerfectTextureRegion txCactus6;

    public static TextureRegion txDinoHit;
    public static TextureRegion txGround;
    public static TextureRegion txCloud;
    public static TextureRegion txCactus7;
    public static TextureRegion txPlay;
    public static TextureRegion txPause;
    public static TextureRegion txRePlay;
    public static TextureRegion txUp;
    public static TextureRegion txDown;
    public static TextureRegion txLogo;
    public static TextureRegion txButtonBg;
    public static TextureRegion txIconStar;
    public static TextureRegion txIconAbout;
    public static TextureRegion txQuoteFrame;
    public static TextureRegion txFlask;
    public static TextureRegion txLeaderboard;
    public static TextureRegion txSwarm;
    public static TextureRegion txGamePaused;
    public static TextureRegion txGameOver;
    public static TextureRegion txArrow;

    public static Font fMain;

    public static Sound sJump;
    public static Sound sScore;
    public static Sound sHit;

    public static void init(BaseGameActivity activity, Engine engine, float camWidth, float camHeight, VertexBufferObjectManager vbo) {
        CONTEXT = activity.getApplicationContext();
        ACTIVITY = activity;
        ENGINE = engine;
        CW = camWidth;
        CH = camHeight;
        VBO = vbo;

        S = (CW / DEV_CAM_WIDTH + CH / DEV_CAM_HEIGHT) / 2;
        SH = CH / DEV_CAM_HEIGHT;

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        PixelPerfectTextureRegionFactory.setAssetBasePath("gfx/");
        FontFactory.setAssetBasePath("fonts/");
        SoundFactory.setAssetBasePath("sfx/");
    }

    public static void loadSplashSceneResources() {
        BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas(ENGINE.getTextureManager(), 1024, 1024,
                BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
        // ===== <ITEMS> ======
        txSplash = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "splash.png");
        // ===== </ITEMS> ======
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        atlas.load();
    }

    public static void unloadSplashSceneResources(){
        txSplash.getTexture().unload();
    }

    public static void loadGameSceneResources(){
        BitmapTextureAtlas atlasGround = new BitmapTextureAtlas(ENGINE.getTextureManager(), 1024, 24,
                BitmapTextureFormat.RGBA_4444, TextureOptions.NEAREST);
        // ===== <ITEMS> ======
        txGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlasGround, CONTEXT, "ground.png", 0, 0);
        // ===== </ITEMS> ======
        atlasGround.load();
        BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas(ENGINE.getTextureManager(), 1024, 1024,
                BitmapTextureFormat.RGBA_4444, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        // ===== <ITEMS> ======
        txBird = PixelPerfectTextureRegionFactory.createTiledFromAsset(atlas, CONTEXT.getAssets(), "bird.png", 2, 1, false, 250);
        txDinoDuck = PixelPerfectTextureRegionFactory.createTiledFromAsset(atlas, CONTEXT.getAssets(), "duck.png", 2, 1, false, 250);
        txDinoRun = PixelPerfectTextureRegionFactory.createTiledFromAsset(atlas, CONTEXT.getAssets(), "run.png", 2, 1, false, 250);
        txDinoNormal = PixelPerfectTextureRegionFactory.createFromAsset(atlas, CONTEXT.getAssets(), "normal.png", false, 250);
        txCactus1 = PixelPerfectTextureRegionFactory.createFromAsset(atlas, CONTEXT.getAssets(), "cactus1.png", false, 250);
        txCactus2 = PixelPerfectTextureRegionFactory.createFromAsset(atlas, CONTEXT.getAssets(), "cactus2.png", false, 250);
        txCactus3 = PixelPerfectTextureRegionFactory.createFromAsset(atlas, CONTEXT.getAssets(), "cactus3.png", false, 250);
        txCactus4 = PixelPerfectTextureRegionFactory.createFromAsset(atlas, CONTEXT.getAssets(), "cactus4.png", false, 250);
        txCactus5 = PixelPerfectTextureRegionFactory.createFromAsset(atlas, CONTEXT.getAssets(), "cactus5.png", false, 250);
        txCactus6 = PixelPerfectTextureRegionFactory.createFromAsset(atlas, CONTEXT.getAssets(), "cactus6.png", false, 250);

        txCactus7 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "cactus7.png");
        txPlay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "play.png");
        txCloud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "cloud.png");
        txDinoBlink = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, CONTEXT, "blink.png", 2, 1);
        txDinoHit = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "hit.png");
        txPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "pause.png");
        txRePlay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "replay.png");
        txDown = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "down.png");
        txUp = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "up.png");
        txLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "logo.png");
        txButtonBg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "button_bg.png");
        txIconStar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "icon_star.png");
        txIconAbout = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "icon_about.png");
        txQuoteFrame = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "quote.png");
        txFlask = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "flask.png");
        txLeaderboard = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "icon_leaderboard.png");
        txSwarm = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "swarm.png");
        txGamePaused = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "game_paused.png");
        txGameOver = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "game_over.png");
        txArrow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, CONTEXT, "arrow.png");
        // ===== </ITEMS> ======
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        atlas.load();

        fMain = FontFactory.createFromAsset(RM.ENGINE.getFontManager(), RM.ENGINE.getTextureManager(), 256, 256,
                 TextureOptions.NEAREST, CONTEXT.getAssets(), "slkscr.ttf", 24f, false, Color.WHITE);
        fMain.load();
        fMain.prepareLetters("HI---SCORE0123456789".toCharArray());

        try {
            sJump = SoundFactory.createSoundFromAsset(ENGINE.getSoundManager(), CONTEXT, "jump.mp3");
            sScore = SoundFactory.createSoundFromAsset(ENGINE.getSoundManager(), CONTEXT, "score.mp3");
            sHit = SoundFactory.createSoundFromAsset(ENGINE.getSoundManager(), CONTEXT, "hit.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
