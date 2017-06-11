package cn.binarywang.wx.miniapp.mssage;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.message.WxMaInMessage;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;

/**
 * 处理小程序推送消息的处理器接口
 */
public interface WxMpMessageHandler {

  void handle(WxMaInMessage wxMessage,
              Map<String, Object> context,
              WxMaService service,
              WxSessionManager sessionManager) throws WxErrorException;

}
