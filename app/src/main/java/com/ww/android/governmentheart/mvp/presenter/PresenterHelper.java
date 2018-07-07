package com.ww.android.governmentheart.mvp.presenter;


import com.ww.android.governmentheart.mvp.model.IModel;
import com.ww.android.governmentheart.mvp.model.VoidModel;
import com.ww.android.governmentheart.mvp.utils.ClassUtil;
import com.ww.android.governmentheart.mvp.vu.IView;
import com.ww.android.governmentheart.mvp.vu.base.VoidView;

/**
 * @author feng
 * @Date 2017/12/21.
 */

class PresenterHelper {
    private IPresenter presenter;

    private PresenterHelper(IPresenter presenter) {
        this.presenter = presenter;
    }

    public static void bind(IPresenter presenter) {
        PresenterHelper presenterHelper = new PresenterHelper(presenter);
        try {
            presenterHelper.build();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void build() throws IllegalAccessException, InstantiationException {
        Class viewCls = getClassView();
        IView iView = null;
        if (viewCls != null) {
            Object obj = viewCls.newInstance();
            if (IView.class.isInstance(obj)) {
                iView = (IView) obj;
            }
        }

        if (iView == null) {
            iView = new VoidView();
        }

        this.presenter.setViewModule(iView);

        Class modelCls = getClassModel();
        IModel iModel = null;
        if (modelCls != null) {
            Object obj = modelCls.newInstance();
            if (IModel.class.isInstance(obj)) {
                iModel = (IModel) obj;
            }
        }

        if (iModel == null) {
            iModel = new VoidModel();
        }

        this.presenter.setModelModule(iModel);
    }

    private Class<?> getClassView() {
        return ClassUtil.getParameterizedClass(presenter.getClass(), 0);
    }

    private Class<?> getClassModel() {
        return ClassUtil.getParameterizedClass(presenter.getClass(), 1);
    }
}
