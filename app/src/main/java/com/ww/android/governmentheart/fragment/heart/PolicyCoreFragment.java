package com.ww.android.governmentheart.fragment.heart;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ww.android.governmentheart.R;
import com.ww.android.governmentheart.adapter.heart.PolicyAdapter;
import com.ww.android.governmentheart.fragment.BaseFragment;
import com.ww.android.governmentheart.mvp.PageListBean;
import com.ww.android.governmentheart.mvp.bean.PageBean;
import com.ww.android.governmentheart.mvp.bean.PagingBean;
import com.ww.android.governmentheart.mvp.bean.login.NewsChildTypeBean;
import com.ww.android.governmentheart.mvp.model.CommonModel;
import com.ww.android.governmentheart.mvp.vu.RefreshView;
import com.ww.android.governmentheart.network.BaseObserver;
import com.ww.android.governmentheart.utils.RecyclerHelper;
import com.ww.android.governmentheart.widget.EmptyLayout;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ww.com.core.utils.TimeUtils;

/**
 * @Author feng
 * @Date 2018/7/8
 */
public class PolicyCoreFragment extends BaseFragment<RefreshView, CommonModel> {

    private String id; // code:1 方针，2 统战知识，3 权威解读， 4 政策库 5 崇州特色 6 农副产品 7 电商介绍 8 人物访谈 9加入我（加入组织）
    private int page;
    private PolicyAdapter adapter;


    public static PolicyCoreFragment newInstance(String id) {
        PolicyCoreFragment fragment = new PolicyCoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_policy_core;
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected void init() {
        initData();
        initListener();
        if (v.crv == null) {
            return;
        }
        initRecycler();
        v.srl.autoRefresh();
    }

    private void initData() {
        id = getArguments().getString("id", "0");
    }

    private void initListener() {
        if (v.srl == null) {
            return;
        }

        v.srl.setOnRefreshListener(refreshLayout -> {
            page = 0;
            news();
        });

        v.srl.setOnLoadMoreListener(refreshLayout -> news());
    }

    private void initRecycler() {
        v.initRecycler(RecyclerHelper.defaultManager(getContext()), RecyclerHelper
                .defaultMoreDecoration(getContext()));

        adapter = new PolicyAdapter(getContext());
        v.crv.setAdapter(adapter);
    }

    private void news() {
        Map map = new HashMap();
        map.put("id", id);
        map.put("pageNo", page);
        if (page == 0) {
            map.put("date", TimeUtils.date2String(new Date()));
        }
        if (v.srl == null) {
            return;
        }
        m.newsCategoryChild(map, new BaseObserver<PageListBean<NewsChildTypeBean>>(getContext(),bindToLifecycle()) {
            @Override
            protected void onSuccess(@Nullable PageListBean<NewsChildTypeBean>
                                             newsChildTypeBeanPageListBean, @Nullable
                                             List<PageListBean<NewsChildTypeBean>> list, @Nullable
                                             PageBean<PageListBean<NewsChildTypeBean>> pageBean) {

                if (newsChildTypeBeanPageListBean != null && newsChildTypeBeanPageListBean.getList() != null &&
                        newsChildTypeBeanPageListBean.getList().size() > 0) {
                    v.loadStatus(EmptyLayout.STATUS_HIDE);

                    List<NewsChildTypeBean> newsBeans = newsChildTypeBeanPageListBean.getList();
                    PagingBean pagingBean = newsChildTypeBeanPageListBean.getPage();
                    int totalPage = pagingBean.getTotalPage();
                    if (page == 0) {
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
}
