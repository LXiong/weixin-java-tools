package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateMessage;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * <pre>
 * 模板消息接口
 * http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
 * Created by Binary Wang on 2016-10-14.
 * @author miller.lin
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public interface WxMaTemplateMsgService {


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
