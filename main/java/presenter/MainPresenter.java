package presenter;

import android.util.Log;

import java.util.Map;

import callback.MyCallBack;
import model.MainModel;
import view.MyView;

/**
 * 作者：穆佳琪
 * 时间：2019/1/3 9:15.
 */

public class MainPresenter implements IPresenter {
    private MyView myView;
    private MainModel mainModel;

    public MainPresenter(MyView myView) {
        this.myView = myView;
        mainModel = new MainModel();
    }

    @Override
    public void getRequest(String url, Map<String, String> map, Class clazz) {
        mainModel.getData(url, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object o) {
                Log.d("MainPresenter", o.toString());
                myView.Success(o);
            }

            @Override
            public void onFail(String error) {
                myView.Error(error);
            }
        });
    }

}
