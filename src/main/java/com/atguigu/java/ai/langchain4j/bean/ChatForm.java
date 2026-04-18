package com.atguigu.java.ai.langchain4j.bean;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;

/**
 * @author yangyue
 * @date 2026/4/9 21:59
 * @desc 这个实体用来封装前端用户发来的消息
 */

@Data
public class ChatForm {
    private  String memoryId;
    private String message;
}
