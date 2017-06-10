package cn.binarywang.wx.miniapp.util.http;


import cn.binarywang.wx.miniapp.bean.material.WxMpMaterialVideoInfoResult;
import cn.binarywang.wx.miniapp.util.http.apache.ApacheMaterialVideoInfoRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.jodd.JoddMaterialVideoInfoRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.okhttp.OkhttpMaterialVideoInfoRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;


public abstract class MaterialVideoInfoRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialVideoInfoResult, String> {
  protected RequestHttp<H, P> requestHttp;

  public MaterialVideoInfoRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<WxMpMaterialVideoInfoResult, String> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMaterialVideoInfoRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddMaterialVideoInfoRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkhttpMaterialVideoInfoRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
