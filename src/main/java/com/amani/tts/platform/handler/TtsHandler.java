package com.amani.tts.platform.handler;

import com.amani.tts.platform.dto.SpeechResult;
import com.amani.tts.platform.dto.api.request.TtsConversionRequest;
import com.amani.tts.platform.dto.api.response.TtsConversionResponse;
import com.amani.tts.platform.external.ttscore.service.TtsCoreService;
import com.amani.tts.platform.service.CloudinaryUploadService;
import com.amani.tts.platform.service.ContentDownloadService;
import com.amani.tts.platform.service.HtmlContentParsingService;
import com.amani.tts.platform.service.PollyTtsService;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class TtsHandler {
  private static final int maxTextSize = 300;
  private final ContentDownloadService contentDownloadService;
  private final HtmlContentParsingService htmlContentParsingService;
  private final TtsCoreService ttsCoreService;
  private final PollyTtsService pollyTtsService;
  private final CloudinaryUploadService cloudinaryUploadService;

  public TtsHandler(
      ContentDownloadService contentDownloadService,
      HtmlContentParsingService htmlContentParsingService,
      TtsCoreService ttsCoreService,
      PollyTtsService pollyTtsService,
      CloudinaryUploadService cloudinaryUploadService) {
    this.contentDownloadService = contentDownloadService;
    this.htmlContentParsingService = htmlContentParsingService;
    this.ttsCoreService = ttsCoreService;
    this.pollyTtsService = pollyTtsService;
    this.cloudinaryUploadService = cloudinaryUploadService;
  }

  public TtsConversionResponse processContent(TtsConversionRequest conversionRequest) {
    String contentUrl = conversionRequest.getUrl();
    Document htmlDoc = contentDownloadService.downloadHtmlFromUrl(contentUrl);
    String content = htmlContentParsingService.parseAndDecodeContent(htmlDoc);
    int endIndex = Math.min(content.length(), maxTextSize);
    String trimmedContent = content.substring(0, endIndex);
    SpeechResult ttsResult = pollyTtsService.convertShortTextToSpeech(trimmedContent);
    String cloudinaryUrl =
        cloudinaryUploadService.uploadAsset(ttsResult.getData(), ttsResult.getFormat());
    return TtsConversionResponse.builder().audioUrl(cloudinaryUrl).build();
  }
}
