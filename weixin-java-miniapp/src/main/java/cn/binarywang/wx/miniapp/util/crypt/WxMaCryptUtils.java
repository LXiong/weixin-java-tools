package cn.binarywang.wx.miniapp.util.crypt;

import cn.binarywang.wx.miniapp.config.WxMaConfig;
import me.chanjar.weixin.common.util.crypto.PKCS7Encoder;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;

public class WxMaCryptUtils extends me.chanjar.weixin.common.util.crypto.WxCryptUtil {
  public WxMaCryptUtils(WxMaConfig wxMaConfig) {
    this.token = wxMaConfig.getToken();
    this.aesKey = Base64.decodeBase64(wxMaConfig.getAesKey() + "=");
  }

  /**
   * AES解密
   *
   * @param encryptedData 密文
   * @param ivStr         iv
   */
  public static String decrypt(String sessionKey, String encryptedData, String ivStr) {
    byte[] bizData = Base64.decodeBase64(encryptedData);
    byte[] keyByte = Base64.decodeBase64(sessionKey);
    byte[] ivByte = Base64.decodeBase64(ivStr);
    try {
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      Key sKeySpec = new SecretKeySpec(keyByte, "AES");
      // 初始化
      AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
      params.init(new IvParameterSpec(ivByte));
      cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);
      byte[] original = cipher.doFinal(bizData);
      // 去除补位字符
      byte[] result = PKCS7Encoder.decode(original);
      return new String(result, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException("aes解密失败", e);
    }
  }

}
