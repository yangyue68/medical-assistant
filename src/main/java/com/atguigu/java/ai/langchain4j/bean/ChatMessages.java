package com.atguigu.java.ai.langchain4j.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yangyue
 * @date 2026/4/8 23:05
 * @desc 这个实体类是用来和MongoDB打交道的
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
public class ChatMessages {
    //mongoDB自己维护的ID
    @Id
    private ObjectId id;

    //用于区分不同会话的ID
    private Long memoryId;

    private String content;
}
