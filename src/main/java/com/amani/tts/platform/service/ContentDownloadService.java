package com.amani.tts.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ContentDownloadService {

  public Document downloadHtmlFromUrl(String url) {
    try {
      return  Jsoup.connect(url).userAgent("Mozilla").timeout(3000).get();
    } catch (IOException e) {
      throw new RuntimeException("Exception while fetching the content from url: " + url, e);
    }
  }
}
