package com.amani.tts.platform.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryUploadService {

  private static final String RESULT_MAP_KEY_NAME = "secure_url";

  private static final String ASSET_UPLOAD_PREFIX_PARAM_NAME = "folder";

  private final Cloudinary cloudinary;
  private final String assetUrlPathPrefix;

  public CloudinaryUploadService(
      @Value("${asset.upload.cloudinary.cloud.name}") final String cloudName,
      @Value("${asset.upload.cloudinary.api.key}") final String apiKey,
      @Value("${asset.upload.cloudinary.api.secret}") final String apiSecret,
      @Value("${asset.upload.cloudinary.url.path.prefix}") final String assetUrlPathPrefix) {
    this.assetUrlPathPrefix = assetUrlPathPrefix;
    Map config =
        com.cloudinary.utils.ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret);
    this.cloudinary = new Cloudinary(config);
  }

  public String uploadAsset(byte[] bytes, String format) {
    try {
      Map result =
          cloudinary
              .uploader()
              .upload(
                  bytes,
                  ObjectUtils.asMap(
                      ASSET_UPLOAD_PREFIX_PARAM_NAME, assetUrlPathPrefix, "resource_type", "video"));

      return result.get(RESULT_MAP_KEY_NAME).toString();
    } catch (IOException ex) {
      throw new RuntimeException("Exception while uploading the asset to cloudinary", ex);
    }
  }
}
