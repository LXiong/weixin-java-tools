package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.test.ApiTestModule;
import cn.binarywang.wx.miniapp.api.test.TestConfig;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateMessage;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * Created by Binary Wang on 2016-10-14.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
@Guice(modules = ApiTestModule.class)
public class WxMaTemplateMsgServiceImplTest {
  @Inject
  protected WxMaService wxService;

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

    String msgId = this.wxService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    Assert.assertNotNull(msgId);
    System.out.println(msgId);
  }

}
