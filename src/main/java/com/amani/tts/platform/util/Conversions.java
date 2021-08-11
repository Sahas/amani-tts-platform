package com.amani.tts.platform.util;

import com.amani.tts.platform.config.ApplicationBeanProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Slf4j
public final class Conversions {

  private Conversions() {
  }

  public static <T> String asJson(final T t) {
    try {
      if (Objects.isNull(t)) {
        return StringUtils.EMPTY;
      } else {
        return ApplicationBeanProvider.mapper().writeValueAsString(t);
      }
    } catch (final Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  public static <T> T asObject(final String jsonString, final Class<T> clazz) {
    try {
      if (StringUtils.isEmpty(jsonString)) {
        return null;
      } else {
        return ApplicationBeanProvider.mapper().readValue(jsonString, clazz);
      }
    } catch (final Exception exception) {
      Conversions.log.error("Error converting JSON to POJO {}", jsonString);
      throw new RuntimeException(exception);
    }
  }

  public static <T> T asObject(final Object object, final Class<T> clazz) {
    try {
      if (Objects.isNull(object)) {
        return null;
      } else {
        return ApplicationBeanProvider.mapper().convertValue(object, clazz);
      }
    } catch (final Exception exception) {
      log.error("Error converting JSON to POJO {}", object);
      throw new RuntimeException(exception);
    }
  }

  public static <T> T asObject(final String jsonString, final TypeReference<T> typeRef) {
    try {
      if (StringUtils.isEmpty(jsonString)) {
        return null;
      } else {
        return ApplicationBeanProvider.mapper().readValue(jsonString, typeRef);
      }
    } catch (final Exception exception) {
      Conversions.log.error("Error converting JSON to POJO {}", jsonString);
      throw new RuntimeException(exception);
    }
  }

  public static <T> T convert(final Object obj, final TypeReference<T> typeRef) {
    try {
      if (Objects.isNull(obj)) {
        return null;
      } else {
        return ApplicationBeanProvider.mapper().convertValue(obj, typeRef);
      }
    } catch (final Exception exception) {
      throw new RuntimeException(exception);
    }
  }

}
