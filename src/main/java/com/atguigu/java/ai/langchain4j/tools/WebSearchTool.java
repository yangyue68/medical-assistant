package com.atguigu.java.ai.langchain4j.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebSearchTool {

    @Value("${tavily.api-key:}")
    private String tavilyApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Tool(name = "webSearch", value = "搜索互联网获取实时信息，如新闻、天气、最新医疗资讯等。当用户问及实时信息或知识库中没有的内容时，使用此工具。")
    public String search(@P("搜索关键词") String query) {
        System.out.println("=== WebSearchTool 被调用 ===");
        System.out.println("搜索关键词: " + query);

        if (tavilyApiKey == null || tavilyApiKey.isEmpty()) {
            return "网络搜索功能未配置，请检查 API Key";
        }

        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 构建请求体
            String requestBody = String.format(
                    "{\"api_key\":\"%s\",\"query\":\"%s\",\"max_results\":5}",
                    tavilyApiKey, query
            );

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.tavily.com/search",
                    request,
                    String.class
            );

            // 解析结果
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode results = root.get("results");

            if (results != null && results.isArray()) {
                StringBuilder sb = new StringBuilder();
                sb.append("搜索完成，找到以下结果：\n\n");
                for (JsonNode result : results) {
                    sb.append("• ").append(result.get("title").asText()).append("\n");
                    sb.append("  ").append(result.get("url").asText()).append("\n");
                    sb.append("  ").append(result.get("content").asText()).append("\n\n");
                }
                return sb.toString();
            }

            return "未找到相关结果";

        } catch (Exception e) {
            return "搜索失败：" + e.getMessage();
        }
    }
}