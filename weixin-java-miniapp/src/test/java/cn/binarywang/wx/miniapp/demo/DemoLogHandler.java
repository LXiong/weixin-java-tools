package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpMessageHandler;
import cn.binarywang.wx.miniapp.api.WxMpService;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;

/**
 * Created by qianjia on 15/1/22.
 */
public class DemoLogHandler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    System.out.println(wxMessage.toString());
    return null;
  }
}
