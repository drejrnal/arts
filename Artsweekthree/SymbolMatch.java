package BackTracking;



public class SymbolMatch {

    enum Result{
        True,False
    }
    /*
    自顶向下回溯递归
     */
    public static boolean isMatch(String s,String p){
        if (p.isEmpty())
            return s.isEmpty();
        boolean firstMatch = s.charAt(0) == p.charAt(0) || p.charAt(0) == '.';
        if (p.length() >= 2 && p.charAt(1) == '*'){
            return isMatch(s,p.substring(2)) || (isMatch(s.substring(1),p) && firstMatch);
        }
        else
            return firstMatch && (isMatch(s.substring(1),p.substring(1)));
    }
    /*
    自顶向下记忆化递归
    出错实例：
    1、s:ab p:.*c mem数组越界
    2、s:aaa p:aaaa s数组下标越界
    */
    public static boolean topDownDP(int i,int j,String s,String p,Result[][] mem){
        if (i > s.length())
            return false;
        if (mem[i][j] == Result.True)
            return true;
        boolean ans;
        if (j == p.length())
            ans = i == s.length();
        else {
            boolean firstMatch = (i<s.length() && s.charAt(i) == p.charAt(j)) || p.charAt(j) == '.';
            if (j + 2 <= p.length() && p.charAt(j+1) == '*') {
                ans = topDownDP(i, j + 2, s, p, mem) ||
                        ( firstMatch && (topDownDP(i + 1, j, s, p, mem)) );
            } else
                ans = firstMatch && topDownDP(i + 1, j + 1, s, p, mem);
        }
        mem[i][j] = ans?Result.True:Result.False;
        return ans;
    }
    /*
    自底向上填表法
     */
    public static void bottomUpDP(boolean[][] dp,String s,String p){
        for(int i = s.length()-1;i>=0;i--){
            for (int j = p.length()-1;j>=0;j--){
                boolean firstMatch = i< s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
                if (j+1 < p.length() && p.charAt(j+1) == '*')
                    dp[i][j] = dp[i][j+2] || (firstMatch && dp[i+1][j]);
                else
                    dp[i][j] = firstMatch && dp[i+1][j+1];
            }
        }
    }
    public static boolean match(String s,String p){
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        /*
        循环i从s.length()-1开始就不需要下面代码
        dp[s.length()][p.length()] = true;
        dp[s.length()][p.length()-1] = p.charAt(p.length()-1) == '*'?true:false;
        dp[s.length()][p.length()-2] = p.charAt(p.length()-1) == '*'?true:false;*/
        bottomUpDP(dp,s,p);
        return dp[0][0];
    }

    

    /*
    通配符匹配 递归 超时
    */
    public static boolean wildcardMatch(String s,String p){
        if (s.isEmpty()) {
            //return p.isEmpty() || p.equals("*");
            if (p.isEmpty())
                return true;
            else {
                boolean include = true;
                for (int i = 0;i<p.length();i++) {
                    if (p.charAt(i) != '*') {
                        include = false;
                        break;
                    }
                }
                return include;
            }
        }
        if (!p.isEmpty() && p.charAt(0) == '*') {
            if (wildcardMatch(s.substring(1), p) || wildcardMatch(s,p.substring(1)))
                return true;
        }
        boolean firstM = !p.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '?');
        if (firstM && wildcardMatch(s.substring(1),p.substring(1)))
            return true;
        return false;
    }
    /*
    通配符匹配 动态规划填表法
    */
    public static void wildcardMatch(String s,String p,boolean[][] mem){
        for (int i = s.length()-1;i>=0;i--){
            for (int j = p.length()-1;j>=0;j--){
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')
                    mem[i][j] = mem[i+1][j+1];
                else if (p.charAt(j) == '*')
                    mem[i][j] = mem[i][j+1] || mem[i+1][j];
                else
                    mem[i][j] = false;
            }
        }
    }

    public static boolean wcMatch(String s,String p){
        boolean[][] cache = new boolean[s.length()+1][p.length()+1];
        cache[s.length()][p.length()] = true;
        for (int i = p.length()-1;i>=0;i--){
            if (p.charAt(i)!='*')
                break;
            cache[s.length()][i] = true;
        }
        wildcardMatch(s,p,cache);
        return cache[0][0];
    }

    public static void main(String[] args){
        String s = "adceb";
        String p = "*a*b";
        System.out.println(wcMatch(s,p));
    }
}
