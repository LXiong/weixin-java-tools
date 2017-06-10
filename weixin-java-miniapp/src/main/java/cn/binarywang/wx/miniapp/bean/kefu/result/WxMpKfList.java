package cn.binarywang.wx.miniapp.bean.kefu.result;

import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;
import com.google.gson.annotations.SerializedName;
import me.chanjar.weixin.common.util.ToStringUtils;

import java.util.List;

/**
 * @author Binary Wang
 */
public class WxMpKfList {
  @SerializedName("kf_list")
  private List<WxMpKfInfo> kfList;

  public static WxMpKfList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfList.class);
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public List<WxMpKfInfo> getKfList() {
    return this.kfList;
  }

  public void setKfList(List<WxMpKfInfo> kfList) {
    this.kfList = kfList;
  }
}
