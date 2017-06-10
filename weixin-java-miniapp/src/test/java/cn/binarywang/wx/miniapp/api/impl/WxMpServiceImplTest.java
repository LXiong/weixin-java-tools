package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMpService;
import cn.binarywang.wx.miniapp.api.test.ApiTestModule;
import cn.binarywang.wx.miniapp.api.test.TestConfigStorage;
import com.google.inject.Inject;
import me.chanjar.weixin.common.api.WxConsts;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Test
@Guice(modules = ApiTestModule.class)
public class WxMpServiceImplTest {

  @Inject
  private WxMpService wxService;

  @Test
  public void testCheckSignature() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testGetAccessToken() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testGetAccessTokenBoolean() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testGetJsapiTicket() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testGetJsapiTicketBoolean() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testCreateJsapiSignature() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testCustomMessageSend() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testMassNewsUpload() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testMassVideoUpload() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testMassGroupMessageSend() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testMassOpenIdsMessageSend() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testMassMessagePreview() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testShortUrl() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testSetIndustry() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testGetIndustry() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testSemanticQuery() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testGet() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testPost() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testExecute() {
    Assert.fail("Not yet implemented");
  }

}
