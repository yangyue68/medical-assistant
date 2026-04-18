package com.atguigu.java.ai.langchain4j.config;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CompositeRetrieverConfig {
    
    @Bean
    public ContentRetriever compositeContentRetriever(
            @Qualifier("contentRetrieverXiaozhiPincone") ContentRetriever contentRetrieverXiaozhiPincone) {
        
        return query -> {
            // 先检索 Pinecone 知识库
            List<Content> pineconeResults = contentRetrieverXiaozhiPincone.retrieve(query);
            if(!pineconeResults.isEmpty()){
                System.out.println("从知识库里没有找到相关内容");
            }
            else{
                System.out.println("从知识库里找到了相关内容");
            }
            return pineconeResults;
        };
    }
}