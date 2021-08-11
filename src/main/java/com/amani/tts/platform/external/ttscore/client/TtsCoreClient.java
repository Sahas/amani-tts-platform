package com.amani.tts.platform.external.ttscore.client;

import com.amani.tts.platform.dto.common.response.BaseResponse;
import com.amani.tts.platform.external.ttscore.dto.TtsCoreRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TtsCoreClient {

  @POST("/conversion/tts/v1/snippet")
  Call<BaseResponse> convertTts(@Body TtsCoreRequest coreRequest);
}
