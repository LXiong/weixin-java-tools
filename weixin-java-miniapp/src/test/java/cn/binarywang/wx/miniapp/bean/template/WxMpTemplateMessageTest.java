package cn.binarywang.wx.miniapp.bean.template;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * <pre>
 * Created by Binary Wang on 2017-3-30.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public class WxMpTemplateMessageTest {
  @Test
  public void testToJson() throws Exception {
    WxMpTemplateMessage tm = WxMpTemplateMessage.builder()
      .toUser("OPENID")
      .templateId("ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY")
      .miniProgram(new WxMpTemplateMessage.MiniProgram("xiaochengxuappid12345", "index?foo=bar"))
      .url("http://weixin.qq.com/download")
      .build();

    tm.addWxMpTemplateData(
      new WxMpTemplateData("first", "haahah", "#FF00FF"));
    tm.addWxMpTemplateData(
      new WxMpTemplateData("remark", "heihei", "#FF00FF"));

    assertEquals(tm.toJson(), "{\"touser\":\"OPENID\",\"template_id\":\"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY\",\"url\":\"http://weixin.qq.com/download\",\"miniprogram\":{\"appid\":\"xiaochengxuappid12345\",\"pagepath\":\"index?foo=bar\"},\"data\":{\"first\":{\"value\":\"haahah\",\"color\":\"#FF00FF\"},\"remark\":{\"value\":\"heihei\",\"color\":\"#FF00FF\"}}}");
  }

}
