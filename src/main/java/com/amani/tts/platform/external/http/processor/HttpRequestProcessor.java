package com.amani.tts.platform.external.http.processor;

import com.amani.tts.platform.exception.RetryableException;
import org.springframework.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Response;

public final class HttpRequestProcessor {

  private HttpRequestProcessor() {}

  public static <T> T process(final Call<T> request) {
    try {
      final Response<T> response = request.execute();
      if (response.isSuccessful()) {
        return response.body();
      } else if (response.code() == HttpStatus.TOO_MANY_REQUESTS.value()) {
        throw new RetryableException(response.errorBody().string());
      } else {
        throw new RuntimeException(response.errorBody().string());
      }
    } catch (final RuntimeException exception) {
      throw exception;
    } catch (final Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
