package cn.binarywang.wx.miniapp.builder.outxml;

import cn.binarywang.wx.miniapp.bean.message.WxMaOutVideoMessage;

/**
 * 视频消息builder
 *
 * @author chanjarster
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxMaOutVideoMessage> {

  private String mediaId;
  private String title;
  private String description;

  public VideoBuilder title(String title) {
    this.title = title;
    return this;
  }

  public VideoBuilder description(String description) {
    this.description = description;
    return this;
  }

  public VideoBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  @Override
  public WxMaOutVideoMessage build() {
    WxMaOutVideoMessage m = new WxMaOutVideoMessage();
    setCommon(m);
    m.setTitle(this.title);
    m.setDescription(this.description);
    m.setMediaId(this.mediaId);
    return m;
  }

}
