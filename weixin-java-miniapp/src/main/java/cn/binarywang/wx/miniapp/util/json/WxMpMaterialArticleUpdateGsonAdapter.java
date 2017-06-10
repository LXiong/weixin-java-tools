/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package cn.binarywang.wx.miniapp.util.json;

import cn.binarywang.wx.miniapp.bean.material.WxMpMaterialArticleUpdate;
import cn.binarywang.wx.miniapp.bean.material.WxMpMaterialNews;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class WxMpMaterialArticleUpdateGsonAdapter implements JsonSerializer<WxMpMaterialArticleUpdate> {

  @Override
  public JsonElement serialize(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject articleUpdateJson = new JsonObject();
    articleUpdateJson.addProperty("media_id", wxMpMaterialArticleUpdate.getMediaId());
    articleUpdateJson.addProperty("index", wxMpMaterialArticleUpdate.getIndex());
    articleUpdateJson.add("articles", WxMpGsonBuilder.create().toJsonTree(wxMpMaterialArticleUpdate.getArticles(), WxMpMaterialNews.WxMpMaterialNewsArticle.class));
    return articleUpdateJson;
  }

}
