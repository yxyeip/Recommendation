package util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapUtil<K,V extends  Comparable<V>> {
	
	 public  TreeMap<K , V> sortMap(HashMap<K, V> unsortedMap) {
	        Comparator<K> comparator = new ValueComparator<K, V>(unsortedMap);
	        TreeMap<K, V> result = new TreeMap<K, V>(comparator);
	        result.putAll(unsortedMap);

	        return result;
	    }
	 
	 public TreeMap<K , V> getTopKNode(HashMap<K, V> unsortedMap,int n) {
		 TreeMap<K , V>sortedMap= sortMap(unsortedMap);
		 if(n<sortedMap.size())
		 return sortedMap;
		 else {
			 TreeMap<K , V> firstKMap=new TreeMap<>();
			 for (K key : sortedMap.keySet()){
				 firstKMap.put(key,sortedMap.get(key));
				 if(--n==0)
					 break;
			    }
			 return firstKMap;
		 }
			 
	 }
}

class ValueComparator<K, V extends  Comparable<V>> implements Comparator<K> {
    HashMap<K, V> map = new HashMap<K, V>();
    public ValueComparator(HashMap<K, V> unsortedMap) {
    	this.map.putAll(map);
	}

    @Override
    public int compare(K s1, K s2) {
        return map.get(s1).compareTo(map.get(s2));
    }
}