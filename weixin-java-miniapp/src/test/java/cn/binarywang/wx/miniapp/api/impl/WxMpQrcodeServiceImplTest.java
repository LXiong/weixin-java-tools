package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.test.ApiTestModule;
import cn.binarywang.wx.miniapp.bean.result.WxMpQrCodeTicket;
import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.File;

/**
 * 测试用户相关的接口
 *
 * @author chanjarster
 */
@Test(groups = "qrCodeAPI")
@Guice(modules = ApiTestModule.class)
public class WxMpQrcodeServiceImplTest {
  @Inject
  protected WxMaService wxService;

  @DataProvider
  public Object[][] sceneIds() {
    return new Object[][]{{-1}, {0}, {1}, {200000}};
  }

  @Test(dataProvider = "sceneIds")
  public void testQrCodeCreateTmpTicket(int sceneId) throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateTmpTicket(sceneId, null);
    Assert.assertNotNull(ticket.getUrl());
    Assert.assertNotNull(ticket.getTicket());
    Assert.assertTrue(ticket.getExpire_seconds() != -1);
    System.out.println(ticket);
  }

  @Test(dataProvider = "sceneIds")
  public void testQrCodeCreateLastTicket(int sceneId) throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateLastTicket(sceneId);
    Assert.assertNotNull(ticket.getUrl());
    Assert.assertNotNull(ticket.getTicket());
    Assert.assertTrue(ticket.getExpire_seconds() == -1);
    System.out.println(ticket);
  }

  public void testQrCodePicture() throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateLastTicket(1);
    File file = this.wxService.getQrcodeService().qrCodePicture(ticket);
    Assert.assertNotNull(file);
    System.out.println(file.getAbsolutePath());
  }

  public void testQrCodePictureUrl() throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateLastTicket(1);
    String url = this.wxService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
    Assert.assertNotNull(url);
    System.out.println(url);
  }

}
