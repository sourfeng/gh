package com.ww.android.governmentheart.adapter.work;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ww.android.governmentheart.R;
import com.ww.android.governmentheart.mvp.bean.work.ThemeReplyEntity;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.utils.TimeUtils;
import ww.com.core.widget.RoundImageView;

public class ThemeContentAdapter extends RvAdapter<ThemeReplyEntity>{

    public ThemeContentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.adapter_theme_content;
    }

    @Override
    protected RvViewHolder<ThemeReplyEntity> getViewHolder(int viewType, View view) {
        return new ThemeContentViewHolder(view);
    }

    class ThemeContentViewHolder extends RvViewHolder<ThemeReplyEntity>{
        @BindView(R.id.riv)
        RoundImageView riv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_order)
        TextView tvOrder;
        @BindView(R.id.tv_department)
        TextView tvDepartment;


        public ThemeContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, ThemeReplyEntity bean) {
            tvName.setText(bean.getUserName());
            tvTime.setText(TimeUtils.milliseconds2String(bean.getReplyDate()));
            tvContent.setText(bean.getContent());
            tvOrder.setText(String.format("%s楼",(position+1)));
            tvDepartment.setVisibility(position ==0?View.VISIBLE:View.GONE);
            tvDepartment.setText(bean.getOfficeName());
        }
    }
}
