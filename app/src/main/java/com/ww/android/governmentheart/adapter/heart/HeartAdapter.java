package com.ww.android.governmentheart.adapter.heart;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.governmentheart.BaseApplication;
import com.ww.android.governmentheart.R;
import com.ww.android.governmentheart.activity.base.ContentDetailActivity;
import com.ww.android.governmentheart.config.type.CommentType;
import com.ww.android.governmentheart.mvp.bean.heart.NewsBean;
import com.ww.android.governmentheart.mvp.bean.home.EasyRequestBean;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

public class HeartAdapter extends RvAdapter<NewsBean> {

    public HeartAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.adapter_heart_body;
    }

    @Override
    protected RvViewHolder<NewsBean> getViewHolder(int viewType, View view) {
        return new HeartViewHolder(view);
    }

    class HeartViewHolder extends RvViewHolder<NewsBean> {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title_name)
        TextView tvTitleName;
        @BindView(R.id.tv_eyes)
        TextView tvEyes;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.comment_container)
        LinearLayout commentContainer;
        @BindView(R.id.container)
        LinearLayout container;


        public HeartViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, NewsBean bean) {
            ImageLoader.getInstance().displayImage(bean.getImage(), iv, BaseApplication
                    .getDisplayImageOptions(R.mipmap.ic_pic_default));
            tvTitleName.setText(bean.getTitle());
            tvEyes.setText(bean.getHits());
            tvComment.setText(bean.getCommentCount());
            tvTime.setText(bean.getCreateDate());


            EasyRequestBean easyRequestBean = new EasyRequestBean.Builder()
                    .setId(bean.getId())
                    .setName(bean.getTitle())
                    .setUrl(bean.getUrl())
                    .setType(CommentType.TYPE_NEWS)
                    .setNum(TextUtils.isEmpty(bean.getCommentCount()) ? 0 : Integer.valueOf
                            (bean.getCommentCount()))
                    .build();
            container.setOnClickListener(v -> ContentDetailActivity.start(getContext(),easyRequestBean));
        }
    }
}
