
package cn.binarywang.wx.miniapp.util.crypto;

import cn.binarywang.wx.miniapp.api.WxMpConfigStorage;
import org.apache.commons.codec.binary.Base64;

public class WxMpCryptUtil extends me.chanjar.weixin.common.util.crypto.WxCryptUtil {

  public WxMpCryptUtil(WxMpConfigStorage wxMpConfigStorage) {
    String encodingAesKey = wxMpConfigStorage.getAesKey();
    String token = wxMpConfigStorage.getToken();

    this.token = token;
    this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
  }

}
