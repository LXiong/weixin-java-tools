package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpMessageHandler;
import cn.binarywang.wx.miniapp.api.WxMpService;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;

/**
 * Created by qianjia on 15/1/22.
 */
public class DemoOAuth2Handler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    String href = "<a href=\"" + wxMpService.oauth2buildAuthorizationUrl(
      wxMpService.getWxMpConfigStorage().getOauth2redirectUri(),
      WxConsts.OAUTH2_SCOPE_USER_INFO, null) + "\">测试oauth2</a>";
    return WxMpXmlOutMessage.TEXT().content(href)
      .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
      .build();
  }
}
