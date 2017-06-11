package cn.binarywang.wx.miniapp.util.http.jodd;

import cn.binarywang.wx.miniapp.bean.WxMpQrcode;
import cn.binarywang.wx.miniapp.util.http.QrCodeRequestExecutor;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.MimeTypes;
import jodd.util.StringPool;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.RequestHttp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class JoddQrCodeRequestExecutor extends QrCodeRequestExecutor<HttpConnectionProvider, ProxyInfo> {
  public JoddQrCodeRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public File execute(String uri, WxMpQrcode ticket) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.bodyText(ticket.toString());
    request.withConnectionProvider(requestHttp.getRequestHttpClient());

    HttpResponse response = request.send();
    response.charset(StringPool.UTF_8);
    String contentTypeHeader = response.header("Content-Type");
    if (MimeTypes.MIME_TEXT_PLAIN.equals(contentTypeHeader)) {
      String responseContent = response.bodyText();
      throw new WxErrorException(WxError.fromJson(responseContent));
    }
    try (InputStream inputStream = new ByteArrayInputStream(response.bodyBytes())) {
      return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
    }
  }
}
