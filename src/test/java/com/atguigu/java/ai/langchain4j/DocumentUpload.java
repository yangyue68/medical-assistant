package com.atguigu.java.ai.langchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DocumentUpload{

    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    private EmbeddingModel embeddingModel;



    @Test
    public void uploadDocument(){
        List<String>list=new ArrayList<>();
        list.add("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\医院信息.md");
        list.add("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\科室信息.md");
        list.add("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\神经内科.md");
        list.add("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\医院信息.md");
        list.add("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\科室信息.md");
        list.add("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\神经内科.md");

        System.out.println("开始上传文档...");
        for(int i=0;i<list.size();i++){
            String filePath=list.get(i);
            Document document = FileSystemDocumentLoader.loadDocument(filePath);

            // 1. 生成查询向量
            Embedding queryEmbedding = embeddingModel.embed(document.text()).content();

            // 2. 使用 search 方法检索相似文档
            List<EmbeddingMatch<TextSegment>> existing = embeddingStore.search(
                    EmbeddingSearchRequest.builder()
                            .queryEmbedding(queryEmbedding)
                            .maxResults(1)
                            .minScore(0.95)
                            .build()
            ).matches();

            // 3. 判断是否已存在
            if (existing.isEmpty()) {
                // 不存在，执行上传
                embeddingStore.add(queryEmbedding, TextSegment.from(document.text()));
                System.out.println("文档上传成功: " + filePath);
            } else {
                System.out.println("文档已存在（相似度: " + existing.get(0).score() + "），跳过上传");
            }

        }

        System.out.println("所有文档上传完成！");
    }


}