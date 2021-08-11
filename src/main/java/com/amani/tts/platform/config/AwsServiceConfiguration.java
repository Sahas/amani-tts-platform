package com.amani.tts.platform.config;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AwsServiceConfiguration {
  private final AwsBaseConfiguration awsBaseConfig;

  public AwsServiceConfiguration(AwsBaseConfiguration awsBaseConfig) {
    this.awsBaseConfig = awsBaseConfig;
  }

  @Bean
  public AmazonPolly pollyClient(){
    return awsBaseConfig.apply(AmazonPollyClient.builder()).build();
  }
}
