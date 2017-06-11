package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.bean.message.WxMaInMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMaOutMessage;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;

/**
 * 处理微信推送消息的处理器接口
 *
 */
public interface WxMpMessageHandler {

  void handle(WxMaInMessage wxMessage,
                        Map<String, Object> context,
                        WxMaService wxMaService,
                        WxSessionManager sessionManager) throws WxErrorException;

}
