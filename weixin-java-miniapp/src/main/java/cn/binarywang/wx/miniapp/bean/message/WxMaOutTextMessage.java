package cn.binarywang.wx.miniapp.bean.message;

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;

@XStreamAlias("xml")
public class WxMaOutTextMessage extends WxMaOutMessage {
  private static final long serialVersionUID = -3972786455288763361L;

  @SerializedName("Content")
  @XStreamAlias("Content")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String content;

  public WxMaOutTextMessage() {
    this.msgType = WxConsts.XML_MSG_TEXT;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }


}
