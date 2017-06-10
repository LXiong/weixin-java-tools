package cn.binarywang.wx.miniapp.util.json;

import cn.binarywang.wx.miniapp.bean.result.WxMpUserList;
import com.google.gson.*;
import me.chanjar.weixin.common.util.json.GsonHelper;

import java.lang.reflect.Type;

public class WxUserListGsonAdapter implements JsonDeserializer<WxMpUserList> {

  @Override
  public WxMpUserList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    WxMpUserList wxMpUserList = new WxMpUserList();
    wxMpUserList.setTotal(GsonHelper.getLong(o, "total"));
    wxMpUserList.setCount(GsonHelper.getInteger(o, "count"));
    wxMpUserList.setNextOpenid(GsonHelper.getString(o, "next_openid"));
    if (o.get("data") != null && !o.get("data").isJsonNull() && !o.get("data").getAsJsonObject().get("openid").isJsonNull()) {
      JsonArray data = o.get("data").getAsJsonObject().get("openid").getAsJsonArray();
      for (int i = 0; i < data.size(); i++) {
        wxMpUserList.getOpenids().add(GsonHelper.getAsString(data.get(i)));
      }
    }
    return wxMpUserList;
  }

}
