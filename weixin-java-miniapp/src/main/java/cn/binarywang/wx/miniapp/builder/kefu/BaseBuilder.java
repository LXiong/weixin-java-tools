package cn.binarywang.wx.miniapp.builder.kefu;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;

public class BaseBuilder<T> {
  protected String msgType;
  protected String toUser;

  @SuppressWarnings("unchecked")
  public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }

  public WxMaKefuMessage build() {
    WxMaKefuMessage m = new WxMaKefuMessage();
    m.setMsgType(this.msgType);
    m.setToUser(this.toUser);
    return m;
  }
}
