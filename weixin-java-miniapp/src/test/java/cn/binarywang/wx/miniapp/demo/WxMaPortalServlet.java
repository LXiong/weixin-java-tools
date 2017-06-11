package cn.binarywang.wx.miniapp.demo;

import cn.binarywang.wx.miniapp.api.WxMaConfig;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMpMessageRouter;
import cn.binarywang.wx.miniapp.bean.message.WxMaInMessage;
import cn.binarywang.wx.miniapp.bean.message.WxMaOutMessage;
import cn.binarywang.wx.miniapp.constant.MsgDataFormat;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WxMaPortalServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected WxMaConfig wxMaConfig;
  protected WxMaService wxMaService;
  protected WxMpMessageRouter wxMpMessageRouter;

  public WxMaPortalServlet(WxMaConfig wxMaConfig, WxMaService wxMaService,
                           WxMpMessageRouter wxMpMessageRouter) {
    this.wxMaConfig = wxMaConfig;
    this.wxMaService = wxMaService;
    this.wxMpMessageRouter = wxMpMessageRouter;
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    String signature = request.getParameter("signature");
    String nonce = request.getParameter("nonce");
    String timestamp = request.getParameter("timestamp");

    if (!this.wxMaService.checkSignature(timestamp, nonce, signature)) {
      // 消息签名不正确，说明不是公众平台发过来的消息
      response.getWriter().println("非法请求");
      return;
    }

    String echostr = request.getParameter("echostr");
    if (StringUtils.isNotBlank(echostr)) {
      // 说明是一个仅仅用来验证的请求，回显echostr
      response.getWriter().println(echostr);
      return;
    }

    String encryptType = request.getParameter("encrypt_type");
    final boolean isJson = this.wxMaConfig.getMsgDataFormat() == MsgDataFormat.JSON;
    if (StringUtils.isBlank(encryptType)) {
      // 明文传输的消息
      WxMaInMessage inMessage;
      if (isJson) {
        inMessage = WxMaInMessage.fromJson(IOUtils.toString(request.getInputStream(),
          StandardCharsets.UTF_8));
      } else {//xml
        inMessage = WxMaInMessage.fromXml(request.getInputStream());
      }

      WxMaOutMessage outMessage = this.wxMpMessageRouter.route(inMessage);
      if (outMessage != null) {
        response.getWriter().write(outMessage.toXml());
      }
      return;
    }

    if ("aes".equals(encryptType)) {
      // 是aes加密的消息
      String msgSignature = request.getParameter("msg_signature");
      WxMaInMessage inMessage;
      if (isJson) {
        inMessage = WxMaInMessage.fromEncryptedJson(request.getInputStream(),
          this.wxMaConfig, timestamp, nonce, msgSignature);
      } else {//xml
        inMessage = WxMaInMessage.fromEncryptedXml(request.getInputStream(),
          this.wxMaConfig, timestamp, nonce, msgSignature);
      }
      WxMaOutMessage outMessage = this.wxMpMessageRouter.route(inMessage);
      String out;
      if (outMessage == null) {
        out = "success";
      } else if (isJson) {
        out = outMessage.toEncryptedJson(this.wxMaConfig);
      } else {
        out = outMessage.toEncryptedXml(this.wxMaConfig);
      }

      this.log(out);
      response.getWriter().write(out);
      return;
    }

    response.getWriter().println("不可识别的加密类型");
    return;
  }

}
