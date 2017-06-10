package cn.binarywang.wx.miniapp.builder.kefu;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import me.chanjar.weixin.common.api.WxConsts;

/**
 * 获得消息builder
 * <pre>
 * 用法: WxMaKefuMessage m = WxMaKefuMessage.IMAGE().mediaId(...).toUser(...).build();
 * </pre>
 *
 * @author chanjarster
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder> {
  private String mediaId;

  public ImageBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_IMAGE;
  }

  public ImageBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}
