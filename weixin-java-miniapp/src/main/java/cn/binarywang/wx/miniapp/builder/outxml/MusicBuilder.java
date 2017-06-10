package cn.binarywang.wx.miniapp.builder.outxml;

import cn.binarywang.wx.miniapp.bean.message.WxMaOutMusicMessage;

/**
 * 音乐消息builder
 *
 * @author chanjarster
 */
public final class MusicBuilder extends BaseBuilder<MusicBuilder, WxMaOutMusicMessage> {

  private String title;
  private String description;
  private String hqMusicUrl;
  private String musicUrl;
  private String thumbMediaId;

  public MusicBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MusicBuilder description(String description) {
    this.description = description;
    return this;
  }

  public MusicBuilder hqMusicUrl(String hqMusicUrl) {
    this.hqMusicUrl = hqMusicUrl;
    return this;
  }

  public MusicBuilder musicUrl(String musicUrl) {
    this.musicUrl = musicUrl;
    return this;
  }

  public MusicBuilder thumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
    return this;
  }

  @Override
  public WxMaOutMusicMessage build() {
    WxMaOutMusicMessage m = new WxMaOutMusicMessage();
    setCommon(m);
    m.setTitle(this.title);
    m.setDescription(this.description);
    m.setHqMusicUrl(this.hqMusicUrl);
    m.setMusicUrl(this.musicUrl);
    m.setThumbMediaId(this.thumbMediaId);
    return m;
  }

}
