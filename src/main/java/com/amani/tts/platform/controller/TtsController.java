package com.amani.tts.platform.controller;

import com.amani.tts.platform.dto.api.request.TtsConversionRequest;
import com.amani.tts.platform.dto.api.response.TtsConversionResponse;
import com.amani.tts.platform.dto.common.response.BaseResponse;
import com.amani.tts.platform.dto.common.response.ErrorResponse;
import com.amani.tts.platform.dto.common.response.SuccessResponse;
import com.amani.tts.platform.handler.TtsHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TtsController {

  private TtsHandler ttsHandler;

  public TtsController(TtsHandler ttsHandler) {
    this.ttsHandler = ttsHandler;
  }

  @CrossOrigin
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/tts/snippet",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public BaseResponse convertTextToSpeech(@RequestBody TtsConversionRequest conversionRequest) {
    try {
      TtsConversionResponse response = this.ttsHandler.processContent(conversionRequest);
      return new SuccessResponse(response);
    } catch (Exception e) {
      log.error("Exception while converting text to speech: ", e);
      return new ErrorResponse("Internal Error");
    }
  }
}
