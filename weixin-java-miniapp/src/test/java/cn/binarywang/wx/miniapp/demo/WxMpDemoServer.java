package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMpConfigStorage;
import cn.binarywang.wx.miniapp.api.WxMpMessageHandler;
import cn.binarywang.wx.miniapp.api.WxMpMessageRouter;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceApacheHttpClientImpl;
import cn.binarywang.wx.miniapp.api.test.TestConstants;
import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMaInMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMaOutMessage;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class WxMpDemoServer {

  private static WxMpConfigStorage wxMpConfigStorage;
  private static WxMaService wxMaService;
  private static WxMpMessageRouter wxMpMessageRouter;

  public static void main(String[] args) throws Exception {
    init();

    Server server = new Server(8080);

    ServletHandler servletHandler = new ServletHandler();
    server.setHandler(servletHandler);

    ServletHolder endpointServletHolder = new ServletHolder(
      new WxMaPortalServlet(wxMpConfigStorage, wxMaService, wxMpMessageRouter));
    servletHandler.addServletWithMapping(endpointServletHolder, "/*");

    server.start();
    server.join();
  }

  private static void init() {
    try (InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml")) {
      WxMaDemoInMemoryConfigStorage config = WxMaDemoInMemoryConfigStorage.fromXml(is1);
      config.setAccessTokenLock(new ReentrantLock());

      wxMpConfigStorage = config;
      wxMaService = new WxMaServiceApacheHttpClientImpl();
      wxMaService.setWxMpConfigStorage(config);

      wxMpMessageRouter = new WxMpMessageRouter(wxMaService);

      wxMpMessageRouter.rule().handler(new WxMpMessageHandler() {
        @Override
        public WxMaOutMessage handle(WxMaInMessage wxMessage, Map<String, Object> context,
                                     WxMaService wxMaService1, WxSessionManager sessionManager) throws WxErrorException {
          System.out.println("收到消息：" + wxMessage.toString());
          return null;
        }
      }).next()

        .rule().async(false).content("哈哈").handler(new WxMpMessageHandler() {
        @Override
        public WxMaOutMessage handle(WxMaInMessage wxMessage, Map<String, Object> context,
                                     WxMaService wxMaService1, WxSessionManager sessionManager)
          throws WxErrorException {
          wxMaService1.getKefuService().sendKefuMessage(WxMaKefuMessage.TEXT().content("测试回复消息")
            .toUser(wxMessage.getFromUser()).build());
          return null;
        }

      }).end()

        .rule().async(false).content("图片").handler(new WxMpMessageHandler() {
        @Override
        public WxMaOutMessage handle(WxMaInMessage wxMessage, Map<String, Object> context, WxMaService wxMaService1, WxSessionManager sessionManager) throws WxErrorException {
          try {
            WxMediaUploadResult wxMediaUploadResult = wxMaService1.getMaterialService()
              .mediaUpload(WxConsts.MEDIA_IMAGE, TestConstants.FILE_JPG, ClassLoader.getSystemResourceAsStream("mm.jpeg"));
            return WxMaOutMessage
              .IMAGE()
              .mediaId(wxMediaUploadResult.getMediaId())
              .fromUser(wxMessage.getToUser())
              .toUser(wxMessage.getFromUser())
              .build();
          } catch (WxErrorException e) {
            e.printStackTrace();
          }

          return null;
        }
      }).end();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
