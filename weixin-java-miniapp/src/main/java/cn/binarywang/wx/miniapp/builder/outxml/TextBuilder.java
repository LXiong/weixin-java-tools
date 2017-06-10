package cn.binarywang.wx.miniapp.builder.outxml;

import cn.binarywang.wx.miniapp.bean.message.WxMaOutTextMessage;

/**
 * 文本消息builder
 *
 * @author chanjarster
 */
public final class TextBuilder extends BaseBuilder<TextBuilder, WxMaOutTextMessage> {
  private String content;

  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public WxMaOutTextMessage build() {
    WxMaOutTextMessage m = new WxMaOutTextMessage();
    setCommon(m);
    m.setContent(this.content);
    return m;
  }
}
