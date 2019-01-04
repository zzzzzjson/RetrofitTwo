package presenter;

import java.util.Map;

/**
 * 作者：穆佳琪
 * 时间：2019/1/3 9:14.
 */

public interface IPresenter {
    void getRequest(String url, Map<String,String> map,Class clazz);
}
