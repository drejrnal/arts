package GraphTheory;

import java.util.*;


public class WordLadder {

    private HashMap<String, LinkedList<String>> adjacents;
    private HashMap<String,Integer> distanceMap;
    private HashSet<String> dictionary;
    private List<List<String>> solution;
    public WordLadder(){
        adjacents = new HashMap<>();
        distanceMap  = new HashMap<>();
        solution = new LinkedList<>();
    }
    public void bfs(String beginWord, String endWord, List<String> wordList){
        dictionary = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        distanceMap.put(beginWord,0);
        while(!queue.isEmpty()){
            String source = queue.poll();
            LinkedList<String> options = findOptionsFromDict(source,dictionary);
            adjacents.put(source,options);
            for (String adj:options){
                if (!distanceMap.containsKey(adj)){
                    distanceMap.put(adj,1+distanceMap.get(source));
                    if (adj.equals(endWord)) {
                        edgeTo.put(adj,source);
                        continue;
                        /*
                        Attention:此处不能用break，否则存在其他adj没有注册到distanceMap中的情况
                         */
                    }
                    else {
                    
                        queue.offer(adj);
                    }
                }
            }
        }
    }

    public void dfs(String beginWord,String endWord,List<String> path){
        if (beginWord.equals(endWord)) {
            solution.add(new LinkedList<>(path));
            return;
        }
        for(String route:adjacents.get(beginWord)){
            if (distanceMap.get(route) == distanceMap.get(beginWord)+1){
                path.add(route);
                dfs(route,endWord,path);
                path.remove(path.size()-1);
            }
        }
    }

    
    public LinkedList<String> findOptionsFromDict(String steorotype,Set<String> candidates){
        LinkedList<String> elected = new LinkedList<>();
        for (int i = 0;i<steorotype.length();i++){
            char[] steoro = steorotype.toCharArray();
            for (char j = 'a';j<='z';j++){
                    steoro[i] = j;
                    String transformed = new String(steoro);
                    if (!steorotype.equals(transformed) && candidates.contains(transformed))
                        elected.offer(transformed);
            }
        }
        return elected;
    }
    
}
