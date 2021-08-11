package com.amani.tts.platform.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsSyncClientBuilder;
import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Slf4j
public class AwsBaseConfiguration {

  private final Regions region;

  private final AWSStaticCredentialsProvider credentialsProvider;

  // Localstack properties
  private final String awsEndpointUrl;

  public AwsBaseConfiguration(
    @Value("${aws.region}") final String region,
    @Value("${aws.access.token}") final String accessToken,
    @Value("${aws.secret.token}") final String secretToken,
    @Value("${aws.endpoint.url}") final String awsEndpointUrl) {

    this.region = Regions.fromName(region);
    this.awsEndpointUrl = awsEndpointUrl;

    BasicAWSCredentials credentials = new BasicAWSCredentials(accessToken, secretToken);
    this.credentialsProvider = new AWSStaticCredentialsProvider(credentials);
  }

  public <Builder extends AwsSyncClientBuilder<Builder, Client>, Client> Builder apply(
    final AwsSyncClientBuilder<Builder, Client> builder) {

    if (StringUtils.isNotEmpty(awsEndpointUrl)) {
      log.info(
        "Configuring {} with endpointOverride {} & region {}",
        builder.getClass().getSimpleName(),
        awsEndpointUrl,
        getRegion());
      builder.withEndpointConfiguration(
        new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
          awsEndpointUrl, getRegion().getName()));
    } else {
      log.info("Configuring {} with region {}", builder.getClass().getSimpleName(), getRegion());
      builder.withRegion(region);
    }

    return builder.withCredentials(credentialsProvider);
  }
}
