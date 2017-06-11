package cn.binarywang.wx.miniapp.util.json;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.material.WxMediaImgUploadResult;
import cn.binarywang.wx.miniapp.bean.material.WxMpMaterialUploadResult;
import cn.binarywang.wx.miniapp.bean.result.WxMpQrCodeTicket;
import cn.binarywang.wx.miniapp.bean.result.WxMpSemanticQueryResult;
import cn.binarywang.wx.miniapp.bean.result.WxMpUser;
import cn.binarywang.wx.miniapp.bean.result.WxMpUserList;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxMpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMaKefuMessage.class, new WxMpKefuMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUser.class, new WxMpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUserList.class, new WxUserListGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpQrCodeTicket.class, new WxQrCodeTicketAdapter());
    INSTANCE.registerTypeAdapter(WxMaTemplateMessage.class, new WxMpTemplateMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpSemanticQueryResult.class, new WxMpSemanticQueryResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialUploadResult.class, new WxMpMaterialUploadResultAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
