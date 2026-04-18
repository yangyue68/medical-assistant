package com.atguigu.java.ai.langchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;

/**
 * @author yangyue
 * @date 2026/4/10 22:21
 * @desc 将知识库上传到Pipecone向量数据库
 */
@SpringBootTest
public class UploadKnowledgeLibrary2Pipecone {
    @Autowired
    EmbeddingStore embeddingStore;

    @Autowired
    EmbeddingModel embeddingModel;
//

    @Test
    public void test() {

        Document document1 = FileSystemDocumentLoader.loadDocument("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument("D:\\尚硅谷课程\\医疗小智\\资料\\knowledge\\神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);

        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build()
                .ingest(documents);

        System.out.println("文档上传完成");
    }

}
