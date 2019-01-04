package callback;

/**
 * 作者：穆佳琪
 * 时间：2019/1/3 9:12.
 */

public interface MyCallBack<T> {
    void onSuccess(T t);
    void onFail(String error);
}
