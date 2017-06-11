package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;
import com.google.common.base.Joiner;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Binary Wang on 2016/7/21.
 */
public class WxMaUserServiceImpl implements WxMaUserService {
  private WxMaService service;

  public WxMaUserServiceImpl(WxMaService service) {
    this.service = service;
  }

  private static String jsCode2sessionUrl = "https://api.weixin.qq.com/sns/jscode2session";

  @Override
  public String getSessionKey(String jsCode) throws WxErrorException {
    final WxMaConfig config = service.getWxMaConfig();
    Map<String, String> params = new HashMap<String, String>();
    params.put("appid", config.getAppid());
    params.put("secret", config.getSecret());
    params.put("js_code", jsCode);
    params.put("grant_type", "authorization_code");

    final String result = this.service.get(jsCode2sessionUrl, Joiner.on("&").withKeyValueSeparator("=").join(params));
    return result;
  }

  @Override
  public WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) {
    return WxMaUserInfo.fromJson( WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
  }

  @Override
  public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
    final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
    System.out.println(generatedSignature);
    return generatedSignature.equals(signature);
  }

}
