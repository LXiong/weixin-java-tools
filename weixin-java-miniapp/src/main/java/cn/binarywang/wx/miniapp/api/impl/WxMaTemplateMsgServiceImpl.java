package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaTemplateMsgService;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * <pre>
 * Created by Binary Wang on 2016-10-14.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public class WxMaTemplateMsgServiceImpl implements WxMaTemplateMsgService {
  public static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/template";
  private static final JsonParser JSON_PARSER = new JsonParser();

  private WxMaService wxMaService;

  public WxMaTemplateMsgServiceImpl(WxMaService wxMaService) {
    this.wxMaService = wxMaService;
  }

  @Override
  public String sendTemplateMsg(WxMaTemplateMessage templateMessage) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
    String responseContent = this.wxMaService.post(url, templateMessage.toJson());
    final JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
    if (jsonObject.get("errcode").getAsInt() == 0) {
      return jsonObject.get("msgid").getAsString();
    }
    throw new WxErrorException(WxError.fromJson(responseContent));
  }

}
