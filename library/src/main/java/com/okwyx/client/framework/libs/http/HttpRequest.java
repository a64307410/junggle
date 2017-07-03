package com.okwyx.client.framework.libs.http;

import com.weibo.game.network.other.HttpListener;
import com.weibo.game.network.request.annotation.AnnotationRequest;
import com.weibo.game.network.request.annotation.BaseHttpParameter;
import com.weibo.game.network.utils.HttpFactory;

import org.apache.http.impl.client.AbstractHttpClient;
/**
 * 作者：Swei on 2017/4/12 17:28<BR/>
 * 邮箱：sweilo@qq.com
 */

public abstract class HttpRequest<P extends BaseHttpParameter, T> extends AnnotationRequest<P, T> {


    protected static final int retryCount = 3;
    protected static final int timeout = 30 * 1000;

    public HttpRequest(String url) {
        super(url);
    }

    @Override
    public void excute(HttpListener<T> listener) {
        super.excute(listener);
    }

    @Override
    protected AbstractHttpClient createHttpClient() {
        return HttpFactory.createHttpClient(HttpConstants.getHeader(),retryCount,timeout,null);
    }




}
