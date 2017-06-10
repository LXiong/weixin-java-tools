package cn.binarywang.wx.miniapp.builder.kefu;

import cn.binarywang.wx.miniapp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.common.api.WxConsts;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxMpKefuMessage m = WxMpKefuMessage.NEWS().mediaId("xxxxx").toUser(...).build();
 * </pre>
 *
 * @author Binary Wang
 */
public final class MpNewsBuilder extends BaseBuilder<MpNewsBuilder> {
  private String mediaId;

  public MpNewsBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_MPNEWS;
  }

  public MpNewsBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  @Override
  public WxMpKefuMessage build() {
    WxMpKefuMessage m = super.build();
    m.setMpNewsMediaId(this.mediaId);
    return m;
  }
}
