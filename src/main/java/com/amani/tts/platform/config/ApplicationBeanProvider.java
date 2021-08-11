package com.amani.tts.platform.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@AllArgsConstructor
public class ApplicationBeanProvider {

  private static ObjectMapper mapper;

  @Bean
  public static ObjectMapper mapper() {

    if (Objects.isNull(ApplicationBeanProvider.mapper)) {
      ApplicationBeanProvider.mapper = new ObjectMapper();

      ApplicationBeanProvider.mapper.setSerializationInclusion(Include.NON_NULL);
      ApplicationBeanProvider.mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
      ApplicationBeanProvider.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      ApplicationBeanProvider.mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

    }

    return ApplicationBeanProvider.mapper;
  }
}
