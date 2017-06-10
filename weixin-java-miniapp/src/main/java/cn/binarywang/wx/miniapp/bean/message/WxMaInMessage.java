package cn.binarywang.wx.miniapp.bean.message;

import cn.binarywang.wx.miniapp.api.WxMpConfigStorage;
import cn.binarywang.wx.miniapp.util.crypto.WxMpCryptUtil;
import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;
import cn.binarywang.wx.miniapp.util.xml.XStreamTransformer;
import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import me.chanjar.weixin.common.util.ToStringUtils;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * </pre>
 */
@XStreamAlias("xml")
public class WxMaInMessage implements Serializable {
  private static final long serialVersionUID = -3586245291677274914L;

  @SerializedName("ToUserName")
  @XStreamAlias("ToUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String toUser;

  @SerializedName("FromUserName")
  @XStreamAlias("FromUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String fromUser;

  @SerializedName("CreateTime")
  @XStreamAlias("CreateTime")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private Integer createTime;

  @SerializedName("MsgType")
  @XStreamAlias("MsgType")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String msgType;

  // 文本消息
  @SerializedName("Content")
  @XStreamAlias("Content")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String content;

  @SerializedName("MsgId")
  @XStreamAlias("MsgId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private Long msgId;

  // 图片消息
  @SerializedName("PicUrl")
  @XStreamAlias("PicUrl")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String picUrl;

  @SerializedName("MediaId")
  @XStreamAlias("MediaId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String mediaId;

  // 事件消息
  @SerializedName("Event")
  @XStreamAlias("Event")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String event;

  @SerializedName("SessionFrom")
  @XStreamAlias("SessionFrom")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String sessionFrom;

  public static WxMaInMessage fromXml(String xml) {
    return XStreamTransformer.fromXml(WxMaInMessage.class, xml);
  }

  public static WxMaInMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxMaInMessage.class, is);
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml      密文
   * @param wxMpConfigStorage 配置存储器对象
   * @param timestamp         时间戳
   * @param nonce             随机串
   * @param msgSignature      签名串
   */
  public static WxMaInMessage fromEncryptedXml(String encryptedXml,
                                               WxMpConfigStorage wxMpConfigStorage, String timestamp, String nonce,
                                               String msgSignature) {
    String plainText = new WxMpCryptUtil(wxMpConfigStorage).decrypt(msgSignature, timestamp, nonce, encryptedXml);
    return fromXml(plainText);
  }

  public static WxMaInMessage fromEncryptedXml(InputStream is, WxMpConfigStorage wxMpConfigStorage, String timestamp,
                                               String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, StandardCharsets.UTF_8), wxMpConfigStorage,
        timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public static WxMaInMessage fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMaInMessage.class);
  }

  public String getToUser() {
    return toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getFromUser() {
    return fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public Integer getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Integer createTime) {
    this.createTime = createTime;
  }

  public String getMsgType() {
    return msgType;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getMsgId() {
    return msgId;
  }

  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }

  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getSessionFrom() {
    return sessionFrom;
  }

  public void setSessionFrom(String sessionFrom) {
    this.sessionFrom = sessionFrom;
  }

  public static WxMaInMessage fromEncryptedJson(InputStream inputStream, WxMpConfigStorage configStorage,
                                                String timestamp, String nonce, String msgSignature) {
    try {
      final String encryptedJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
      //String plainText = new WxMpCryptUtil(configStorage).decrypt(msgSignature, timestamp, nonce, encryptedJson);
      return fromJson(encryptedJson);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
