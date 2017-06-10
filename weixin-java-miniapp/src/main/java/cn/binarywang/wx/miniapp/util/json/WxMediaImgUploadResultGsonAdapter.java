package cn.binarywang.wx.miniapp.util.json;

import cn.binarywang.wx.miniapp.bean.material.WxMediaImgUploadResult;
import com.google.gson.*;
import me.chanjar.weixin.common.util.json.GsonHelper;

import java.lang.reflect.Type;

/**
 * @author miller
 */
public class WxMediaImgUploadResultGsonAdapter implements JsonDeserializer<WxMediaImgUploadResult> {
  @Override
  public WxMediaImgUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    WxMediaImgUploadResult wxMediaImgUploadResult = new WxMediaImgUploadResult();
    JsonObject jsonObject = json.getAsJsonObject();
    if (null != jsonObject.get("url") && !jsonObject.get("url").isJsonNull()) {
      wxMediaImgUploadResult.setUrl(GsonHelper.getAsString(jsonObject.get("url")));
    }
    return wxMediaImgUploadResult;
  }
}
