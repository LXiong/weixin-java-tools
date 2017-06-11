package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.test.ApiTestModule;
import cn.binarywang.wx.miniapp.test.TestConfig;
import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateMessage;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试客服相关接口
 *
 * @author Binary Wang
 */
@Test
@Guice(modules = ApiTestModule.class)
public class WxMaMsgServiceImplTest {

  @Inject
  protected WxMaService wxService;

  public void testSendKefuMpNewsMessage() throws WxErrorException {
    TestConfig configStorage = (TestConfig) this.wxService
      .getWxMaConfig();
    WxMaKefuMessage message = new WxMaKefuMessage();
    message.setMsgType(WxConsts.CUSTOM_MSG_MPNEWS);
    message.setToUser(configStorage.getOpenid());
    message.setMpNewsMediaId("52R6dL2FxDpM9N1rCY3sYBqHwq-L7K_lz1sPI71idMg");

    this.wxService.getMsgService().sendKefuMsg(message);
  }

  public void testSendKefuMessage() throws WxErrorException {
    TestConfig configStorage = (TestConfig) this.wxService
      .getWxMaConfig();
    WxMaKefuMessage message = new WxMaKefuMessage();
    message.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
    message.setToUser(configStorage.getOpenid());
    message.setContent(
      "欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");

    this.wxService.getMsgService().sendKefuMsg(message);
  }

  public void testSendKefuMessageWithKfAccount() throws WxErrorException {
    TestConfig configStorage = (TestConfig) this.wxService
      .getWxMaConfig();
    WxMaKefuMessage message = new WxMaKefuMessage();
    message.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
    message.setToUser(configStorage.getOpenid());
    message.setKfAccount(configStorage.getKfAccount());
    message.setContent(
      "欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");

    this.wxService.getMsgService().sendKefuMsg(message);
  }
  @Test(invocationCount = 5, threadPoolSize = 3)
  public void testSendTemplateMsg() throws WxErrorException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    TestConfig config = (TestConfig) this.wxService.getWxMaConfig();

    WxMaTemplateMessage templateMessage = WxMaTemplateMessage.newBuilder()
      .toUser(config.getOpenid())
      .formId("FORMID")
      .page("index")
      .data(Lists.newArrayList(
        new WxMaTemplateMessage.Data("keyword1", "339208499", "#173177"),
        new WxMaTemplateMessage.Data("keyword2", dateFormat.format(new Date()), "#173177"),
        new WxMaTemplateMessage.Data("keyword3", "粤海喜来登酒店", "#173177"),
        new WxMaTemplateMessage.Data("keyword4", "广州市天河区天河路208号", "#173177")))
      .templateId(config.getTemplateId())
      .emphasisKeyword("keyword1.DATA")
      .build();

    String msgId = this.wxService.getMsgService().sendTemplateMsg(templateMessage);
    Assert.assertNotNull(msgId);
    System.out.println(msgId);
  }

}
