package com.atguigu.java.ai.langchain4j.config;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.tavily.TavilyWebSearchEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSearchConfig {

    @Value("${tavily.api-key:}")
    private String tavilyApiKey;

    @Bean
    public WebSearchEngine webSearchEngine() {
        if (tavilyApiKey == null || tavilyApiKey.isEmpty()) {
            System.err.println("警告：未配置 tavily.api-key，网络搜索功能将不可用");
            return null;
        }
        return TavilyWebSearchEngine.builder()
                .apiKey(tavilyApiKey)
                .searchDepth("basic")  // basic 或 advanced
                .includeAnswer(true)   // 是否包含AI生成的摘要
                .build();
    }

    @Bean
    public ContentRetriever webSearchContentRetriever(WebSearchEngine webSearchEngine) {
        if (webSearchEngine == null) {
            return null;
        }
        return WebSearchContentRetriever.builder()
                .webSearchEngine(webSearchEngine)
                .maxResults(3)  // RAG 阶段检索的最大结果数
                .build();
    }
}