package com.ww.android.governmentheart.mvp.model.work;

import com.ww.android.governmentheart.mvp.PageListBean;
import com.ww.android.governmentheart.mvp.bean.wisdom.TransmissionDetailBean;
import com.ww.android.governmentheart.mvp.bean.work.MessageEntity;
import com.ww.android.governmentheart.mvp.bean.work.NotifyEntity;
import com.ww.android.governmentheart.mvp.bean.work.ReceptionEntity;
import com.ww.android.governmentheart.mvp.bean.work.ThemeDetailBean;
import com.ww.android.governmentheart.mvp.bean.work.ThemeEntity;
import com.ww.android.governmentheart.mvp.bean.work.ThemeReplyEntity;
import com.ww.android.governmentheart.mvp.model.base.BaseModel;
import com.ww.android.governmentheart.network.BaseObserver;
import com.ww.android.governmentheart.network.HttpRequest;
import com.ww.android.governmentheart.network.JsonParse;
import com.ww.android.governmentheart.network.utils.RxSchedulers;

import java.util.Map;

public class WorkModel extends BaseModel {

    public void workList(Map map, BaseObserver<PageListBean<NotifyEntity>> observer){
        HttpRequest.workApi().workList(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }



    public void messageList(Map map, BaseObserver<PageListBean<MessageEntity>> observer){
        HttpRequest.workApi().messageList(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }

    public void notifyDetail(Map map, BaseObserver<PageListBean<NotifyEntity>> observer){
        HttpRequest.workApi().notifyDetail(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }

    public void topicList(Map map, BaseObserver<PageListBean<ThemeEntity>> observer){
        HttpRequest.workApi().topicList(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }

    public void topicDetail(Map map, BaseObserver<PageListBean<ThemeDetailBean>> observer){
        HttpRequest.workApi().topicDetail(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }


    public void topicReplayList(Map map, BaseObserver<PageListBean<ThemeReplyEntity>> observer){
        HttpRequest.workApi().replyList(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }


    public void replayForum(Map map, BaseObserver<String> observer){
        HttpRequest.workApi().replayForum(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }

    public void receiveMaterial(Map map, BaseObserver<PageListBean<ReceptionEntity>> observer){
        HttpRequest.workApi().receiveMaterial(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }


    public void readMsg(Map map, BaseObserver<String> observer){
        HttpRequest.workApi().readMsg(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }

    /**
     * 资料发送详情
     * @param map
     * @param observer
     */
    public void materialDetail(Map map, BaseObserver<PageListBean<TransmissionDetailBean>> observer) {
        HttpRequest.wisdomApi().materialDetail(JsonParse.createArgs(map))
                .compose(RxSchedulers.cutObservableMain())
                .compose(observer.getTransformer())
                .subscribe(observer);
    }




}
