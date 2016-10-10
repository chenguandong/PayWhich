package com.a10h3y.paywhat.App;

import android.app.Application;

import com.a10h3y.paywhat.Tools.DateTools;

import io.realm.Realm;


/**
 * Created by guandongchen on 2016/10/8.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Realm 数据库
        Realm.init(this);

        DateTools.getInstance();

    }
}
