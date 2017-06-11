package cn.binarywang.wx.miniapp.bean.message;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class WxMpXmlOutVoiceMessageTest {

  public void test() {
    WxMaOutVoiceMessage m = new WxMaOutVoiceMessage();
    m.setMediaId("ddfefesfsdfef");
    m.setCreateTime(1122l);
    m.setFromUserName("from");
    m.setToUserName("to");

    String expected = "<xml>"
      + "<ToUserName><![CDATA[to]]></ToUserName>"
      + "<FromUserName><![CDATA[from]]></FromUserName>"
      + "<CreateTime>1122</CreateTime>"
      + "<MsgDataFormat><![CDATA[voice]]></MsgDataFormat>"
      + "<Voice><MediaId><![CDATA[ddfefesfsdfef]]></MediaId></Voice>"
      + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(m.toXml().replaceAll("\\s", ""), expected.replaceAll("\\s", ""));
  }

  public void testBuild() {
    WxMaOutVoiceMessage m = WxMaOutMessage.VOICE().mediaId("ddfefesfsdfef").fromUser("from").toUser("to").build();
    String expected = "<xml>"
      + "<ToUserName><![CDATA[to]]></ToUserName>"
      + "<FromUserName><![CDATA[from]]></FromUserName>"
      + "<CreateTime>1122</CreateTime>"
      + "<MsgDataFormat><![CDATA[voice]]></MsgDataFormat>"
      + "<Voice><MediaId><![CDATA[ddfefesfsdfef]]></MediaId></Voice>"
      + "</xml>";
    System.out.println(m.toXml());
    Assert.assertEquals(
      m
        .toXml()
        .replaceAll("\\s", "")
        .replaceAll("<CreateTime>.*?</CreateTime>", ""),
      expected
        .replaceAll("\\s", "")
        .replaceAll("<CreateTime>.*?</CreateTime>", "")
    );
  }

}
