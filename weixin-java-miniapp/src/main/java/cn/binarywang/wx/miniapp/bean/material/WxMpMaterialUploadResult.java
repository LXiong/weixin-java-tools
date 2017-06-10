package cn.binarywang.wx.miniapp.bean.material;

import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

public class WxMpMaterialUploadResult implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -128818731449449537L;
  private String mediaId;
  private String url;

  public static WxMpMaterialUploadResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialUploadResult.class);
  }

  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "WxMpMaterialUploadResult [media_id=" + this.mediaId + ", url=" + this.url + "]";
  }

}

