package com.amani.tts.platform.dto.common.response;

public class SuccessResponse extends BaseResponse {

    public SuccessResponse() {
        super(200, "Success");
    }

    public SuccessResponse(Object data) {
        super(200, "Success", data);
    }
}
