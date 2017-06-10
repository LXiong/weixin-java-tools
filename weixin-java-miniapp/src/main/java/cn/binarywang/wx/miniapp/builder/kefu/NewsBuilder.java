package cn.binarywang.wx.miniapp.builder.kefu;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import me.chanjar.weixin.common.api.WxConsts;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxMaKefuMessage m = WxMaKefuMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 *
 * @author chanjarster
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<WxMaKefuMessage.WxArticle> articles = new ArrayList<>();

  public NewsBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_NEWS;
  }

  public NewsBuilder addArticle(WxMaKefuMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
