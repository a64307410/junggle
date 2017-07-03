package com.okwyx.client.juggle.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.okwyx.client.framework.libs.ad.interfaces.ADListener;
import com.okwyx.client.framework.libs.ad.interfaces.tencent.ADTencent;
import com.okwyx.client.framework.libs.utils.Log;
import com.okwyx.client.juggle.R;
import com.okwyx.client.juggle.UserConfig;
import com.okwyx.client.juggle.base.SceneName;
import com.okwyx.client.juggle.listener.IGameListener;
import com.okwyx.client.juggle.logic.GameLogic;
import com.okwyx.client.juggle.mananger.SoundManager;
import com.okwyx.client.juggle.utils.GameUtil;
import com.okwyx.client.juggle.widget.GameStartUI;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.ScreenCapture;
import org.andengine.opengl.view.RenderSurfaceView;


public class MainActivity extends BaseGameActivity {
    public static Handler mHandler = new Handler();

    private ScreenCapture screenCapture;

    private GameStartUI gameStartUI;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        UserConfig.getInstance().setCurScene(SceneName.Main);
        super.onCreate(pSavedInstanceState);
        //初始化声音
        SoundManager.getInstance().init();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        GameUtil.SCREEN_WIDTH = dm.widthPixels;
        GameUtil.SCREEN_HEIGHT = dm.heightPixels;
        GameUtil.textureMgr = getTextureManager();
        GameUtil.assetMgr = getAssets();
        GameUtil.vertexBufferObjectMgr = getVertexBufferObjectManager();
    }

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_main);
        this.mRenderSurfaceView = (RenderSurfaceView) findViewById(R.id.main_game_surfaceview);
        this.mRenderSurfaceView.setRenderer(this.mEngine, this);
        this.gameStartUI = GameStartUI.create(this,findViewById(R.id.main_game_start));
    }

//    public void showToast() {
//        noticeView.setVisibility(View.VISIBLE);
//        noticeView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fading_in));
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                noticeView.setVisibility(View.GONE);
//                noticeView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fading_out));
//            }
//        }, 1500);
//    }

    @Override
    public synchronized void onGameCreated() {
        super.onGameCreated();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.gameStartUI.show();
            }
        });
        this.gameStartUI.setListener(new IGameListener() {
            @Override
            public void onStartGame() {
                ADTencent.getInstance().showBannner(MainActivity.this, new ADListener.DefaultADListener() {
                    @Override
                    public void onStatus(int code, Object obj) {
                        super.onStatus(code,obj);


                    }
                });

//                CreateManager.getInstance().getFrameCreate().start();
            }
        });

        Log.d("onGameCreated");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getGameLogic().onRelease();
    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = super.onCreateScene();
        SoundManager.playMusic();
        screenCapture = new ScreenCapture();
        screenCapture.setZIndex(8);
        scene.attachChild(screenCapture);
        return scene;
    }


    @Override
    public void onReloadResources() {
        super.onReloadResources();
    }

    @Override
    public GameLogic createGameLogic() {
        return new GameLogic();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDestroyResources() throws Exception {
    }


    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        SoundManager.resumeMusic();
    }

    @Override
    public synchronized void onPauseGame() {
        super.onPauseGame();
        SoundManager.pauseMusic();
    }

    public ScreenCapture getScreenCapture() {
        return screenCapture;
    }
}

