package util;

import java.util.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MapUtil<K, V extends Comparable<V>> {

	public TreeMap<K, V> getTopKNode(TreeMap<K, V> map, int n) {
		TreeMap<K, V> topKNode = new TreeMap<K, V>();
		List<Entry<K, V>> list = new ArrayList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			// 大到小
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		for (Entry<K, V> e : list) {
			// System.out.println(e.getKey()+":"+e.getValue());
			if (n > 0) {
				topKNode.put(e.getKey(), e.getValue());
				n--;
			}
		}
		return topKNode;
	}

	public List<Entry<K, V>> sortByValue(TreeMap<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			// 大到小
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return list;
	}
}
