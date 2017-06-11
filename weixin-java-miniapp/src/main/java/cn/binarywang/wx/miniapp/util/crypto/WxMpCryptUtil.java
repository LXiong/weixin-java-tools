
package cn.binarywang.wx.miniapp.util.crypto;

import cn.binarywang.wx.miniapp.api.WxMaConfig;
import org.apache.commons.codec.binary.Base64;

public class WxMpCryptUtil extends me.chanjar.weixin.common.util.crypto.WxCryptUtil {

  public WxMpCryptUtil(WxMaConfig wxMaConfig) {
    String encodingAesKey = wxMaConfig.getAesKey();
    String token = wxMaConfig.getToken();

    this.token = token;
    this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
  }

}
