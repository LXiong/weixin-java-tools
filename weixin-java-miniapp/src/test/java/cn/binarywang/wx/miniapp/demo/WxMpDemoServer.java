package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpConfigStorage;
import cn.binarywang.wx.miniapp.api.WxMpMessageHandler;
import cn.binarywang.wx.miniapp.api.WxMpMessageRouter;
import cn.binarywang.wx.miniapp.api.WxMpService;
import cn.binarywang.wx.miniapp.api.impl.WxMpServiceApacheHttpClientImpl;
import me.chanjar.weixin.common.api.WxConsts;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;

public class WxMpDemoServer {

  private static WxMpConfigStorage wxMpConfigStorage;
  private static WxMpService wxMpService;
  private static WxMpMessageRouter wxMpMessageRouter;

  public static void main(String[] args) throws Exception {
    initWeixin();

    Server server = new Server(8080);

    ServletHandler servletHandler = new ServletHandler();
    server.setHandler(servletHandler);

    ServletHolder endpointServletHolder = new ServletHolder(
      new WxMpEndpointServlet(wxMpConfigStorage, wxMpService,
        wxMpMessageRouter));
    servletHandler.addServletWithMapping(endpointServletHolder, "/*");

    ServletHolder oauthServletHolder = new ServletHolder(
      new WxMpOAuth2Servlet(wxMpService));
    servletHandler.addServletWithMapping(oauthServletHolder, "/oauth2/*");

    server.start();
    server.join();
  }

  private static void initWeixin() {
    try (InputStream is1 = ClassLoader
      .getSystemResourceAsStream("test-config.xml")) {
      WxMpDemoInMemoryConfigStorage config = WxMpDemoInMemoryConfigStorage
        .fromXml(is1);

      wxMpConfigStorage = config;
      wxMpService = new WxMpServiceApacheHttpClientImpl();
      wxMpService.setWxMpConfigStorage(config);

      WxMpMessageHandler logHandler = new DemoLogHandler();
      WxMpMessageHandler textHandler = new DemoTextHandler();
      WxMpMessageHandler imageHandler = new DemoImageHandler();
      WxMpMessageHandler oauth2handler = new DemoOAuth2Handler();
      DemoGuessNumberHandler guessNumberHandler = new DemoGuessNumberHandler();

      wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
      wxMpMessageRouter.rule().handler(logHandler).next().rule()
        .msgType(WxConsts.XML_MSG_TEXT).matcher(guessNumberHandler)
        .handler(guessNumberHandler).end().rule().async(false).content("哈哈")
        .handler(textHandler).end().rule().async(false).content("图片")
        .handler(imageHandler).end().rule().async(false).content("oauth")
        .handler(oauth2handler).end();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
