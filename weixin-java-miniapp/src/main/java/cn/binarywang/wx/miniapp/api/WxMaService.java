package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.bean.*;
import cn.binarywang.wx.miniapp.bean.result.*;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;

/**
 * 微信API的Service
 */
public interface WxMaService {
  /**
   * 获取access_token
   */
  String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
  /**
   * 长链接转短链接接口
   */
  String SHORTURL_API_URL = "https://api.weixin.qq.com/cgi-bin/shorturl";
  /**
   * 语义查询接口
   */
  String SEMANTIC_SEMPROXY_SEARCH_URL = "https://api.weixin.qq.com/semantic/semproxy/search";

  /**
   * <pre>
   * 验证消息的确来自微信服务器
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319&token=&lang=zh_CN
   * </pre>
   */
  boolean checkSignature(String timestamp, String nonce, String signature);

  /**
   * 获取access_token, 不强制刷新access_token
   *
   * @see #getAccessToken(boolean)
   */
  String getAccessToken() throws WxErrorException;

  /**
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   *
   * 另：本service的所有方法都会在access_token过期是调用此方法
   *
   * 程序员在非必要情况下尽量不要主动调用此方法
   *
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN
   * </pre>
   *
   * @param forceRefresh 强制刷新
   */
  String getAccessToken(boolean forceRefresh) throws WxErrorException;

  /**
   * <pre>
   * 长链接转短链接接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=长链接转短链接接口
   * </pre>
   */
  String shortUrl(String long_url) throws WxErrorException;

  /**
   * <pre>
   * 语义查询接口
   * 详情请见：http://mp.weixin.qq.com/wiki/index.php?title=语义理解
   * </pre>
   */
  WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   */
  String get(String url, String queryParam) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   */
  String post(String url, String postData) throws WxErrorException;

  /**
   * <pre>
   * Service没有实现某个API的时候，可以用这个，
   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
   * 可以参考，{@link MediaUploadRequestExecutor}的实现方法
   * </pre>
   */
  <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException;

  /**
   * 获取代理对象
   */
  //HttpHost getRequestHttpProxy();

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   */
  void setRetrySleepMillis(int retrySleepMillis);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，最大重试次数
   * 默认：5次
   * </pre>
   */
  void setMaxRetryTimes(int maxRetryTimes);

  /**
   * 获取WxMaConfig 对象
   *
   * @return WxMaConfig
   */
  WxMaConfig getWxMaConfig();

  /**
   * 注入 {@link WxMaConfig} 的实现
   */
  void setWxMaConfig(WxMaConfig wxConfigProvider);

  /**
   * 返回客服接口方法实现类，以方便调用其各个接口
   *
   * @return WxMpKefuService
   */
  WxMpKefuService getKefuService();

  /**
   * 返回素材相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMaMediaService
   */
  WxMaMediaService getMediaService();

  /**
   * 返回用户相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMpUserService
   */
  WxMpUserService getUserService();


  /**
   * 返回二维码相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMaQrcodeService
   */
  WxMaQrcodeService getQrcodeService();

  /**
   * 返回模板消息相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMaTemplateMsgService
   */
  WxMaTemplateMsgService getTemplateMsgService();

  /**
   * 初始化http请求对象
   */
  void initHttp();

  /**
   * @return
   */
  RequestHttp getRequestHttp();


}
