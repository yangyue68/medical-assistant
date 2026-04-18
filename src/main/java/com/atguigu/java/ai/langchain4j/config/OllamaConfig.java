package com.atguigu.java.ai.langchain4j.config;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OllamaConfig {

    @Bean
    @Primary
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:3b")
                .temperature(0.7)
                .topP(0.9)
                .numCtx(4096)
                // 关键：设置自定义请求头，指定 UTF-8 编码
                .customHeaders(new java.util.HashMap<String, String>() {{
                    put("Content-Type", "application/json; charset=utf-8");
                    put("Accept-Charset", "UTF-8");
                }})
                .build();
    }

    @Bean
    @Primary
    public OllamaStreamingChatModel ollamaStreamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:3b")
                .temperature(0.7)
                .topP(0.9)
                .numCtx(4096)
                .customHeaders(new java.util.HashMap<String, String>() {{
                    put("Content-Type", "application/json; charset=utf-8");
                    put("Accept-Charset", "UTF-8");
                }})
                .build();
    }
}