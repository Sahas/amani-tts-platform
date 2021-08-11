package com.amani.tts.platform.external.http.client.factory;

import com.amani.tts.platform.external.http.client.builder.HttpClientBuilder;
import com.amani.tts.platform.external.http.client.config.UpstreamsConfiguration;
import com.amani.tts.platform.external.http.interceptor.LoggingInterceptor;
import io.micrometer.core.instrument.MeterRegistry;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Arrays;

public final class HttpClientFactory {

  private static final JacksonConverterFactory JSON_CONVERTER_FACTORY = JacksonConverterFactory.create();
  private HttpClientFactory() {}

  public static <T> T jsonClient(
      final UpstreamsConfiguration.BaseRestConnectionConfiguration configuration,
      final Class<T> clazz,
      final MeterRegistry meterRegistry) {

    return new Retrofit.Builder()
        .baseUrl(configuration.getUrl())
        .addConverterFactory(HttpClientFactory.JSON_CONVERTER_FACTORY)
        .client(
            HttpClientBuilder.build(
                configuration, Arrays.asList(LoggingInterceptor.DEFAULT), meterRegistry))
        .build()
        .create(clazz);
  }

}
