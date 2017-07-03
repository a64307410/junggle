package com.okwyx.client.juggle.widget;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.okwyx.client.framework.libs.display.ImageDisplayer;
import com.okwyx.client.juggle.R;
import com.okwyx.client.juggle.UserConfig;
import com.okwyx.client.juggle.listener.IGameListener;
import com.okwyx.client.juggle.mananger.SoundManager;
import com.okwyx.client.juggle.utils.ScoreUtils;

/**
 * 作者：Swei on 2017/6/1 07:47<BR/>
 * 邮箱：sweilo@qq.com
 */

public class GameStartUI {
    public Context context;

    private View rootView;
    private View logoView;
    private View startView;

    //声音
    private ImageView audioView;
    //自己选择的国家图标
    private ImageView myCountry;
    //得分
    private TextView myScore;

    private IGameListener listener;

    /** 左上角自己的国家 */
    private String myCounrtyRes = "";

    public static GameStartUI create(Context context,View rootView){
        return new GameStartUI(context,rootView);
    }

    public GameStartUI(Context context,View rootView){
        this.context = context;
        this.rootView = rootView;
        initViews();
        initListeners();
    }

    private void loadData() {
        if(SoundManager.isSoundOpen()){
            audioView.setImageResource(R.mipmap.ui_voice_on);
        }else{
            audioView.setImageResource(R.mipmap.ui_voice_off);
        }
        //加载选择的国家
        String bannerName = UserConfig.getInstance().getBannerName();
        StringBuilder sb = new StringBuilder();
        sb.append("assets://").append("gfx/").append(bannerName);
        ImageDisplayer.load(myCountry, sb.toString());
        //得分
        myScore.setText(ScoreUtils.getScoreHtml(2384));
    }

    public void setListener(IGameListener listener) {
        this.listener = listener;
    }


    private void initViews(){
        logoView = findViewById(R.id.logo);
        audioView = (ImageView) findViewById(R.id.audio);
        startView = findViewById(R.id.play);
        myCountry = (ImageView) (findViewById(R.id.img_change_guoqi).findViewById(R.id.rank_img_country));
        myScore = (TextView) findViewById(R.id.score_tv);
    }

    private void initListeners() {
        audioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //点击后的声音开关
                if(SoundManager.toggleSoundMuted()){
                    ((ImageView)v).setImageResource(R.mipmap.ui_voice_on);
                }else{
                    ((ImageView)v).setImageResource(R.mipmap.ui_voice_off);
                }
            }
        });

        startView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rootView.setVisibility(View.GONE);
                if(listener != null){
                    listener.onStartGame();
                }
            }
        });
    }

    public void show(){
        loadData();
        rootView.setVisibility(View.VISIBLE);
        Animation animation = new TranslateAnimation(0, 0,-800 , 0);
        animation.setInterpolator(new BounceInterpolator());
        animation.setFillAfter(true);
        animation.setDuration(1000);
        logoView.startAnimation(animation);
    }

    private View findViewById(int id){
        return this.rootView.findViewById(id);
    }


}
