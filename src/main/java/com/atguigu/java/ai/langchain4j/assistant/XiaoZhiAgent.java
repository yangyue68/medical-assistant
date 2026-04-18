package com.atguigu.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;


/**
 * @author yangyue
 * @date 2026/4/9 21:10
 * @desc TODO
 */

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
//        streamingChatModel = "openAiStreamingChatModel",//使用vllm部署的qwen3微调模型
        streamingChatModel ="qwenStreamingChatModel",
//        streamingChatModel = "ollamaStreamingChatModel",  // 使用 Ollama 部署的qwen3流式模型
//        chatModel = "qwenChatModel",
        tools = {"appointmentTools","webSearchTool"},
        chatMemoryProvider = "chatMemoryProviderXiaoZhi",
//        contentRetriever = "contentRetrieverXiaozhiPincone"
        contentRetriever = "contentRetrieverXiaozhiPincone"
)
public interface XiaoZhiAgent {
    @SystemMessage(fromResource = "Xiaozhi-prompt-template1.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
