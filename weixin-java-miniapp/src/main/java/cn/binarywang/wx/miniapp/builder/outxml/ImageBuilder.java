package cn.binarywang.wx.miniapp.builder.outxml;

import cn.binarywang.wx.miniapp.bean.message.WxMaOutImageMessage;

/**
 * 图片消息builder
 *
 * @author chanjarster
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder, WxMaOutImageMessage> {

  private String mediaId;

  public ImageBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  @Override
  public WxMaOutImageMessage build() {
    WxMaOutImageMessage m = new WxMaOutImageMessage();
    setCommon(m);
    m.setMediaId(this.mediaId);
    return m;
  }

}
