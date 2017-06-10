package cn.binarywang.wx.miniapp.bean.kefu.result;

import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;
import com.google.gson.annotations.SerializedName;
import me.chanjar.weixin.common.util.ToStringUtils;

import java.util.List;

/**
 * @author Binary Wang
 */
public class WxMpKfOnlineList {
  @SerializedName("kf_online_list")
  private List<WxMpKfInfo> kfOnlineList;

  public static WxMpKfOnlineList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfOnlineList.class);
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public List<WxMpKfInfo> getKfOnlineList() {
    return this.kfOnlineList;
  }

  public void setKfOnlineList(List<WxMpKfInfo> kfOnlineList) {
    this.kfOnlineList = kfOnlineList;
  }
}
