package cn.binarywang.wx.miniapp.util.http;

import cn.binarywang.wx.miniapp.util.http.apache.ApacheMaterialDeleteRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.jodd.JoddMaterialDeleteRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.okhttp.OkhttpMaterialDeleteRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;

public abstract class MaterialDeleteRequestExecutor<H, P> implements RequestExecutor<Boolean, String> {
  protected RequestHttp<H, P> requestHttp;

  public MaterialDeleteRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMaterialDeleteRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddMaterialDeleteRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkhttpMaterialDeleteRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
