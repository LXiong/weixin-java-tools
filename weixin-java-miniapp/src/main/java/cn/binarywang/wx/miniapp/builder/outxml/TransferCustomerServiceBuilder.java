package cn.binarywang.wx.miniapp.builder.outxml;

import cn.binarywang.wx.miniapp.bean.message.WxMaOutTransferKefuMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * 客服消息builder
 * <pre>
 * 用法: WxMpKefuMessage m = WxMaOutMessage.TRANSFER_CUSTOMER_SERVICE().content(...).toUser(...).build();
 * </pre>
 *
 * @author chanjarster
 */
public final class TransferCustomerServiceBuilder extends BaseBuilder<TransferCustomerServiceBuilder, WxMaOutTransferKefuMessage> {
  private String kfAccount;

  public TransferCustomerServiceBuilder kfAccount(String kf) {
    this.kfAccount = kf;
    return this;
  }

  @Override
  public WxMaOutTransferKefuMessage build() {
    WxMaOutTransferKefuMessage m = new WxMaOutTransferKefuMessage();
    setCommon(m);
    if (StringUtils.isNotBlank(this.kfAccount)) {
      WxMaOutTransferKefuMessage.TransInfo transInfo = new WxMaOutTransferKefuMessage.TransInfo();
      transInfo.setKfAccount(this.kfAccount);
      m.setTransInfo(transInfo);
    }
    return m;
  }
}
