package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMpConfigStorage;
import cn.binarywang.wx.miniapp.api.WxMaService;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.HttpType;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

public class WxMaServiceOkHttpImpl extends AbstractWxMaServiceImpl<ConnectionPool, OkHttpProxyInfo> {
  private ConnectionPool httpClient;
  private OkHttpProxyInfo httpProxy;

  @Override
  public ConnectionPool getRequestHttpClient() {
    return httpClient;
  }

  @Override
  public OkHttpProxyInfo getRequestHttpProxy() {
    return httpProxy;
  }

  @Override
  public HttpType getRequestType() {
    return HttpType.OK_HTTP;
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
        String url = String.format(WxMaService.GET_ACCESS_TOKEN_URL, this.getWxMpConfigStorage().getAppid(),
          this.getWxMpConfigStorage().getSecret());

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectionPool(httpClient);
        //设置代理
        if (httpProxy != null) {
          clientBuilder.proxy(getRequestHttpProxy().getProxy());
        }
        //设置授权
        clientBuilder.authenticator(new Authenticator() {
          @Override
          public Request authenticate(Route route, Response response) throws IOException {
            String credential = Credentials.basic(httpProxy.getProxyUsername(), httpProxy.getProxyPassword());
            return response.request().newBuilder()
              .header("Authorization", credential)
              .build();
          }
        });
        //得到httpClient
        OkHttpClient client = clientBuilder.build();

        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
        String resultContent = response.body().string();
        WxError error = WxError.fromJson(resultContent);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
        this.getWxMpConfigStorage().updateAccessToken(accessToken.getAccessToken(),
          accessToken.getExpiresIn());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return this.getWxMpConfigStorage().getAccessToken();
  }

  @Override
  public void initHttp() {
    WxMpConfigStorage configStorage = this.getWxMpConfigStorage();

    if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
      httpProxy = new OkHttpProxyInfo(OkHttpProxyInfo.ProxyType.SOCKS5, configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort(), configStorage.getHttpProxyUsername(), configStorage.getHttpProxyPassword());
    }

    httpClient = new ConnectionPool();
  }

  @Override
  public RequestHttp getRequestHttp() {
    return this;
  }


}
