package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpMessageHandler;
import cn.binarywang.wx.miniapp.api.WxMpService;
import cn.binarywang.wx.miniapp.api.test.TestConstants;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlOutImageMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;

public class DemoImageHandler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                  WxMpService wxMpService, WxSessionManager sessionManager) {
    try {
      WxMediaUploadResult wxMediaUploadResult = wxMpService.getMaterialService()
        .mediaUpload(WxConsts.MEDIA_IMAGE, TestConstants.FILE_JPG, ClassLoader.getSystemResourceAsStream("mm.jpeg"));
      WxMpXmlOutImageMessage m
        = WxMpXmlOutMessage
        .IMAGE()
        .mediaId(wxMediaUploadResult.getMediaId())
        .fromUser(wxMessage.getToUser())
        .toUser(wxMessage.getFromUser())
        .build();
      return m;
    } catch (WxErrorException e) {
      e.printStackTrace();
    }

    return null;
  }
}
