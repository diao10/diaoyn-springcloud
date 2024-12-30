package com.diaoyn.provider.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author diaoyn
 * @ClassName SimilarityUtil
 * @Date 2024/12/30 17:36
 */
public class SimilarityUtil {

    public static final Map<String, String> SYNONYM_MAP = new HashMap<>();


    /**
     * 计算两个字符串的余弦相似度
     *
     * @param sentence1 sentence1
     * @param sentence2 sentence2
     * @return double
     */
    private static double similarity(String sentence1, String sentence2) {
        if (StrUtil.isEmpty(sentence1) || StrUtil.isEmpty(sentence2)) {
            return 0D;
        }
        //加载同义词
        String synonymPath = "synonym.txt";
        if (SYNONYM_MAP.isEmpty() && ObjectUtil.isNotNull(ResourceUtil.getResource(synonymPath))) {
            String synonym = IoUtil.read(ResourceUtil.getReader(synonymPath, StandardCharsets.UTF_8));
            for (String s : StrUtil.split(synonym, StrUtil.CRLF)) {
                List<String> wordList = StrUtil.split(s, StrUtil.SPACE);
                for (int i = 1; i < wordList.size(); i++) {
                    SYNONYM_MAP.put(wordList.get(i), wordList.get(0));
                }
            }
        }
        //分词
        JiebaSegmenter segment = new JiebaSegmenter();
        List<SegToken> tokenList1 = segment.process(sentence1, JiebaSegmenter.SegMode.INDEX);
        List<SegToken> tokenList2 = segment.process(sentence2, JiebaSegmenter.SegMode.INDEX);

        Map<String, Integer> freqMap1 =
                tokenList1.stream().map(s -> s.word).collect(Collectors.toMap(word -> SYNONYM_MAP.getOrDefault(word,
                        word), word -> 1, Integer::sum));
        Map<String, Integer> freqMap2 =
                tokenList2.stream().map(s -> s.word).collect(Collectors.toMap(word -> SYNONYM_MAP.getOrDefault(word,
                        word), word -> 1, Integer::sum));
        return cosineSimilarity(freqMap1, freqMap2);
    }

    /**
     * 计算两个map的余弦相似度
     *
     * @param freqMap1 freqMap1
     * @param freqMap2 freqMap2
     * @return double
     */
    private static double cosineSimilarity(Map<String, Integer> freqMap1, Map<String, Integer> freqMap2) {
        Set<String> allWords = new HashSet<>(freqMap1.keySet());
        allWords.addAll(freqMap2.keySet());

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (String word : allWords) {
            int freq1 = freqMap1.getOrDefault(word, 0);
            int freq2 = freqMap2.getOrDefault(word, 0);
            dotProduct += freq1 * freq2;
            normA += freq1 * freq1;
            normB += freq2 * freq2;
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
