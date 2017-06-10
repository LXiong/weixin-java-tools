package cn.binarywang.wx.miniapp.builder.kefu;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import me.chanjar.weixin.common.api.WxConsts;

/**
 * 卡券消息builder
 * <pre>
 * 用法: WxMaKefuMessage m = WxMaKefuMessage.WXCARD().cardId(...).toUser(...).build();
 * </pre>
 *
 * @author mgcnrx11
 */
public final class WxCardBuilder extends BaseBuilder<WxCardBuilder> {
  private String cardId;

  public WxCardBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_WXCARD;
  }

  public WxCardBuilder cardId(String cardId) {
    this.cardId = cardId;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setCardId(this.cardId);
    return m;
  }
}
