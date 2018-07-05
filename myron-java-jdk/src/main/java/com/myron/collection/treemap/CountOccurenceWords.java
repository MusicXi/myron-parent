package com.myron.collection.treemap;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * 统计文本出现次数,按文本顺序展示
 * @author Administrator
 *
 */
public class CountOccurenceWords {

	public static void main(String[] args) {
		String[] sentences = {"hello world", "how are you", "what would you like", "have a good visit"};
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		Random ramdom = new Random();
		for (int i = 0; i < 20; i++) {
			String[] words = sentences[ramdom.nextInt(sentences.length)].split(" ");
			for(int j = 0; j < words.length; j++) {
				int count = 0;
				String key = words[j];
				if (countMap.containsKey(key)) {
					count = countMap.get(key);
				}
				count ++;
				countMap.put(key, count);
			}
		}
		
		//遍历map结果的结果集
		for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
			System.out.println(entry.getKey() + ":"  + entry.getValue().intValue());
		}
		
	}

}
