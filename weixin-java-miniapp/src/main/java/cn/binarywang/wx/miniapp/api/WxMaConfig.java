package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.constant.MsgDataFormat;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;

import java.util.concurrent.locks.Lock;

/**
 * 微信客户端配置存储
 *
 * @author chanjarster
 */
public interface WxMaConfig {

  String getAccessToken();

  Lock getAccessTokenLock();

  boolean isAccessTokenExpired();

  /**
   * 强制将access token过期掉
   */
  void expireAccessToken();

  /**
   * 应该是线程安全的
   *
   * @param accessToken 要更新的WxAccessToken对象
   */
  void updateAccessToken(WxAccessToken accessToken);

  /**
   * 应该是线程安全的
   *
   * @param accessToken      新的accessToken值
   * @param expiresInSeconds 过期时间，以秒为单位
   */
  void updateAccessToken(String accessToken, int expiresInSeconds);

  String getAppid();

  String getSecret();

  String getToken();

  String getAesKey();

  MsgDataFormat getMsgDataFormat();

  long getExpiresTime();

  String getOauth2redirectUri();

  String getHttpProxyHost();

  int getHttpProxyPort();

  String getHttpProxyUsername();

  String getHttpProxyPassword();

  /**
   * http client builder
   *
   * @return ApacheHttpClientBuilder
   */
  ApacheHttpClientBuilder getApacheHttpClientBuilder();

  /**
   * 是否自动刷新token
   */
  boolean autoRefreshToken();

}
