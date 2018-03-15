package com.od.onlineordering;

/**
 * Created by ${hanxixun} on 2017/5/15.
 */

public class Singleton {
    //持有私有静态实例，防止被引用，此处赋值位null，目的是实验延迟加载
    private static Singleton instance = null;

    //私有构造方法，防止被实例化
    private Singleton() {

    }

    //懒汉模式，静态工程方法，创建实例
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
