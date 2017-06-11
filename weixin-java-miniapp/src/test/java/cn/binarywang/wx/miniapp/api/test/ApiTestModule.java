package cn.binarywang.wx.miniapp.api.test;

import cn.binarywang.wx.miniapp.api.WxMaConfig;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceApacheHttpClientImpl;
import com.google.inject.Binder;
import com.google.inject.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("test-config.xml")) {
      TestConfig config = TestConfig.fromXml(inputStream);
      config.setAccessTokenLock(new ReentrantLock());

      WxMaService wxService = new WxMaServiceApacheHttpClientImpl();
      wxService.setWxMaConfig(config);

      binder.bind(WxMaService.class).toInstance(wxService);
      binder.bind(WxMaConfig.class).toInstance(config);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
