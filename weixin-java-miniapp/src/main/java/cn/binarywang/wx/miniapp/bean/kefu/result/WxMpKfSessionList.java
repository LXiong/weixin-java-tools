package cn.binarywang.wx.miniapp.bean.kefu.result;

import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;
import com.google.gson.annotations.SerializedName;
import me.chanjar.weixin.common.util.ToStringUtils;

import java.util.List;

/**
 * @author Binary Wang
 */
public class WxMpKfSessionList {
  /**
   * 会话列表
   */
  @SerializedName("sessionlist")
  private List<WxMpKfSession> kfSessionList;

  public static WxMpKfSessionList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json,
      WxMpKfSessionList.class);
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public List<WxMpKfSession> getKfSessionList() {
    return this.kfSessionList;
  }

  public void setKfSessionList(List<WxMpKfSession> kfSessionList) {
    this.kfSessionList = kfSessionList;
  }
}
