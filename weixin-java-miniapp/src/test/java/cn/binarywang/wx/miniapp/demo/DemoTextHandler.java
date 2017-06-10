package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpMessageHandler;
import cn.binarywang.wx.miniapp.api.WxMpService;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlOutMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;

/**
 * Created by qianjia on 15/1/22.
 */
public class DemoTextHandler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                  WxMpService wxMpService, WxSessionManager sessionManager) {
    WxMpXmlOutTextMessage m
      = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUser())
      .toUser(wxMessage.getFromUser()).build();
    return m;
  }

}
