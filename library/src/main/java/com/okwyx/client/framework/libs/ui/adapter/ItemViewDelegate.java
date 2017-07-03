package com.okwyx.client.framework.libs.ui.adapter;

/**
 * 作者：Swei on 2017/4/26 15:18<BR/>
 * 邮箱：sweilo@qq.com
 */

public interface ItemViewDelegate<T> {

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    /**
     * 布局文件中定义的变量id  即是BR.item
     * @return
     */
    public abstract int getVariableId();

}
