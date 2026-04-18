package com.atguigu.java.ai.langchain4j;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yangyue
 * @date 2026/4/10 22:17
 * @desc TODO
 */

@SpringBootTest
public class PipeConeTest {
    @Autowired
    EmbeddingStore embeddingStore;
    @Autowired
    EmbeddingModel embeddingModel;


    @Test
    public void testPipeCone(){
        //将文本转换成向量
        TextSegment segment1 = TextSegment.from("我喜欢羽毛球");
        Embedding embedding1 = embeddingModel.embed(segment1).content();

        //存入向量数据库
        String add = embeddingStore.add(embedding1, segment1);
        System.out.println(add);


        //将文本转换成向量
        TextSegment segment2 = TextSegment.from("今天天气真好");
        Embedding embedding2 = embeddingModel.embed(segment1).content();

        //存入向量数据库
        String add2 = embeddingStore.add(embedding2, segment2);
        System.out.println(add2);

    }


    @Test
    public void testPipeCone2(){
        Embedding content = embeddingModel.embed("你最喜欢的运动是什么？").content();
        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(content)
                .maxResults(1)
                .minScore(0.8)
                .build();

        EmbeddingSearchResult<TextSegment> search = embeddingStore.search(searchRequest);

        EmbeddingMatch<TextSegment> textSegmentEmbeddingMatch = search.matches().get(0);

        System.out.println(textSegmentEmbeddingMatch.score());

        System.out.println(textSegmentEmbeddingMatch.embedded().text());
    }

}
