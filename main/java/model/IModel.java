package model;

import java.util.Map;

import callback.MyCallBack;

/**
 * 作者：穆佳琪
 * 时间：2019/1/3 9:10.
 */

public interface IModel {
    void getData(String url, Map<String,String> param, Class clazz, MyCallBack myCallBack);
}
