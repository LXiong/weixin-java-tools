package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpInMemoryConfigStorage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;

import java.io.InputStream;

/**
 * @author Daniel Qian
 */
@XStreamAlias("xml")
class WxMpDemoInMemoryConfigStorage extends WxMpInMemoryConfigStorage {

  public static WxMpDemoInMemoryConfigStorage fromXml(InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxMpDemoInMemoryConfigStorage.class);
    return (WxMpDemoInMemoryConfigStorage) xstream.fromXML(is);
  }

  @Override
  public String toString() {
    return "SimpleWxConfigProvider [appId=" + this.appId + ", secret=" + this.secret + ", accessToken=" + this.accessToken
      + ", expiresTime=" + this.expiresTime + ", token=" + this.token + ", aesKey=" + this.aesKey + "]";
  }

}
