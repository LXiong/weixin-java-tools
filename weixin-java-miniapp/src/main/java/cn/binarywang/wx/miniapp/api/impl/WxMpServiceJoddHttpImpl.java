package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMpConfigStorage;
import cn.binarywang.wx.miniapp.api.WxMpService;
import jodd.http.*;
import jodd.http.net.SocketHttpConnectionProvider;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.HttpType;
import me.chanjar.weixin.common.util.http.RequestHttp;

import java.util.concurrent.locks.Lock;

/**
 * jodd-http方式实现
 */
public class WxMpServiceJoddHttpImpl extends AbstractWxMpServiceImpl<HttpConnectionProvider, ProxyInfo> {
  private HttpConnectionProvider httpClient;
  private ProxyInfo httpProxy;

  @Override
  public HttpConnectionProvider getRequestHttpClient() {
    return httpClient;
  }

  @Override
  public ProxyInfo getRequestHttpProxy() {
    return httpProxy;
  }

  @Override
  public HttpType getRequestType() {
    return HttpType.JODD_HTTP;
  }

  @Override
  public void initHttp() {

    WxMpConfigStorage configStorage = this.getWxMpConfigStorage();

    if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
      httpProxy = new ProxyInfo(ProxyInfo.ProxyType.HTTP, configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort(), configStorage.getHttpProxyUsername(), configStorage.getHttpProxyPassword());
    }

    httpClient = JoddHttp.httpConnectionProvider;
  }

  @Override
  public RequestHttp getRequestHttp() {
    return this;
  }


  @Override
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    Lock lock = this.getWxMpConfigStorage().getAccessTokenLock();
    try {
      lock.lock();

      if (forceRefresh) {
        this.getWxMpConfigStorage().expireAccessToken();
      }

      if (this.getWxMpConfigStorage().isAccessTokenExpired()) {
        String url = String.format(WxMpService.GET_ACCESS_TOKEN_URL, this.getWxMpConfigStorage().getSecret());

        HttpRequest request = HttpRequest.get(url);

        if (this.getRequestHttpProxy() != null) {
          SocketHttpConnectionProvider provider = new SocketHttpConnectionProvider();
          provider.useProxy(getRequestHttpProxy());

          request.withConnectionProvider(provider);
        }
        HttpResponse response = request.send();
        String resultContent = response.bodyText();
        WxError error = WxError.fromJson(resultContent);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
        this.getWxMpConfigStorage().updateAccessToken(accessToken.getAccessToken(),
          accessToken.getExpiresIn());
      }
    } finally {
      lock.unlock();
    }
    return this.getWxMpConfigStorage().getAccessToken();
  }

}
