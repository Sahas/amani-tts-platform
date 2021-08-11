package com.amani.tts.platform.external.http.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.IOException;

public final class LoggingInterceptor implements Interceptor {

  private static final String DEBUG = "debug";
  private static final String TRANSACTION_ID = "tid";

  private final Logger logger = org.slf4j.LoggerFactory.getLogger(LoggingInterceptor.class);

  public static final LoggingInterceptor DEFAULT = new LoggingInterceptor();

  private static final HttpLoggingInterceptor BASIC_LOGGER =
    new HttpLoggingInterceptor(LoggingInterceptor.DEFAULT.new DebugLoggingInterceptor()).setLevel(Level.BASIC);

  private static final HttpLoggingInterceptor BODY_LOGGER =
    new HttpLoggingInterceptor(LoggingInterceptor.DEFAULT.new DebugLoggingInterceptor()).setLevel(Level.BODY);

  private LoggingInterceptor() {
  }

  @Override
  public Response intercept(final Chain chain) throws IOException {

    Response response = null;
    final Request request = chain.request();
    final boolean debug = Boolean.parseBoolean(request.header(LoggingInterceptor.DEBUG));

    MDC.put(DEBUG, String.valueOf(debug));
    MDC.put(LoggingInterceptor.TRANSACTION_ID, request.header(LoggingInterceptor.TRANSACTION_ID));

    if (debug) {
      response = LoggingInterceptor.BODY_LOGGER.intercept(chain);
    } else if (logger.isDebugEnabled()) {
      response = LoggingInterceptor.BASIC_LOGGER.intercept(chain);
    } else {
      response = chain.proceed(request);
    }

    MDC.remove(LoggingInterceptor.TRANSACTION_ID);
    MDC.remove(DEBUG);

    return response;
  }

  class DebugLoggingInterceptor implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(final String message) {
      logger.debug(message);
    }
  }

}
