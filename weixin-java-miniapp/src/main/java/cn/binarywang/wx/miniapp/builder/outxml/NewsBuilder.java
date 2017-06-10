package cn.binarywang.wx.miniapp.builder.outxml;

import cn.binarywang.wx.miniapp.bean.message.WxMaOutNewsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息builder
 *
 * @author chanjarster
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxMaOutNewsMessage> {

  protected final List<WxMaOutNewsMessage.Item> articles = new ArrayList<>();

  public NewsBuilder addArticle(WxMaOutNewsMessage.Item item) {
    this.articles.add(item);
    return this;
  }

  @Override
  public WxMaOutNewsMessage build() {
    WxMaOutNewsMessage m = new WxMaOutNewsMessage();
    for (WxMaOutNewsMessage.Item item : this.articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }

}
