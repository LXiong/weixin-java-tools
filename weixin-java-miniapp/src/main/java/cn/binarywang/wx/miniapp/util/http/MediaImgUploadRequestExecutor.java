package cn.binarywang.wx.miniapp.util.http;

import cn.binarywang.wx.miniapp.bean.material.WxMediaImgUploadResult;
import cn.binarywang.wx.miniapp.util.http.apache.ApacheMediaImgUploadRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.jodd.JoddMediaImgUploadRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.okhttp.OkhttpMediaImgUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;

import java.io.File;

/**
 * @author miller
 */
public abstract class MediaImgUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaImgUploadResult, File> {
  protected RequestHttp<H, P> requestHttp;

  public MediaImgUploadRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<WxMediaImgUploadResult, File> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMediaImgUploadRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddMediaImgUploadRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkhttpMediaImgUploadRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
