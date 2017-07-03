package com.okwyx.client.juggle.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

import com.okwyx.client.juggle.JuggleApplication;

/**
 * 作者：Swei on 2017/6/1 10:52<BR/>
 * 邮箱：sweilo@qq.com
 */

public class ScoreUtils {
    public static final String SCORE_TAG_ = "img_score_";


    public static Spanned getScoreHtml(long score) {
        if (score < 0) {
            score = 0;
        }
        String strScore = String.valueOf(score);
        String html = "<span style=\"font-size:2px; color:#000000\">&nbsp;</span>";
        for (int i = 0; i < strScore.length(); i++) {
            html += "<img src=\""+SCORE_TAG_ + strScore.charAt(i) + "\"/>";
        }

        return Html.fromHtml(html, imgGetter, null);
    }

    static Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            try {
                int id = getDrawableID(source);
                drawable = JuggleApplication.instance.getResources().getDrawable(id);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
            return drawable;
        }
    };

    public static int getDrawableID(String name) {
        return JuggleApplication.instance.getResources().getIdentifier(name, "mipmap", JuggleApplication.instance.getPackageName());
    }
}
