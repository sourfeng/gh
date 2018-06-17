package com.ww.android.governmentheart.adapter.style;

import android.content.Context;
import android.view.View;

import com.ww.android.governmentheart.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @Author feng
 * @Date 2018/6/17
 */
public class FarmAdapter extends RvAdapter<String> {

    public FarmAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.adapter_farm;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return new FarmViewHolder(view);
    }


    class FarmViewHolder extends RvViewHolder<String> {

        public FarmViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }
}
