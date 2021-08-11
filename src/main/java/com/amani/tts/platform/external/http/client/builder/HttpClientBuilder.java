package com.amani.tts.platform.external.http.client.builder;

import com.amani.tts.platform.external.http.client.config.UpstreamsConfiguration;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.okhttp3.OkHttpMetricsEventListener;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class HttpClientBuilder {

  // Defaults as per com.squareup.okhttp.ConnectionPool.getDefault();
  private static final long OK_HTTP_DEFAULT_KEEP_ALIVE_TIME_MS = 5 * 60 * 1000;

  private HttpClientBuilder() {}

  public static OkHttpClient build(final UpstreamsConfiguration.BaseRestConnectionConfiguration configuration,
      final List<? extends Interceptor> interceptors,
      final MeterRegistry meterRegistry) {

    final OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .eventListener(OkHttpMetricsEventListener
            .builder(meterRegistry, "okhttp.requests")
            .uriMapper(req -> req.url().encodedPath())
            .build())
        .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.MILLISECONDS)
        .readTimeout(configuration.getReadTimeOut(), TimeUnit.MILLISECONDS)
        .writeTimeout(configuration.getWriteTimeOut(), TimeUnit.MILLISECONDS)
        .connectionPool(new ConnectionPool(
            configuration.getMaxIdleConnections(),
            OK_HTTP_DEFAULT_KEEP_ALIVE_TIME_MS,
            TimeUnit.MILLISECONDS));

    for (final Interceptor interceptor : interceptors) {
      builder.addInterceptor(interceptor);
    }

    return builder.build();
  }
}
