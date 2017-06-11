package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.test.ApiTestModule;
import com.google.inject.Inject;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.File;

@Test
@Guice(modules = ApiTestModule.class)
public class WxMaQrcodeServiceImplTest {
  @Inject
  protected WxMaService wxService;

  @Test
  public void testCreateQrCode() throws Exception {
    final File qrCode = this.wxService.getQrcodeService().createQrcode("111", 122);
    System.out.println(qrCode);
  }

}
