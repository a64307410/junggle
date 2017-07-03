package com.okwyx.client.framework.libs.http;

import com.okwyx.client.framework.libs.utils.Log;
import com.weibo.game.network.exception.HttpParseException;
import com.weibo.game.network.request.RequestURL;
import com.weibo.game.network.request.annotation.BaseHttpParameter;
import com.weibo.game.network.request.annotation.HttpParameterApi;

import org.json.JSONObject;

/**
 * 作者：Swei on 2017/4/12 17:30<BR/>
 * 邮箱：sweilo@qq.com
 */

public abstract class HttpJSONParse <P extends BaseHttpParameter,T> extends HttpParameterApi<P ,T> {

    public HttpJSONParse(P p) {
        super(p);
    }

    public HttpJSONParse(P p, boolean needAutoAdd) {
        super(p, needAutoAdd);
    }

    @Override
    public T byteToObject(P parameter, byte[] retValue) throws HttpParseException {
        String json = null ;
        try {
            json = new String(retValue , "UTF-8");
            Log.d(json);
            JSONObject jo = new JSONObject(json);
            int code = jo.optInt("code");
            if(code ==0){
                JSONObject data = jo.optJSONObject("data");
                return opreateJSON(data);
            }else{
                String msg = jo.optString("msg");
                JSONObject obj = new JSONObject();
                obj.put("error", msg);
                obj.put("error_code", code);
                json = obj.toString();
                throw new HttpParseException(json);
            }
        }catch(Exception t){
            throw new HttpParseException(json);
        }
    }

    public abstract T opreateJSON(JSONObject job);

    @Override
    protected String baseURL(BaseHttpParameter p) {
        return super.baseURL(p);
    }

    @Override
    protected RequestURL parseIn(RequestURL url, BaseHttpParameter parameter) {
        return super.parseIn(url, parameter);
    }


}