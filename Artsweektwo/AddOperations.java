package BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:毛翔宇
 * @Date 2019-03-30 14:26
 */
public class AddOperations {

    ArrayList<String> res;
    String expression;
    int target;
    public void dfs(int index,long currentOperands,long previousOperands,long value,
                     List<String> ans){
        if ( index == this.expression.length()){
            if (value == this.target && currentOperands == 0){
                StringBuilder sb = new StringBuilder();
                ans.subList(1,ans.size()).forEach(v -> sb.append(v));
                res.add(sb.toString());
            }
            return;
        }
        currentOperands = currentOperands *10+Character.getNumericValue(expression.charAt(index));
        String cur_oper_str = Long.toString(currentOperands);
        if (currentOperands > 0){
            dfs(index+1,currentOperands,previousOperands,value,ans);
        }

        ans.add("+");
        ans.add(cur_oper_str);
        dfs(index+1,0,currentOperands,value+currentOperands,ans);
        ans.remove(ans.size()-1);
        ans.remove(ans.size()-1);

        //for fear that *10+5 or -10+5 appear which violate the original meaning
        if (ans.size() > 0){
            ans.add("-");
            ans.add(cur_oper_str);
            dfs(index+1,0,-currentOperands,value - currentOperands,ans);
            ans.remove(ans.size()-1);
            ans.remove(ans.size()-1);

            ans.add("*");
            ans.add(cur_oper_str);
            dfs(index+1,0,
                    currentOperands*previousOperands,
                    (value - previousOperands)+(currentOperands*previousOperands),ans);
            ans.remove(ans.size()-1);
            ans.remove(ans.size()-1);
        }
    }
    public List<String> addOperators(String num,int target){
        this.target = target;
        this.expression = num;
        this.res = new ArrayList<>();
        dfs(0,0,0,0,new ArrayList<>());
        return res;
    }
    public static void main(String[] args){
        AddOperations aot = new AddOperations();
        aot.addOperators("105",5);
        for (String ele:aot.res){
            System.out.println(ele);
        }
    }
}
