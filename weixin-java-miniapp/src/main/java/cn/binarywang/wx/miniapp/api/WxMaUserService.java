package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * 用户管理相关操作接口
 *
 * @author Binary Wang
 */
public interface WxMaUserService {
  /**
   * 获取sessionKey
   * @param jsCode 登录时获取的 code
   */
  String getSessionKey(String jsCode) throws WxErrorException;

  /**
   * 解密用户敏感数据
   * @param sessionKey 会话密钥
   * @param encryptedData 明文
   * @param ivStr 加密算法的初始向量
   */
  WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr);

  /**
   * 验证用户信息完整性
   * @param sessionKey 会话密钥
   * @param rawData 微信用户基本信息
   * @param signature 数据签名
   */
  boolean checkUserInfo(String sessionKey, String rawData, String signature);
}
