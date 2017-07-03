package com.okwyx.client.framework.libs.model;

/**
 * 作者：Swei on 2017/4/26 21:45<BR/>
 * 邮箱：sweilo@qq.com
 */

public class ItemDelegateModel {

    private int itemLayoutId;

    private int itemType;

    private int variabieId;

    public ItemDelegateModel(int itemLayoutId, int itemType, int variabieId) {
        this.itemLayoutId = itemLayoutId;
        this.itemType = itemType;
        this.variabieId = variabieId;
    }

    public int getItemLayoutId() {
        return itemLayoutId;
    }

    public int getVariabieId() {
        return variabieId;
    }
    public int getItemType() {
        return itemType;
    }

    public void setItemLayoutId(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setVariabieId(int variabieId) {
        this.variabieId = variabieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDelegateModel that = (ItemDelegateModel) o;

        return itemLayoutId == that.itemLayoutId;

    }

    @Override
    public int hashCode() {
        return itemLayoutId;
    }
}
