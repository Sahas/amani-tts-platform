package com.amani.tts.platform.external.ttscore.service;

import com.amani.tts.platform.dto.common.response.BaseResponse;
import com.amani.tts.platform.external.ttscore.client.TtsCoreClient;
import com.amani.tts.platform.external.ttscore.dto.TtsCoreRequest;
import com.amani.tts.platform.util.Conversions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TtsCoreService {
  private final TtsCoreClient ttsCoreClient;

  public TtsCoreService(TtsCoreClient ttsCoreClient) {
    this.ttsCoreClient = ttsCoreClient;
  }

  public void convertTextToSpeech(String text) {
    try {
      String[] sentences = text.split("\\.");
      List<List<String>> sentenceBatches = ListUtils.partition(Arrays.asList(sentences), 5);
      for (List<String> sentenceBatch : sentenceBatches) {
        String partialText = StringUtils.join(sentenceBatch.toArray(),  ".");
        BaseResponse response =
            ttsCoreClient.convertTts(TtsCoreRequest.builder().text(partialText).build()).execute().body();
        log.info("Response from TTS Core client: {}", Conversions.asJson(response));
      }

    } catch (Exception e) {
      log.error("Exception while making a request to ttsCoreClient ");
      throw new RuntimeException(e);
    }
  }
}
