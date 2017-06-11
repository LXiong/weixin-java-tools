package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMaInMessage;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.mssage.WxMpMessageHandler;
import cn.binarywang.wx.miniapp.mssage.WxMpMessageRouter;
import cn.binarywang.wx.miniapp.test.TestConfig;
import com.google.common.collect.Lists;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class WxMpDemoServer {
  private static WxMaConfig wxMaConfig;
  private static WxMaService wxMaService;
  private static WxMpMessageRouter wxMpMessageRouter;
  private static String templateId;

  private static final WxMpMessageHandler logHandler = new WxMpMessageHandler() {
    @Override
    public void handle(WxMaInMessage wxMessage, Map<String, Object> context,
                       WxMaService service, WxSessionManager sessionManager) throws WxErrorException {
      System.out.println("收到消息：" + wxMessage.toString());
      service.getMsgService().sendKefuMsg(WxMaKefuMessage.TEXT().content("收到信息为：" + wxMessage.toJson())
        .toUser(wxMessage.getFromUser()).build());
    }
  };

  private static final WxMpMessageHandler textHandler = new WxMpMessageHandler() {
    @Override
    public void handle(WxMaInMessage wxMessage, Map<String, Object> context,
                       WxMaService service, WxSessionManager sessionManager)
      throws WxErrorException {
      service.getMsgService().sendKefuMsg(WxMaKefuMessage.TEXT().content("回复文本消息")
        .toUser(wxMessage.getFromUser()).build());
    }

  };

  private static final WxMpMessageHandler picHandler = new WxMpMessageHandler() {
    @Override
    public void handle(WxMaInMessage wxMessage, Map<String, Object> context,
                       WxMaService service, WxSessionManager sessionManager) throws WxErrorException {
      try {
        WxMediaUploadResult uploadResult = service.getMediaService()
          .uploadMedia("image", "png",
            ClassLoader.getSystemResourceAsStream("tmp.png"));
        service.getMsgService().sendKefuMsg(
          WxMaKefuMessage
            .IMAGE()
            .mediaId(uploadResult.getMediaId())
            .toUser(wxMessage.getFromUser())
            .build());
      } catch (WxErrorException e) {
        e.printStackTrace();
      }
    }
  };


  private static final WxMpMessageHandler qrcodeHandler = new WxMpMessageHandler() {
    @Override
    public void handle(WxMaInMessage wxMessage, Map<String, Object> context,
                       WxMaService service, WxSessionManager sessionManager) throws WxErrorException {
      try {
        final File file = service.getQrcodeService().createQrcode("123", 430);
        WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
        service.getMsgService().sendKefuMsg(
          WxMaKefuMessage
            .IMAGE()
            .mediaId(uploadResult.getMediaId())
            .toUser(wxMessage.getFromUser())
            .build());
      } catch (WxErrorException e) {
        e.printStackTrace();
      }
    }
  };

  private static final WxMpMessageHandler templateMsgHandler = new WxMpMessageHandler() {
    @Override
    public void handle(WxMaInMessage wxMessage, Map<String, Object> context,
                       WxMaService service, WxSessionManager sessionManager)
      throws WxErrorException {
      service.getMsgService().sendTemplateMsg(WxMaTemplateMessage.newBuilder()
        .templateId(templateId).data(Lists.newArrayList(
          new WxMaTemplateMessage.Data("keyword1", "339208499", "#173177")))
        .toUser(wxMessage.getFromUser())
        .formId("自己替换可用的formid")
        .build());
    }

  };

  public static void main(String[] args) throws Exception {
    init();

    Server server = new Server(8080);

    ServletHandler servletHandler = new ServletHandler();
    server.setHandler(servletHandler);

    ServletHolder endpointServletHolder = new ServletHolder(new WxMaPortalServlet(wxMaConfig, wxMaService, wxMpMessageRouter));
    servletHandler.addServletWithMapping(endpointServletHolder, "/*");

    server.start();
    server.join();
  }

  private static void init() {
    try (InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml")) {
      TestConfig config = TestConfig.fromXml(is1);
      config.setAccessTokenLock(new ReentrantLock());
      templateId = config.getTemplateId();

      wxMaConfig = config;
      wxMaService = new WxMaServiceImpl();
      wxMaService.setWxMaConfig(config);

      wxMpMessageRouter = new WxMpMessageRouter(wxMaService);

      wxMpMessageRouter.rule().handler(logHandler).next()
        .rule().async(false).content("模板").handler(templateMsgHandler).end()
        .rule().async(false).content("文本").handler(textHandler).end()
        .rule().async(false).content("图片").handler(picHandler).end()
        .rule().async(false).content("二维码").handler(qrcodeHandler).end();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
