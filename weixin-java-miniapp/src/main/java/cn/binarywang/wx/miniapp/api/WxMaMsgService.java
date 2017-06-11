package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.bean.kefu.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.kefu.request.WxMpKfAccountRequest;
import cn.binarywang.wx.miniapp.bean.kefu.result.*;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateMessage;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.File;
import java.util.Date;

/**
 * <pre>
 * 客服接口 ，
 * 注意：命名采用kefu拼音的原因是：其英文CustomerService如果再加上Service后缀显得有点啰嗦，如果不加又显得表意不完整。
 * </pre>
 *
 * @author Binary Wang
 */
public interface WxMaMsgService {
  String KEFU_MESSAGE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
  String TEMPLATE_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";

  /**
   * <pre>
   * 发送客服消息
   * 详情请见: <a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547&token=&lang=zh_CN">发送客服消息</a>
   * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean sendKefuMsg(WxMaKefuMessage message) throws WxErrorException;

  /**
   * <pre>
   * 发送模板消息
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
   * </pre>
   *
   * @return 消息Id
   */
  String sendTemplateMsg(WxMaTemplateMessage templateMessage) throws WxErrorException;
}
