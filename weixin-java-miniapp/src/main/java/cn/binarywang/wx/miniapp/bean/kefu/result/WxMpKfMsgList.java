package cn.binarywang.wx.miniapp.bean.kefu.result;

import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;
import com.google.gson.annotations.SerializedName;
import me.chanjar.weixin.common.util.ToStringUtils;

import java.util.List;

/**
 * Created by Binary Wang on 2016/7/15.
 */
public class WxMpKfMsgList {
  @SerializedName("recordlist")
  private List<WxMpKfMsgRecord> records;

  @SerializedName("number")
  private Integer number;

  @SerializedName("msgid")
  private Long msgId;

  public static WxMpKfMsgList fromJson(String responseContent) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(responseContent, WxMpKfMsgList.class);
  }

  public List<WxMpKfMsgRecord> getRecords() {
    return this.records;
  }

  public void setRecords(List<WxMpKfMsgRecord> records) {
    this.records = records;
  }

  public Integer getNumber() {
    return this.number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Long getMsgId() {
    return this.msgId;
  }

  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }
}
