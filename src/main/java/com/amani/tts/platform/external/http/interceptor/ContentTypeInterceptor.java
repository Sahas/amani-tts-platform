package com.amani.tts.platform.external.http.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public final class ContentTypeInterceptor implements Interceptor {

  private final String contentType;

  public ContentTypeInterceptor(final String contentType) {
    this.contentType = contentType;
  }

  @Override
  public Response intercept(final Chain chain) throws IOException {
    final Request request = chain.request();

    final Request newRequest = request.newBuilder()
        .addHeader("Content-Type", contentType)
        .addHeader("Accepts", contentType)
        .build();

    return chain.proceed(newRequest);
  }

}
