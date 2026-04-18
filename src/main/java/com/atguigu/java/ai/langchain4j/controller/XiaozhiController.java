package com.atguigu.java.ai.langchain4j.controller;

import com.atguigu.java.ai.langchain4j.assistant.XiaoZhiAgent;
import com.atguigu.java.ai.langchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Tag(name="北京协和医学院诊疗助手")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {

    @Autowired
    private XiaoZhiAgent xiaoZhiAgent;

    @Operation(summary = "医疗咨询")
    @PostMapping(value = "/chat", produces = "text/plain;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        // 添加日志，确认请求到达
        System.out.println("收到请求: memoryId=" + chatForm.getMemoryId() + ", message=" + chatForm.getMessage());

        // 处理 memoryId 可能为 null 的情况
        Long memoryId = chatForm.getMemoryId() != null ? Long.parseLong(chatForm.getMemoryId()) : System.currentTimeMillis();

        try {
            return xiaoZhiAgent.chat(memoryId, chatForm.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.just("抱歉，系统出现错误：" + e.getMessage());
        }
    }
}