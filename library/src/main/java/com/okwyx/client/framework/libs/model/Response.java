package com.okwyx.client.framework.libs.model;

import java.io.Serializable;

/**
 * 作者：Swei on 2017/4/13 21:06<BR/>
 * 邮箱：sweilo@qq.com
 * 所有网络请求实体继承我
 */

public class Response<T> implements Serializable{
    private int code;
    private String msg;
    private String url;

    private T info;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
