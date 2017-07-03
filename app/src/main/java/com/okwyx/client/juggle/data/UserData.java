package com.okwyx.client.juggle.data;

import java.io.Serializable;

/**
 * 作者：Swei on 2017/6/1 13:06<BR/>
 * 邮箱：sweilo@qq.com
 */

public class UserData implements Serializable{

    public static UserData getInstance(){
        return Holder.userData;
    }

    private final static class Holder {
        private static UserData userData = new UserData();
    }
    public String uid;//id

    public String countryName = DefaultData.countryName;//国家

    public int maxScore = DefaultData.score;//最高得分

    public long money;






}
