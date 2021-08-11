package com.amani.tts.platform.external.http.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpstreamsConfiguration {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class BaseRestConnectionConfiguration {
    private String url;
    private int readTimeOut;
    private int connectTimeOut;
    private int writeTimeOut;
    private int maxIdleConnections;
  }

  @Data
  @Configuration
  @EqualsAndHashCode(callSuper = true)
  public class TtsCoreConnectionConfiguration extends BaseRestConnectionConfiguration {
    public TtsCoreConnectionConfiguration(@Value("${services.tts-core.base.url}") final String url,
                                                       @Value("${services.tts-core.read.timeout.ms}") final int readTimeOut,
                                                       @Value("${services.tts-core.connect.timeout.ms}") final int connectTimeOut,
                                                       @Value("${services.tts-core.write.timeout.ms}") final int writeTimeOut,
                                                       @Value("${services.tts-core.max.idle.connections}") final int maxIdleConnections) {

      super(url, readTimeOut, connectTimeOut, writeTimeOut, maxIdleConnections);
    }
  }
}
