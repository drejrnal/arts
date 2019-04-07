package TrieTree;

import java.util.*;


public class WordSearch {

    public class TrieNode{
        boolean isEnding;
        String word;
        //HashMap<Character,TrieNode> children;
        TrieNode[] children;
        public TrieNode(){
            isEnding = false;
            children = new TrieNode[26];
        }
    }

    public class TrieTree{
        private TrieNode root ;
        public TrieTree(){
            root = new TrieNode();
        }
        public void addWord(String word){
            TrieNode head = root;
            char[] characters = word.toCharArray();
            int index = 0;
            for (char c:characters){
                index = c - 'a';
                if (head.children[index] == null)
                    head.children[index] = new TrieNode();
                head = head.children[index];
            }
            head.word = word;
        }
    }

    /*
    wordSearch
    */
    public boolean exist(char[][] board, String word){
        int[] rowMove = {1,-1,0,0};
        int[] colMove = {0,0,1,-1};
        for (int i = 0;i<board.length;i++){
            for (int j = 0;j<board[0].length;j++){
                if (dfsForWordSearch(board,i,j,rowMove,colMove,word,0))
                    return true;
            }
        }
        return false;
    }

    public boolean dfsForWordSearch(char[][] board, int row, int column, int[] rowMove, int[] colMove, String word, int depth){
        if (row <0 || row >= board.length || column <0 || column >=board[0].length)
            return false;
        char c = board[row][column];
        if (c == '#')
            return false;
        if (c != word.charAt(depth))
            return false;
        if (depth == word.length()-1)
            return true;
        board[row][column] = '#';
        for (int i = 0;i<4;i++){
            if(dfsForWordSearch(board,row+rowMove[i],column+colMove[i],rowMove,colMove,word,depth+1))
                return true;
        }
        board[row][column] = c;
        return false;
    }

    /*
    wordSearch2
    */
    public List<String> findWords(char[][] board, String[] words){
        List<String> res = new LinkedList<>();
        TrieTree trieTree = new TrieTree();
        for (String word:words)
            trieTree.addWord(word);
        TrieNode head = trieTree.root;
        for(int i = 0;i<board.length;i++) {
            for (int j = 0; j < board[0].length; j++)
                dfs(board,i, j,head,res);
        }
        return res;
    }

    public void dfs(char[][] board, int row, int column, TrieNode node, List<String> res){
        if (row < 0 || row >= board.length || column <0 || column >= board[0].length)
            return;
        char c = board[row][column];
        if (c=='#')
            return;
        if (node.children[c-'a']==null)
            return;
        node = node.children[c-'a'];
        if (node.word != null) {
            res.add(node.word);
            node.word = null;
        }
        board[row][column] = '#';
        dfs(board,row+1,column,node,res);//下一行
        dfs(board,row-1,column,node,res);//上一行
        dfs(board,row,column+1,node,res);//右列
        dfs(board,row,column-1,node,res);//左列
        board[row][column] = c;
    }

    public static void main(String[] args){
        char[][] board = {
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}
        };
        char[][] board1 = {
                {'a'}
               ,{'a'}};
        char[][] board2 = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        String[] words = {"oath","pea","eat","rain"};
        String[] words1 = {"a"};
        WordSearch ws = new WordSearch();
        //System.out.println(ws.findWords(board,words));
        System.out.println(ws.exist(board2,"ABC"));
    }

}
