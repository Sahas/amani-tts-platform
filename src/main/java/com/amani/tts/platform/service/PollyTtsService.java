package com.amani.tts.platform.service;

import com.amani.tts.platform.dto.SpeechResult;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.model.Engine;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.VoiceId;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class PollyTtsService {
  private final AmazonPolly polly;

  public PollyTtsService(AmazonPolly pollyClient) {
    this.polly = pollyClient;
  }

  public SpeechResult convertShortTextToSpeech(String text) {
    try {
      SynthesizeSpeechRequest request = new SynthesizeSpeechRequest();
      request
          .withEngine(Engine.Neural)
          .withText(text)
          .withLanguageCode("en-US")
          .withOutputFormat(OutputFormat.Mp3)
          .withVoiceId(VoiceId.Matthew);
      SynthesizeSpeechResult result = this.polly.synthesizeSpeech(request);

      byte[] data = IOUtils.toByteArray(result.getAudioStream());

      return SpeechResult.builder().data(data).format(OutputFormat.Mp3.toString()).build();
    } catch (Exception ex) {
      throw new RuntimeException("Exception while converting text to speech", ex);
    }
  }
}
