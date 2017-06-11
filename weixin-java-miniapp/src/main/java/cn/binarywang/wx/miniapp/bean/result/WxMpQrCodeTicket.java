package cn.binarywang.wx.miniapp.bean.result;

import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 换取二维码的Ticket
 *
 * @author chanjarster
 */
public class WxMpQrCodeTicket implements Serializable {
  private static final long serialVersionUID = 5777119669111011584L;
  private String path;
  private int width = 430;

  public WxMpQrCodeTicket() {
  }

  public WxMpQrCodeTicket(String path, int width) {
    this.path = path;
    this.width = width;
  }

  public static WxMpQrCodeTicket fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpQrCodeTicket.class);
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public String toString() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
}
