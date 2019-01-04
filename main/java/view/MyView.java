package view;

/**
 * 作者：穆佳琪
 * 时间：2019/1/3 9:13.
 */

public interface MyView<T> {
    void Success(T success);
    void Error(String error);
}
