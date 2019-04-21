package GraphTheory;

import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2019-03-22 10:43
 */
public class WordLadder {

    private HashMap<String, LinkedList<String>> adjacents;
    private HashMap<String,Integer> distanceMap;
    private HashSet<String> dictionary;
    private List<List<String>> solution;
    private HashMap<String,String> edgeTo;
    public WordLadder(){
        adjacents = new HashMap<>();
        distanceMap  = new HashMap<>();
        edgeTo = new HashMap<>();
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
                        edgeTo.put(adj,source);
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

    public Iterable<String> pathTo(String beginWord,String endWord){
        LinkedList<String> res = new LinkedList<>();
        if (!distanceMap.containsKey(endWord))
            return res;
        Stack<String> path = new Stack<>();
        for (String route = endWord;;route = edgeTo.get(route)){
            if (route.equals(beginWord))
                break;
            path.push(route);
        }
        path.push(beginWord);
        while (!path.isEmpty())
            res.add(path.pop());
        return res;
    }

    public LinkedList<String> findOptionsFromDict(String steorotype,Set<String> candidates){
        LinkedList<String> elected = new LinkedList<>();
        /*for (int i = 0;i<steorotype.length();i++){
            char[] steoro = steorotype.toCharArray();
            for (char j = 'a';j<='z';j++){
                    steoro[i] = j;
                    String transformed = new String(steoro);
                    if (!steorotype.equals(transformed) && candidates.contains(transformed))
                        elected.offer(transformed);
            }
        }*/
        for(String candidate:candidates){
            int diff = 0;
            for (int i = 0;i<candidate.length();i++)
                if (candidate.charAt(i) != steorotype.charAt(i))
                    diff+=1;
            if (diff == 1)
                elected.add(candidate);
        }
        return elected;
    }
    public static void main(String[] args){
        WordLadder wordLadder = new WordLadder();
        String begin = "hit";
        String end = "cog";
        String[] dictionary = {"hot","dot","dog","lot","log","cog"};
        //System.out.println(Arrays.asList(dictionary));
        List<List<String>> solution = new LinkedList<>();
        wordLadder.bfs(begin,end,Arrays.asList(dictionary));
        LinkedList<String> path = new LinkedList<>();
        path.add(begin);
        System.out.println(wordLadder.distanceMap.get(end));
        //wordLadder.dfs(begin,end,path);
        //System.out.println(wordLadder.solution);

    }
}
