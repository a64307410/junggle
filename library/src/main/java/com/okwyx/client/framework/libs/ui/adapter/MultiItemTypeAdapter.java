package com.okwyx.client.framework.libs.ui.adapter;

//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 作者：Swei on 2017/4/26 15:17<BR/>
// * 邮箱：sweilo@qq.com
// */
//
//public class MultiItemTypeAdapter<T> extends BaseAdapter {
//    protected List<T> mDatas;
//
//    private LayoutInflater mInflater;
//
//    private ItemViewDelegateManager mItemViewDelegateManager;
//
//    private ViewDataBinding mViewDataBinding;
//
//
//    private final Object mLock = new Object();
//
//    public MultiItemTypeAdapter() {
//        this(null);
//    }
//
//    public MultiItemTypeAdapter(List<T> datas) {
//        if (datas == null) {
//            datas = new ArrayList<>();
//        }
//        this.mDatas = datas;
//        this.mItemViewDelegateManager = new ItemViewDelegateManager();
//    }
//
//    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
//        mItemViewDelegateManager.addDelegate(itemViewDelegate);
//        return this;
//    }
//
//    public void clearItemViewDelegate() {
//        mItemViewDelegateManager.clear();
//    }
//
//    private boolean useItemViewDelegateManager() {
//        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        if (useItemViewDelegateManager()) {
//            return mItemViewDelegateManager.getItemViewDelegateCount();
//        } else {
//            return super.getViewTypeCount();
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (useItemViewDelegateManager()) {
//            int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
//            return viewType;
//        }
//        return super.getItemViewType(position);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (mInflater == null) {
//            mInflater = LayoutInflater.from(parent.getContext());
//        }
//
//        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
//        int layoutId = itemViewDelegate.getItemViewLayoutId();
//        if (convertView == null) {
//            mViewDataBinding = DataBindingUtil.inflate(mInflater, layoutId, parent, false);
//            convertView = mViewDataBinding.getRoot();
//            convertView.setTag(mViewDataBinding);
//
//        } else {
//            mViewDataBinding = (ViewDataBinding) convertView.getTag();
//        }
//
//        mViewDataBinding.setVariable(itemViewDelegate.getVariableId(), getItem(position));
//        return convertView;
//    }
//
//
//    @Override
//    public int getCount() {
//        return mDatas.size();
//    }
//
//    @Override
//    public T getItem(int position) {
//        return mDatas.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public void addAll(List<T> datas) {
//        synchronized (mLock) {
//            mDatas.addAll(datas);
//        }
//        notifyDataSetChanged();
//    }
//
//    public void clear(boolean notifyDataSetChanged) {
//        synchronized (mLock) {
//            mDatas.clear();
//        }
//        if(notifyDataSetChanged){
//            notifyDataSetChanged();
//        }
//    }
//    public void clear(){
//        clear(true);
//    }
//
//
//}
