package com.amani.tts.platform.dto.api.request;

import com.amani.tts.platform.dto.bo.TextSourceFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TtsConversionRequest {
  private int orgId;
  private long contentTemplateId;
  private TextSourceFormat textSourceFormat;
  private String url;
}
