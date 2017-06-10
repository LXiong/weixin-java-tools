package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpInMemoryConfigStorage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.InputStream;

/**
 * @author Daniel Qian
 */
@XStreamAlias("xml")
class WxMaDemoInMemoryConfigStorage extends WxMpInMemoryConfigStorage {

  public static WxMaDemoInMemoryConfigStorage fromXml(InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxMaDemoInMemoryConfigStorage.class);
    return (WxMaDemoInMemoryConfigStorage) xstream.fromXML(is);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
