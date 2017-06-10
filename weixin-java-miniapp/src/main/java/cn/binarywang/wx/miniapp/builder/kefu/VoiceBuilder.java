package cn.binarywang.wx.miniapp.builder.kefu;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import me.chanjar.weixin.common.api.WxConsts;

/**
 * 语音消息builder
 * <pre>
 * 用法: WxMaKefuMessage m = WxMaKefuMessage.VOICE().mediaId(...).toUser(...).build();
 * </pre>
 *
 * @author chanjarster
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder> {
  private String mediaId;

  public VoiceBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_VOICE;
  }

  public VoiceBuilder mediaId(String media_id) {
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
