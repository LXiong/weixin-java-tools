package cn.binarywang.wx.miniapp.api.impl;

import cn.binarywang.wx.miniapp.api.WxMpMaterialService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.material.*;
import cn.binarywang.wx.miniapp.util.http.*;
import cn.binarywang.wx.miniapp.util.json.WxMpGsonBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.MediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Binary Wang on 2016/7/21.
 */
public class WxMpMaterialServiceImpl implements WxMpMaterialService {
  private static final String MEDIA_API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/media";
  private static final String MATERIAL_API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/material";
  private WxMaService wxMaService;

  public WxMpMaterialServiceImpl(WxMaService wxMaService) {
    this.wxMaService = wxMaService;
  }

  @Override
  public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException {
    try {
      return this.mediaUpload(mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
    } catch (IOException e) {
      e.printStackTrace();
      throw new WxErrorException(WxError.newBuilder().setErrorMsg(e.getMessage()).build());
    }
  }

  @Override
  public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/upload?type=" + mediaType;
    return this.wxMaService.execute(MediaUploadRequestExecutor.create(this.wxMaService.getRequestHttp()), url, file);
  }

  @Override
  public File mediaDownload(String media_id) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/get";
    return this.wxMaService.execute(
      MediaDownloadRequestExecutor.create(this.wxMaService.getRequestHttp(), this.wxMaService.getWxMpConfigStorage().getTmpDirFile()),
      url,
      "media_id=" + media_id);
  }

  @Override
  public WxMediaImgUploadResult mediaImgUpload(File file) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/uploadimg";
    return this.wxMaService.execute(MediaImgUploadRequestExecutor.create(this.wxMaService.getRequestHttp()), url, file);
  }

  @Override
  public WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial material) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/add_material?type=" + mediaType;
    return this.wxMaService.execute(MaterialUploadRequestExecutor.create(this.wxMaService.getRequestHttp()), url, material);
  }

  @Override
  public WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews news) throws WxErrorException {
    if (news == null || news.isEmpty()) {
      throw new IllegalArgumentException("news is empty!");
    }
    String url = MATERIAL_API_URL_PREFIX + "/add_news";
    String responseContent = this.wxMaService.post(url, news.toJson());
    return WxMpMaterialUploadResult.fromJson(responseContent);
  }

  @Override
  public InputStream materialImageOrVoiceDownload(String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return this.wxMaService.execute(MaterialVoiceAndImageDownloadRequestExecutor.create(this.wxMaService.getRequestHttp(), this.wxMaService.getWxMpConfigStorage().getTmpDirFile()), url, media_id);
  }

  @Override
  public WxMpMaterialVideoInfoResult materialVideoInfo(String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return this.wxMaService.execute(MaterialVideoInfoRequestExecutor.create(this.wxMaService.getRequestHttp()), url, media_id);
  }

  @Override
  public WxMpMaterialNews materialNewsInfo(String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return this.wxMaService.execute(MaterialNewsInfoRequestExecutor.create(this.wxMaService.getRequestHttp()), url, media_id);
  }

  @Override
  public boolean materialNewsUpdate(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/update_news";
    String responseText = this.wxMaService.post(url, wxMpMaterialArticleUpdate.toJson());
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return true;
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public boolean materialDelete(String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/del_material";
    return this.wxMaService.execute(MaterialDeleteRequestExecutor.create(this.wxMaService.getRequestHttp()), url, media_id);
  }

  @Override
  public WxMpMaterialCountResult materialCount() throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_materialcount";
    String responseText = this.wxMaService.get(url, null);
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialCountResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int offset, int count) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", WxConsts.MATERIAL_NEWS);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = this.wxMaService.post(url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialNewsBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int offset, int count) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", type);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = this.wxMaService.post(url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialFileBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

}
