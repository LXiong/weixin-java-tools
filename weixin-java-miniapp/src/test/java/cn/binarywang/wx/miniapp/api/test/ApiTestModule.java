package cn.binarywang.wx.miniapp.api.test;

import cn.binarywang.wx.miniapp.api.WxMpConfigStorage;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceApacheHttpClientImpl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.thoughtworks.xstream.XStream;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
    try (InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml")) {
      TestConfigStorage config = this.fromXml(TestConfigStorage.class, is1);
      config.setAccessTokenLock(new ReentrantLock());
      WxMaService wxService = new WxMaServiceApacheHttpClientImpl();
      wxService.setWxMpConfigStorage(config);

      binder.bind(WxMaService.class).toInstance(wxService);
      binder.bind(WxMpConfigStorage.class).toInstance(config);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  private <T> T fromXml(Class<T> clazz, InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.alias("xml", clazz);
    xstream.processAnnotations(clazz);
    return (T) xstream.fromXML(is);
  }

}
