package com.amani.tts.platform.external.ttscore.config;

import com.amani.tts.platform.external.http.client.config.UpstreamsConfiguration;
import com.amani.tts.platform.external.http.client.factory.HttpClientFactory;
import com.amani.tts.platform.external.ttscore.client.TtsCoreClient;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TtsCoreClientConfig {
  private final MeterRegistry meterRegistry;
  private final UpstreamsConfiguration.TtsCoreConnectionConfiguration configuration;

  public TtsCoreClientConfig(MeterRegistry meterRegistry,
                             UpstreamsConfiguration.TtsCoreConnectionConfiguration configuration) {
    this.meterRegistry = meterRegistry;
    this.configuration = configuration;
  }

  @Bean
  public TtsCoreClient ttsCoreClient(){
    return HttpClientFactory.jsonClient(configuration, TtsCoreClient.class, meterRegistry);
  }
}
