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
public interface WxMpService {
  /**
   * 获取access_token
   */
  String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
  /**
   * 获得jsapi_ticket
   */
  String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";
  /**
   * 上传群发用的图文消息
   */
  String MEDIA_UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
  /**
   * 上传群发用的视频
   */
  String MEDIA_UPLOAD_VIDEO_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadvideo";
  /**
   * 分组群发消息
   */
  String MESSAGE_MASS_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
  /**
   * 按openId列表群发消息
   */
  String MESSAGE_MASS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
  /**
   * 群发消息预览接口
   */
  String MESSAGE_MASS_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview";
  /**
   * 长链接转短链接接口
   */
  String SHORTURL_API_URL = "https://api.weixin.qq.com/cgi-bin/shorturl";
  /**
   * 语义查询接口
   */
  String SEMANTIC_SEMPROXY_SEARCH_URL = "https://api.weixin.qq.com/semantic/semproxy/search";
  /**
   * 用code换取oauth2的access token
   */
  String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
  /**
   * 刷新oauth2的access token
   */
  String OAUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
  /**
   * 用oauth2获取用户信息
   */
  String OAUTH2_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";
  /**
   * 验证oauth2的access token是否有效
   */
  String OAUTH2_VALIDATE_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
  /**
   * 获取微信服务器IP地址
   */
  String GET_CALLBACK_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
  /**
   * 第三方使用网站应用授权登录的url
   */
  String QRCONNECT_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
  /**
   * oauth2授权的url连接
   */
  String CONNECT_OAUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

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
   * 获取WxMpConfigStorage 对象
   *
   * @return WxMpConfigStorage
   */
  WxMpConfigStorage getWxMpConfigStorage();

  /**
   * 注入 {@link WxMpConfigStorage} 的实现
   */
  void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider);

  /**
   * 返回客服接口方法实现类，以方便调用其各个接口
   *
   * @return WxMpKefuService
   */
  WxMpKefuService getKefuService();

  /**
   * 返回素材相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMpMaterialService
   */
  WxMpMaterialService getMaterialService();

  /**
   * 返回用户相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMpUserService
   */
  WxMpUserService getUserService();


  /**
   * 返回二维码相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMpQrcodeService
   */
  WxMpQrcodeService getQrcodeService();

  /**
   * 返回模板消息相关接口方法的实现类对象，以方便调用其各个接口
   *
   * @return WxMpTemplateMsgService
   */
  WxMpTemplateMsgService getTemplateMsgService();

  /**
   * 初始化http请求对象
   */
  void initHttp();

  /**
   * @return
   */
  RequestHttp getRequestHttp();


}
