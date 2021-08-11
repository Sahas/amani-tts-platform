package com.amani.tts.platform.dto.common.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorResponse extends BaseResponse {
    public ErrorResponse(Object data) {
        super(1, "error occured", data);
    }

    public ErrorResponse(String message) {
        super(1, message, null);
    }

    public ErrorResponse(int statusCode, String message) {
        super(statusCode, message, null);
    }

}
