package cn.binarywang.wx.miniapp.builder.outxml;

import cn.binarywang.wx.miniapp.bean.message.WxMaOutVoiceMessage;

/**
 * 语音消息builder
 *
 * @author chanjarster
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxMaOutVoiceMessage> {

  private String mediaId;

  public VoiceBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  @Override
  public WxMaOutVoiceMessage build() {
    WxMaOutVoiceMessage m = new WxMaOutVoiceMessage();
    setCommon(m);
    m.setMediaId(this.mediaId);
    return m;
  }

}
