package cn.binarywang.wx.miniapp.util.http;

import cn.binarywang.wx.miniapp.bean.WxMpQrcode;
import cn.binarywang.wx.miniapp.util.http.apache.ApacheQrCodeRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.jodd.JoddQrCodeRequestExecutor;
import cn.binarywang.wx.miniapp.util.http.okhttp.OkhttpQrCodeRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;

import java.io.File;

/**
 * 获得QrCode图片 请求执行器
 *
 * @author chanjarster
 */
public abstract class QrCodeRequestExecutor<H, P> implements RequestExecutor<File, WxMpQrcode> {
  protected RequestHttp<H, P> requestHttp;

  public QrCodeRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<File, WxMpQrcode> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheQrCodeRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddQrCodeRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkhttpQrCodeRequestExecutor(requestHttp);
      default:
        //TODO 需要优化，最好抛出异常
        return null;
    }
  }

}
