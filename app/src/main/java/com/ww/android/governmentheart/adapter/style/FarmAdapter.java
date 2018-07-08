package com.ww.android.governmentheart.adapter.style;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.governmentheart.BaseApplication;
import com.ww.android.governmentheart.R;
import com.ww.android.governmentheart.mvp.bean.heart.NewsBean;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @Author feng
 * @Date 2018/6/17
 */
public class FarmAdapter extends RvAdapter<NewsBean> {

    public FarmAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.adapter_farm;
    }

    @Override
    protected RvViewHolder<NewsBean> getViewHolder(int viewType, View view) {
        return new FarmViewHolder(view);
    }


    class FarmViewHolder extends RvViewHolder<NewsBean> {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public FarmViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, NewsBean bean) {
            ImageLoader.getInstance().displayImage(bean.getImage(), iv, BaseApplication
                    .getDisplayImageOptions(R.mipmap.ic_pic_default));
            tvTitle.setText(bean.getTitle());
        }
    }
}
