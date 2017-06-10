package cn.binarywang.wx.miniapp.builder.kefu;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import me.chanjar.weixin.common.api.WxConsts;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxMaKefuMessage m = WxMaKefuMessage.TEXT().content(...).toUser(...).build();
 * </pre>
 *
 * @author chanjarster
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
  private String content;

  public TextBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_TEXT;
  }

  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setContent(this.content);
    return m;
  }
}
