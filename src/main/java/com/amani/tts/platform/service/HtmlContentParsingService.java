package com.amani.tts.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HtmlContentParsingService {

  public String parseAndDecodeContent(Document htmlDoc) {
    StringBuilder content = new StringBuilder();
    String amaniIntro =
        "Hi, this article is being read by Amani AI, a revolutionary way to convert text content to audio.";
    String authorIntro = "This article is written by ";
    String articleIntro = "Here is the article.";
    String title = htmlDoc.select(".article_title").first().text();
    String description = htmlDoc.select(".article_desc").first().text();
    String author = htmlDoc.select(".article_author").first().text();

    append(content, amaniIntro);
    if (StringUtils.isNotBlank(author)) {
      append(content, authorIntro + author);
    }
    append(content, articleIntro);
    append(content, title);
    append(content, description);

    Elements contentElements = htmlDoc.select("div.content_wrapper p");
    if (CollectionUtils.isEmpty(contentElements)) {
      throw new RuntimeException("The content elements are not present in provided HTML");
    }

    for (Element contentEle : contentElements) {
      append(content, contentEle.text());
    }
    String fullContent = content.toString();
    log.info("Article Content: \n {} ", fullContent);
    return fullContent;
  }

  private void append(StringBuilder stringBuilder, String newStr) {
    if (StringUtils.isNotEmpty(newStr)) {
      stringBuilder.append(newStr);
      if (stringBuilder.charAt(stringBuilder.length() - 1) != '.') {
        stringBuilder.append('.');
      }
    }
  }
}
