package com.ww.android.governmentheart.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ww.android.governmentheart.R;
import com.ww.android.governmentheart.activity.base.UserActivity;
import com.ww.android.governmentheart.activity.work.PortraitActivity;
import com.ww.android.governmentheart.adapter.home.HomeAdapter;
import com.ww.android.governmentheart.config.Constant;
import com.ww.android.governmentheart.mvp.PageListBean;
import com.ww.android.governmentheart.mvp.bean.PageBean;
import com.ww.android.governmentheart.mvp.bean.PagingBean;
import com.ww.android.governmentheart.mvp.bean.heart.NewsBean;
import com.ww.android.governmentheart.mvp.model.CommonModel;
import com.ww.android.governmentheart.mvp.vu.RefreshView;
import com.ww.android.governmentheart.network.BaseObserver;
import com.ww.android.governmentheart.utils.RecyclerHelper;
import com.ww.android.governmentheart.widget.EmptyLayout;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import ww.com.core.utils.TimeUtils;

/**
 * @Author feng
 * @Date 2018/6/10
 */
public class HomeFragment extends BaseFragment<RefreshView, CommonModel> {

    private int code = 0;
    private int page;
    private HomeAdapter adapter;
    private String mainpic;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initListener();
        if (v.crv == null) {
            return;
        }
        initRecycler();
        mainPic();
    }


    @Override
    public void onTitleRight() {
        super.onTitleRight();
        UserActivity.start(getActivity());
    }

    private void initListener() {
        if (v.srl == null) {
            return;
        }

        v.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                news();
            }
        });

        v.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                news();
            }
        });
    }

    private void initRecycler() {
        v.initRecycler(RecyclerHelper.defaultManager(getContext()), RecyclerHelper
                .defaultMoreDecoration(getContext()));

        adapter = new HomeAdapter(getContext());
        v.crv.setAdapter(adapter);
    }

    @OnClick({R.id.btn_title_left})
    public void onClick() {
        PortraitActivity.start(getContext());
    }

    private void news() {
        Map map = new HashMap();
        map.put("code", code);
        map.put("pageNo", page);
        if (page == 0) {
            map.put("date", TimeUtils.date2String(new Date()));
        }
        if (v.srl == null) {
            return;
        }
        m.news(map, new BaseObserver<PageListBean<NewsBean>>(getContext(), bindToLifecycle()) {
            @Override
            protected void onSuccess(@Nullable PageListBean<NewsBean> newsBeanPageListBean,
                                     @Nullable List<PageListBean<NewsBean>> list, @Nullable
                                             PageBean<PageListBean<NewsBean>> pageBean) {

                if (newsBeanPageListBean != null && newsBeanPageListBean.getList() != null
                        && newsBeanPageListBean.getList().size() > 0) {
                    v.loadStatus(EmptyLayout.STATUS_HIDE);
                    List<NewsBean> newsBeans = setType(newsBeanPageListBean.getList());
                    if (newsBeans != null && newsBeans.size() > 0) {
                        Constant.INTRODUCE_URL = newsBeans.get(0).getUrl();
                    }
                    PagingBean pagingBean = newsBeanPageListBean.getPage();
                    int totalPage = pagingBean.getTotalPage();
                    if (page == 0) {
                        NewsBean newsBean = new NewsBean(NewsBean.MULTIPLE_HEADER);
                        newsBean.mainpic = mainpic;
                        newsBean.totalNum = pagingBean.getTotalNum();
                        newsBeans.add(0, newsBean);
                        v.srl.finishRefresh();
                        if (newsBeans != null && newsBeans.size() > 0) {
                            adapter.addList(newsBeans);
                            page++;
                        } else {
                            v.srl.setNoMoreData(true);
                        }
                    } else {
                        v.srl.finishLoadMore();
                        if (page < totalPage) {
                            adapter.appendList(newsBeans);
                            v.srl.setNoMoreData(false);
                            page++;
                        } else {
                            v.srl.setNoMoreData(true);
                        }
                    }
                } else {
                    reload(EmptyLayout.STATUS_NO_DATA);
                }
            }

            @Override
            protected void onFailure() {
                super.onFailure();
                reload(EmptyLayout.STATUS_NO_NET);
            }
        });
    }

    private List<NewsBean> setType(List<NewsBean> newsBeans) {
        for (NewsBean newsBean : newsBeans) {
            newsBean.setItemType(NewsBean.MULTIPLE_BODY);
        }
        return newsBeans;
    }

    private void reload(int type) {
        if (type == EmptyLayout.STATUS_NO_NET) {
            v.loadStatus(EmptyLayout.STATUS_NO_NET);
        } else {
            v.loadStatus(EmptyLayout.STATUS_NO_DATA);
        }
        v.mEmptyLayout.setRetryListener(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                news();
            }
        });
        if (page == 0) {
            v.srl.finishRefresh();
        } else {
            v.srl.finishLoadMore();
        }
    }

    private void mainPic() {
        m.mainPic(new BaseObserver<String>(getContext(), bindToLifecycle()) {
            @Override
            protected void onSuccess(@Nullable String s, @Nullable List<String> list, @Nullable
                    PageBean<String> page) {
                mainpic = s;
                news();
            }
        });
    }
}
