package com.okwyx.client.framework.libs.model;

/**
 * 作者：Swei on 2017/4/26 22:34<BR/>
 * 邮箱：sweilo@qq.com
 * 对应card节点
 */

public class BaseCard<INFO> {

    private int cardType;

    private String cardName;

    /** 比如:来自编辑推荐 */
    private String title;


    private INFO data;

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public INFO getData() {
        return data;
    }

    public void setData(INFO data) {
        this.data = data;
    }
}
