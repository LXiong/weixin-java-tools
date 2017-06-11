package cn.binarywang.wx.miniapp.mssage;

import cn.binarywang.wx.miniapp.bean.message.WxMaInMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxMpMessageMatcher {

  /**
   * 消息是否匹配某种模式
   *
   * @param message
   */
  boolean match(WxMaInMessage message);

}
