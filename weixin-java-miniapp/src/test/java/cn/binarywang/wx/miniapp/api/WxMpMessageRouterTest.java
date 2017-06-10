package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.bean.message.WxMaInMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMaOutMessage;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.StandardSessionManager;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * 测试消息路由器
 *
 * @author chanjarster
 */
@Test
public class WxMpMessageRouterTest {

  @Test(enabled = false)
  public void prepare(boolean async, StringBuffer sb, WxMpMessageRouter router) {
    router
      .rule()
      .async(async)
      .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1").content("CONTENT_1")
      .handler(new WxEchoMpMessageHandler(sb, "COMBINE_4"))
      .end()
      .rule()
      .async(async)
      .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK).eventKey("KEY_1")
      .handler(new WxEchoMpMessageHandler(sb, "COMBINE_3"))
      .end()
      .rule()
      .async(async)
      .msgType(WxConsts.XML_MSG_TEXT).event(WxConsts.EVT_CLICK)
      .handler(new WxEchoMpMessageHandler(sb, "COMBINE_2"))
      .end()
      .rule().async(async).msgType(WxConsts.XML_MSG_TEXT).handler(new WxEchoMpMessageHandler(sb, WxConsts.XML_MSG_TEXT)).end()
      .rule().async(async).event(WxConsts.EVT_CLICK).handler(new WxEchoMpMessageHandler(sb, WxConsts.EVT_CLICK)).end()
      .rule().async(async).eventKey("KEY_1").handler(new WxEchoMpMessageHandler(sb, "KEY_1")).end()
      .rule().async(async).content("CONTENT_1").handler(new WxEchoMpMessageHandler(sb, "CONTENT_1")).end()
      .rule().async(async).rContent(".*bc.*").handler(new WxEchoMpMessageHandler(sb, "abcd")).end()
      .rule().async(async).handler(new WxEchoMpMessageHandler(sb, "ALL")).end();
  }

  @Test(dataProvider = "messages-1")
  public void testSync(WxMaInMessage message, String expected) {
    StringBuffer sb = new StringBuffer();
    WxMpMessageRouter router = new WxMpMessageRouter(null);
    prepare(false, sb, router);
    router.route(message);
    Assert.assertEquals(sb.toString(), expected);
  }

  @Test(dataProvider = "messages-1")
  public void testAsync(WxMaInMessage message, String expected) throws InterruptedException {
    StringBuffer sb = new StringBuffer();
    WxMpMessageRouter router = new WxMpMessageRouter(null);
    prepare(true, sb, router);
    router.route(message);
    Thread.sleep(500l);
    Assert.assertEquals(sb.toString(), expected);
  }

  public void testConcurrency() throws InterruptedException {
    final WxMpMessageRouter router = new WxMpMessageRouter(null);
    router.rule().handler(new WxMpMessageHandler() {
      @Override
      public WxMaOutMessage handle(WxMaInMessage wxMessage, Map<String, Object> context, WxMaService wxMaService,
                                   WxSessionManager sessionManager) {
        return null;
      }
    }).end();

    final WxMaInMessage m = new WxMaInMessage();
    Runnable r = new Runnable() {
      @Override
      public void run() {
        router.route(m);
        try {
          Thread.sleep(1000l);
        } catch (InterruptedException e) {
        }
      }
    };
    for (int i = 0; i < 10; i++) {
      new Thread(r).start();
    }

    Thread.sleep(1000l * 2);
  }

  @DataProvider(name = "messages-1")
  public Object[][] messages2() {
    WxMaInMessage message1 = new WxMaInMessage();
    message1.setMsgType(WxConsts.XML_MSG_TEXT);

    WxMaInMessage message2 = new WxMaInMessage();
    message2.setEvent(WxConsts.EVT_CLICK);

    WxMaInMessage message4 = new WxMaInMessage();
    message4.setContent("CONTENT_1");

    WxMaInMessage message5 = new WxMaInMessage();
    message5.setContent("BLA");

    WxMaInMessage message6 = new WxMaInMessage();
    message6.setContent("abcd");

    WxMaInMessage c2 = new WxMaInMessage();
    c2.setMsgType(WxConsts.XML_MSG_TEXT);
    c2.setEvent(WxConsts.EVT_CLICK);

    WxMaInMessage c3 = new WxMaInMessage();
    c3.setMsgType(WxConsts.XML_MSG_TEXT);
    c3.setEvent(WxConsts.EVT_CLICK);

    WxMaInMessage c4 = new WxMaInMessage();
    c4.setMsgType(WxConsts.XML_MSG_TEXT);
    c4.setEvent(WxConsts.EVT_CLICK);
    c4.setContent("CONTENT_1");

    return new Object[][]{
      new Object[]{message1, WxConsts.XML_MSG_TEXT + ","},
      new Object[]{message2, WxConsts.EVT_CLICK + ","},
      new Object[]{message4, "CONTENT_1,"},
      new Object[]{message5, "ALL,"},
      new Object[]{message6, "abcd,"},
      new Object[]{c2, "COMBINE_2,"},
      new Object[]{c3, "COMBINE_3,"},
      new Object[]{c4, "COMBINE_4,"}
    };

  }

  @DataProvider
  public Object[][] standardSessionManager() {

    // 故意把session存活时间变短，清理更频繁
    StandardSessionManager ism = new StandardSessionManager();
    ism.setMaxInactiveInterval(1);
    ism.setProcessExpiresFrequency(1);
    ism.setBackgroundProcessorDelay(1);

    return new Object[][]{
      new Object[]{ism}
    };

  }

  @Test(dataProvider = "standardSessionManager")
  public void testSessionClean1(StandardSessionManager ism) throws InterruptedException {

    // 两个同步请求，看是否处理完毕后会被清理掉
    final WxMpMessageRouter router = new WxMpMessageRouter(null);
    router.setSessionManager(ism);
    router
      .rule().async(false).handler(new WxSessionMessageHandler()).next()
      .rule().async(false).handler(new WxSessionMessageHandler()).end();

    WxMaInMessage msg = new WxMaInMessage();
    msg.setFromUser("abc");
    router.route(msg);

    Thread.sleep(2000l);
    Assert.assertEquals(ism.getActiveSessions(), 0);

  }

  @Test(dataProvider = "standardSessionManager")
  public void testSessionClean2(StandardSessionManager ism) throws InterruptedException {

    // 1个同步,1个异步请求，看是否处理完毕后会被清理掉
    {
      final WxMpMessageRouter router = new WxMpMessageRouter(null);
      router.setSessionManager(ism);
      router
        .rule().async(false).handler(new WxSessionMessageHandler()).next()
        .rule().async(true).handler(new WxSessionMessageHandler()).end();

      WxMaInMessage msg = new WxMaInMessage();
      msg.setFromUser("abc");
      router.route(msg);

      Thread.sleep(2000l);
      Assert.assertEquals(ism.getActiveSessions(), 0);
    }
    {
      final WxMpMessageRouter router = new WxMpMessageRouter(null);
      router.setSessionManager(ism);
      router
        .rule().async(true).handler(new WxSessionMessageHandler()).next()
        .rule().async(false).handler(new WxSessionMessageHandler()).end();

      WxMaInMessage msg = new WxMaInMessage();
      msg.setFromUser("abc");
      router.route(msg);

      Thread.sleep(2000l);
      Assert.assertEquals(ism.getActiveSessions(), 0);
    }

  }

  @Test(dataProvider = "standardSessionManager")
  public void testSessionClean3(StandardSessionManager ism) throws InterruptedException {

    // 2个异步请求，看是否处理完毕后会被清理掉
    final WxMpMessageRouter router = new WxMpMessageRouter(null);
    router.setSessionManager(ism);
    router
      .rule().async(true).handler(new WxSessionMessageHandler()).next()
      .rule().async(true).handler(new WxSessionMessageHandler()).end();

    WxMaInMessage msg = new WxMaInMessage();
    msg.setFromUser("abc");
    router.route(msg);

    Thread.sleep(2000l);
    Assert.assertEquals(ism.getActiveSessions(), 0);

  }

  @Test(dataProvider = "standardSessionManager")
  public void testSessionClean4(StandardSessionManager ism) throws InterruptedException {

    // 一个同步请求，看是否处理完毕后会被清理掉
    {
      final WxMpMessageRouter router = new WxMpMessageRouter(null);
      router.setSessionManager(ism);
      router
        .rule().async(false).handler(new WxSessionMessageHandler()).end();

      WxMaInMessage msg = new WxMaInMessage();
      msg.setFromUser("abc");
      router.route(msg);

      Thread.sleep(2000l);
      Assert.assertEquals(ism.getActiveSessions(), 0);
    }

    {
      final WxMpMessageRouter router = new WxMpMessageRouter(null);
      router.setSessionManager(ism);
      router
        .rule().async(true).handler(new WxSessionMessageHandler()).end();

      WxMaInMessage msg = new WxMaInMessage();
      msg.setFromUser("abc");
      router.route(msg);

      Thread.sleep(2000l);
      Assert.assertEquals(ism.getActiveSessions(), 0);
    }
  }

  public static class WxEchoMpMessageHandler implements WxMpMessageHandler {

    private StringBuffer sb;
    private String echoStr;

    public WxEchoMpMessageHandler(StringBuffer sb, String echoStr) {
      this.sb = sb;
      this.echoStr = echoStr;
    }

    @Override
    public WxMaOutMessage handle(WxMaInMessage wxMessage, Map<String, Object> context, WxMaService wxMaService,
                                 WxSessionManager sessionManager) {
      this.sb.append(this.echoStr).append(',');
      return null;
    }

  }

  public static class WxSessionMessageHandler implements WxMpMessageHandler {

    @Override
    public WxMaOutMessage handle(WxMaInMessage wxMessage, Map<String, Object> context, WxMaService wxMaService,
                                 WxSessionManager sessionManager) {
      sessionManager.getSession(wxMessage.getFromUser());
      return null;
    }

  }

}
