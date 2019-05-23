package com.alibaba.weex;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.alibaba.weex.extend.ImageAdapter;
import com.alibaba.weex.extend.WXEventModule;
import com.alibaba.weex.plugin.loader.WeexPluginContainer;
import com.alibaba.weex.ui.GSYWeb;
import com.alibaba.weex.util.AppConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

public class WXApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    WXSDKEngine.addCustomOptions("appName", "WXSample");
    WXSDKEngine.addCustomOptions("appGroup", "WXApp");
    WXSDKEngine.initialize(this,
        new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build()
    );
    try {
      WXSDKEngine.registerModule("event", WXEventModule.class);
      WXSDKEngine.registerComponent("gsyWeb", GSYWeb.class);
    } catch (WXException e) {
      e.printStackTrace();
    }
    AppConfig.init(this);
    WeexPluginContainer.loadAll(this);

    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
    StrictMode.setVmPolicy(builder.build());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      builder.detectFileUriExposure();
    }
  }
}
