//package com.diaoyn.alone.util;
//
//import com.sun.tools.javac.Main;
//
//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
//import java.util.regex.Pattern;
//
///**
// * @author diaoyn
// * @ClassName Util
// * @Date 2025/6/27 17:26
// */
//public class Util {
// //购物车
//    private static class good{
//        public int v;  //物品的价格
//        public int p;  //物品的重要度
//        public int q;  //物品的主附件ID
//
//        public int a1=0;   //附件1ID
//        public int a2=0;   //附件2ID
//
//        public good(int v, int p, int q) {
//            this.v = v;
//            this.p = p;
//            this.q = q;
//        }
//
//        public void setA1(int a1) {
//            this.a1 = a1;
//        }
//
//        public void setA2(int a2) {
//            this.a2 = a2;
//        }
//    }
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int money = sc.nextInt();
//        int n = sc.nextInt();
//        if(n<=0||money<=0) System.out.println(0);
//
//        good[] Gs = new good[n+1];
//        for (int i = 1; i <= n; i++) {
//            int v = sc.nextInt();
//            int p = sc.nextInt();
//            int q = sc.nextInt();
//            Gs[i] = new good(v,p,q);
//
//            if(q>0){
//                if(Gs[q].a1==0){
//                    Gs[q].setA1(i);
//                }else {
//                    Gs[q].setA2(i);
//                }
//            }
//        }
//
//        int[][] dp = new int[n+1][money+1];
//        for (int i = 1; i <= n; i++) {
//            int v=0,v1=0,v2=0,v3=0,tempdp=0,tempdp1=0,tempdp2=0,tempdp3=0;
//
//            v = Gs[i].v;
//
//            tempdp = Gs[i].p*v; //只有主件
//
//            if(Gs[i].a1!=0){//主件加附件1
//                v1 = Gs[Gs[i].a1].v+v;
//                tempdp1 = tempdp + Gs[Gs[i].a1].v*Gs[Gs[i].a1].p;
//            }
//
//            if(Gs[i].a2!=0){//主件加附件2
//                v2 = Gs[Gs[i].a2].v+v;
//                tempdp2 = tempdp + Gs[Gs[i].a2].v*Gs[Gs[i].a2].p;
//            }
//
//            if(Gs[i].a1!=0&&Gs[i].a2!=0){//主件加附件1和附件2
//                v3 = Gs[Gs[i].a1].v+Gs[Gs[i].a2].v+v;
//                tempdp3 = tempdp + Gs[Gs[i].a1].v*Gs[Gs[i].a1].p + Gs[Gs[i].a2].v*Gs[Gs[i].a2].p;
//            }
//
//            for(int j=1; j<=money; j++){
//                if(Gs[i].q > 0) {   //当物品i是附件时,相当于跳过
//                    dp[i][j] = dp[i-1][j];
//                } else {
//                    dp[i][j] = dp[i-1][j];
//                    if(j>=v&&v!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v]+tempdp);
//                    if(j>=v1&&v1!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v1]+tempdp1);
//                    if(j>=v2&&v2!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v2]+tempdp2);
//                    if(j>=v3&&v3!=0) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v3]+tempdp3);
//                }
//            }
//        }
//        System.out.println(dp[n][money]);
//
//
//    }
//
//
//
////坐标移动
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNext()) {
//            System.out.println(myAns(in.next()));
//        }
//    }
//
//    private static String myAns(String str) {
//        int x = 0, y = 0;
//        String[] strs = str.split(";");
//        for(String s : strs) {
//            if( s == null || s.trim().length() == 0) {
//                continue;
//            }
//            String di = s.substring(0,1);
//            if(!("A".equals(di) || "D".equals(di) || "W".equals(di) || "S".equals(di)) ){
//                continue;
//            }
//            int mv = 0;
//            try{
//                mv = Integer.valueOf(s.substring(1));
//            }catch(Exception e){
//                continue;
//            }
//            if("A".equals(di)){
//                x -= mv;
//            } else if("D".equals(di)){
//                x += mv;
//            } else if("W".equals(di)){
//                y += mv;
//            } else if("S".equals(di)){
//                y -= mv;
//            }
//        }
//        return x + "," + y;
//
//}
//
////密码验证合格程序
//
//    public static void main(String[] arg){
//        Scanner sc = new Scanner(System.in);
//        while(sc.hasNext()){
//            String str = sc.next();
//            if(str.length() <= 8){
//                System.out.println("NG");
//                continue;
//            }
//            if(getMatch(str)){
//                System.out.println("NG");
//                continue;
//            }
//            if(getString(str, 0, 3)){
//                System.out.println("NG");
//                continue;
//            }
//            System.out.println("OK");
//        }
//    }
//    // 校验是否有重复子串
//    private static boolean getString(String str, int l, int r) {
//        if (r >= str.length()) {
//            return false;
//        }
//        if (str.substring(r).contains(str.substring(l, r))) {
//            return true;
//        } else {
//            return getString(str,l+1,r+1);
//        }
//    }
//    // 检查是否满足正则
//    private static boolean getMatch(String str){
//        int count = 0;
//        Pattern p1 = Pattern.compile("[A-Z]");
//        if(p1.matcher(str).find()){
//            count++;
//        }
//        Pattern p2 = Pattern.compile("[a-z]");
//        if(p2.matcher(str).find()){
//            count++;
//        }
//        Pattern p3 = Pattern.compile("[0-9]");
//        if(p3.matcher(str).find()){
//            count++;
//        }
//        Pattern p4 = Pattern.compile("[^a-zA-Z0-9]");
//        if(p4.matcher(str).find()){
//            count++;
//        }
//        if(count >= 3){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    // 合唱队
//         public static void main(String[] args) {
//            Scanner sc = new Scanner(System.in);
//            while (sc.hasNext()) {
//                int n = sc.nextInt();
//                int[] arr = new int[n];
//                for (int i = 0; i < n; i++) {
//                    arr[i] = sc.nextInt();
//                }
//
//                int[] left = new int[n]; //存储每个数左边小于其的数的个数
//                int[] right = new int[n];//存储每个数右边小于其的数的个数
//                left[0] = 1;            //最左边的数设为1
//                right[n - 1] = 1;        //最右边的数设为1
//                //计算每个位置左侧的最长递增
//                for (int i = 0; i < n; i++) {
//                    left[i] = 1;
//                    for (int j = 0; j < i; j++) {
//                        if (arr[i] > arr[j]) {   //动态规划
//                            left[i] = Math.max(left[j] + 1, left[i]);
//                        }
//                    }
//                }
//                //计算每个位置右侧的最长递减
//                for (int i = n - 1; i >= 0; i--) {
//                    right[i] = 1;
//                    for (int j = n - 1; j > i; j--) {
//                        if (arr[i] > arr[j]) {   //动态规划
//                            right[i] = Math.max(right[i], right[j] + 1);
//                        }
//                    }
//                }
//                // 记录每个位置的值
//                int[] result = new int[n];
//                for (int i = 0; i < n; i++) {
//                    //位置 i计算了两次 所以需要－1
//                    result[i] = left[i] + right[i] - 1; //两个都包含本身
//                }
//
//                //找到最大的满足要求的值
//                int max = 1;
//                for (int i = 0; i < n; i++) {
//                    max = Math.max(result[i],max);
//                }
//                System.out.println(n - max);
//            }
//
//        }
//
//        //字符串排序
//        public static String sort(String str) {
//            // 先将英文字母收集起来
//            List<Character> letters = new ArrayList<>();
//            for (char ch : str.toCharArray()) {
//                if (Character.isLetter(ch)) {
//                    letters.add(ch);
//                }
//            }
//            // 将英文字母先排序好
//            letters.sort(new Comparator<Character>() {
//                public int compare(Character o1, Character o2) {
//                    return Character.toLowerCase(o1) - Character.toLowerCase(o2);
//                }
//            });
//            // 若是非英文字母则直接添加
//            StringBuilder result = new StringBuilder();
//            for (int i = 0, j = 0; i < str.length(); i++) {
//                if (Character.isLetter(str.charAt(i))) {
//                    result.append(letters.get(j++));
//                }
//                else {
//                    result.append(str.charAt(i));
//                }
//            }
//            return result.toString();
//        }
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        while (in.hasNextLine()) {
//            String str = in.nextLine();
//            String res = sort(str);
//            System.out.println(res);
//        }
//    }
//
//    //查找兄弟单词
//    public static void main(String[] args) {
//
//        Scanner scanner = new Scanner(System.in);
//
//        while (scanner.hasNext()){
//            String[] ss = scanner.nextLine().split(" ");
//            Integer a = Integer.parseInt(ss[0]);
//            String x = ss[ss.length-2];
//            Integer k = Integer.parseInt(ss[ss.length-1]);
//            List<String> list = new ArrayList<>();
//
//            for (int i = 1; i <=a ; i++) {
//                if (isBrother(x,ss[i])){
//                    list.add(ss[i]);
//                }
//            }
//            int size = list.size();
//            System.out.println(size);
//            if (size>=k){
//                Collections.sort(list);
//                System.out.println(list.get(k-1));
//            }
//
//        }
//    }
//    public static boolean isBrother(String x,String y){
//        if (x.length()!=y.length()||y.equals(x)){
//            return false;
//        }
//        char[] s = x.toCharArray();
//        char[] j= y.toCharArray();
//        Arrays.sort(s);
//        Arrays.sort(j);
//        return new String(s).equals(new String(j));
//
//
//    }
//
//
////    字符串加解密
//    public static void main(String[] args){
//        Scanner in = new Scanner(System.in);
//        while(in.hasNext()){
//            System.out.println(encode(in.nextLine()));
//            System.out.println(decode(in.nextLine()));
//        }
//    }
//
//    //加密函数
//    private static String encode(String code){
//        char[] t = code.toCharArray();    //将String对象转换为字符数组
//        for(int i=0; i < t.length; i++){
//            if(t[i]>='a' && t[i]<'z')
//                t[i] = (char)(t[i] - 'a' + 'A' + 1);
//            else if(t[i] == 'z')
//                t[i] = 'A';
//            else if(t[i]>='A' && t[i]<'Z')
//                t[i] = (char)(t[i] - 'A' + 'a' + 1);
//            else if(t[i] == 'Z')
//                t[i] = 'a';
//            else if(t[i]>='0' && t[i]<'9')
//                t[i] = (char)(t[i]+1);
//            else if(t[i] == '9')
//                t[i] = '0';
//        }
//        return String.valueOf(t);
//    }
//
//    //解密函数
//    private static String decode(String password){
//        char[] t = password.toCharArray();
//        for(int i=0; i < t.length; i++){
//            if(t[i]>'a' && t[i]<='z')
//                t[i] = (char)(t[i] - 'a' + 'A' - 1);
//            else if(t[i] == 'a')
//                t[i] = 'Z';
//            else if(t[i]>'A' && t[i]<='Z')
//                t[i] = (char)(t[i] - 'A' + 'a' - 1);
//            else if(t[i] == 'A')
//                t[i] = 'z';
//            else if(t[i]>'0' && t[i]<='9')
//                t[i] = (char)(t[i]-1);
//            else if(t[i] == '0')
//                t[i] = '9';
//        }
//        return String.valueOf(t);
//    }
//
//
////    密码截取
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String s = sc.nextLine();
//        System.out.println(solution(s));
//    }
//
//    private static int solution(String s) {
//        int res = 0;
//        for(int i = 0; i < s.length(); i++) {
//            // ABA型
//            int len1 = longest(s, i, i);
//            // ABBA型
//            int len2 = longest(s, i, i + 1);
//            res = Math.max(res, len1 > len2 ? len1 : len2);
//        }
//        return res;
//    }
//
//    private static int longest(String s, int l, int r) {
//        while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
//            l--;
//            r++;
//        }
//        return r - l - 1;
//    }
//
////    整数与IP地址间的转换
//
//    public String convert(String str) {
//        // ipv4 -> int
//        if (str.contains(".")) {
//            String[] fields = str.split("\\.");
//            long result = 0;
//            for (int i = 0; i < N; i++) {
//                result = result * 256 + Integer.parseInt(fields[i]);
//            }
//            return "" + result;
//        }
//        // int -> ipv4
//        else {
//            long ipv4 = Long.parseLong(str);
//            String result = "";
//            for (int i = 0; i < N; i++) {
//                result = ipv4 % 256 + "." + result;
//                ipv4 /= 256;
//            }
//            return result.substring(0, result.length() - 1);
//        }
//    }
//
//    public static void main(String[] args) {
//        Main solution = new Main();
//        Scanner in = new Scanner(System.in);
//        while (in.hasNext()) {
//            String str = in.next();
//            String res = solution.convert(str);
//            System.out.println(res);
//        }
//    }
//
//    //字符串加密
//    public static void main(String args[]) {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            String s1 = sc.nextLine().toUpperCase();
//            String s2 = sc.nextLine();
//            char[] chars1 = s1.toCharArray();
//            char[] chars2 = s2.toCharArray();
//            LinkedHashSet<Character> set = new LinkedHashSet();
//            for (int i = 0; i < chars1.length; i++) {
//                set.add(chars1[i]);
//            }
//            int k = 0;
//            while (set.size() < 26) {
//                char a = (char) ('A' + k);
//                set.add(a);
//                k++;
//            }
//            ArrayList<Character> list = new ArrayList<>(set);
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < chars2.length; i++) {
//                if (chars2[i] == ' ') {
//                    sb.append(chars2[i]);
//                } else if (chars2[i] < 'a') {
//                    int n = (int) (chars2[i] - 'A');
//                    char c = list.get(n);
//                    sb.append(c);
//                } else {
//                    int n = (int) (chars2[i] - 'a');
//                    char c = (char)(list.get(n)+'a'-'A');
//                    sb.append(c);
//                }
//
//            }
//
//            System.out.println(sb.toString());
//        }
//    }
//
//
////    求小球落地5次后所经历的路程和第5次反弹的高度
//public static void main(String[] args) throws IOException {
//    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//    String s;
//    while((s = bf.readLine()) != null){
//        double height = Double.parseDouble(s);
//        System.out.println(height+(height/2)*2+(height/4)*2+(height/8)*2+(height/16)*2);
//        System.out.println(height/32);
//    }
//
////    称砝码
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            HashSet<Integer> set = new HashSet<>();//存放所有可能的结果，不用担心重复问题
//            set.add(0);//初始化为0
//            int n = in.nextInt();//个数
//            int[] w = new int[n];
//            int[] nums = new int[n];
//            for(int i=0;i<n;i++){
//                w[i] = in.nextInt();//砝码的重量
//            }
//            for(int i=0;i<n;i++){
//                nums[i] = in.nextInt();//砝码个数
//            }
//            for(int i=0;i<n;i++){//遍历砝码
//                ArrayList<Integer> list = new ArrayList<>(set);//取当前所有的结果
//                for(int j=1;j<=nums[i];j++){//遍历个数
//                    for(int k=0;k<list.size();k++){
//                        set.add(list.get(k) + w[i] * j);
//                    }
//                }
//            }
//            System.out.println(set.size());
//        }
//    }
//
//
//    //迷宫问题#
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            int n = in.nextInt();
//            int m = in.nextInt();
//            // 构造迷宫
//            int[][] map = new int[n][m];
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < m; j++) {
//                    map[i][j] = in.nextInt();
//                }
//            }
//
//            // 路径存储的数组
//            List<Pos> path = new ArrayList<>();
//            // DFS 搜索路径
//            dfs(map, 0, 0, path);
//            // 输出
//            for (Pos p : path) {
//                System.out.println("(" + p.x + "," + p.y + ")");
//            }
//        }
//    }
//
//    // 返回值 标记是否找到可通行的路劲
//    public static boolean dfs(int[][] map, int x, int y, List<Pos> path) {
//        // 添加路径并标记已走
//        path.add(new Pos(x, y));
//        map[x][y] = 1;
//        // 结束标志
//        if (x == map.length - 1 && y == map[0].length - 1) {
//            return true;
//        }
//        // 向下能走时
//        if (x + 1 < map.length && map[x + 1][y] == 0) {
//            if (dfs(map, x + 1, y, path)) {
//                return true;
//            }
//        }
//        // 向右能走时
//        if (y + 1 < map[0].length && map[x][y + 1] == 0) {
//            if (dfs(map, x, y + 1, path)) {
//                return true;
//            }
//        }
//        // 向上能走时
//        if (x - 1 > -1 && map[x - 1][y] == 0) {
//            if (dfs(map, x - 1, y, path)) {
//                return true;
//            }
//        }
//        // 向左能走时
//        if (y - 1 > -1 && map[x][y - 1] == 0) {
//            if (dfs(map, x, y - 1, path)) {
//                return true;
//            }
//        }
//        // 回溯
//        path.remove(path.size() - 1);
//        map[x][y] = 0;
//        return false;
//    }
//
//    // 简单的位置类
//    public static class Pos {
//        int x;
//        int y;
//
//        public Pos(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//
//    //名字的漂亮度
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        Scanner in=new Scanner(System.in);
//        while(in.hasNext()){
//            int n=in.nextInt();
//            for(int i=0;i<n;i++){
//                String str=in.next();
//                int s[]=new int[128];
//                for(int j=0;j<str.length();j++){
//                    s[str.charAt(j)]++;
//                }
//                Arrays.sort(s);
//                int mul=26,sum=0;
//                for(int j=s.length-1;j>=0&&s[j]>0;j--){
//                    sum+=s[j]*mul;
//                    mul--;
//                }
//                System.out.println(sum);
//            }
//        }
//
//
////        从单向链表中删除指定值的节点
//        public static void main(String[] args) {
//            Scanner sc = new Scanner(System.in);
//            while (sc.hasNext()) {
//                int total = sc.nextInt();
//                int head = sc.nextInt();
//
//                List<Integer> linkedlist = new ArrayList<>();
//
//                linkedlist.add(head);
//                for (int i = 0; i < total - 1; i ++) {
//                    int value = sc.nextInt();
//                    int target = sc.nextInt();
//                    linkedlist.add(linkedlist.indexOf(target) + 1, value);
//                }
//
//                int remove = sc.nextInt();
//                linkedlist.remove(linkedlist.indexOf(remove));
//                for (int i : linkedlist) {
//                    System.out.print(i + " ");
//                }
//                System.out.println();
//            }
//
//
//            //四则运算
//            Scanner sc = new Scanner(System.in);
//            String s = sc.nextLine();
//            Stack<Integer> numbers = new Stack<>();
//            Stack<Character> operators = new Stack<>();
//            s = s.replace("[", "(");
//            s = s.replace("]", ")");
//            s = s.replace("{", "(");
//            s = s.replace("}", ")");
//            boolean flag = false;
//            for (int i = 0; i < s.length(); i++) {
//                char c = s.charAt(i);
//                if (Character.isDigit(c)) {
//                    StringBuilder sb = new StringBuilder();
//                    if (flag) {
//                        sb.append("-");
//                        flag = false;
//                    }
//                    while (i < s.length() && Character.isDigit(s.charAt(i))) {
//                        sb.append(s.charAt(i++));
//                    }
//                    i--;
//                    numbers.push(Integer.valueOf(sb.toString()));
//                } else if (isOperator(c)) {
//                    // 处理负数情况
//                    if (c == '-' && (i == 0 || s.charAt(i-1) == '(')) {
//                        flag = true;
//                        continue;
//                    }
//                    while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
//                        numbers.push(doOperate(operators.pop(), numbers.pop(), numbers.pop()));
//                    }
//                    operators.push(c);
//                } else if (c == '(') {
//                    operators.push(c);
//                } else if (c == ')') {
//                    while (operators.peek() != '(') {
//                        numbers.push(doOperate(operators.pop(), numbers.pop(), numbers.pop()));
//                    }
//                    operators.pop();
//                }
//
//            }
//            while (!operators.isEmpty()) {
//                numbers.push(doOperate(operators.pop(), numbers.pop(), numbers.pop()));
//            }
//            System.out.println(numbers.pop());
//        }
//
//        public static int doOperate(char c, int b, int a) {
//            if (c == '+') {
//                return a + b;
//            } else if (c == '-') {
//                return a - b;
//            } else if (c == '*') {
//                return a * b;
//            } else {
//                return a / b;
//            }
//            // return 0;
//        }
//
//        public static boolean hasPrecedence(char op2, char op1) {
//            if (isOperator(op1) && (op2 == '+' || op2 == '-')) {
//                return true;
//            }
//            return false;
//        }
//
//        public static boolean isOperator(char c) {
//            if (c == '+' || c == '-' || c == '*' || c == '/') {
//                return true;
//            }
//            return false;
//        }
//
//        //计算字符串的编辑距离
//        public static void main(String[] args){
//            Scanner sc = new Scanner(System.in);
//            while(sc.hasNext()){
//                String a = sc.nextLine();
//                String b = sc.nextLine();
//                int[][] dp = new int[a.length()+1][b.length()+1];  //定义动规数组
//
//                for(int i=1; i<=a.length(); i++){  // 初始化
//                    dp[i][0] = i;
//                }
//                for(int i=1; i<=b.length(); i++){  // 初始化
//                    dp[0][i] = i;
//                }
//                for(int i=1; i<=a.length(); i++){
//                    for(int j=1; j<=b.length(); j++){
//                        if(a.charAt(i-1)==b.charAt(j-1)){  //第一种情况
//                            dp[i][j] = dp[i-1][j-1];
//                        }else{  //第二种情况
//                            dp[i][j] = Math.min(dp[i-1][j]+1, Math.min(dp[i-1][j-1]+1, dp[i][j-1]+1));  //状态转移方程
//                        }
//                    }
//                }
//                System.out.println(dp[a.length()][b.length()]);
//            }
//
//            //挑7
//            public static void main(String[] args){
//                Scanner sc = new Scanner(System.in);
//                while(sc.hasNext()){
//                    int n = sc.nextInt();
//                    int sum=0;
//                    for(int i = 1;i<=n;i++){
//                        if(i%7==0){
//                            sum++;
//                        }else{
//                            String s = String.valueOf(i);
//                            if(s.contains("7")){
//                                sum++;
//                            }
//                        }
//                    }
//                    System.out.println(sum);
//                }
//            }
//            // 高精度整数加法
//            public static void main(String[] args) {
//                Scanner scan = new Scanner(System.in);
//                while (scan.hasNext()) {
//                    String s1 = scan.next();
//                    String s2 = scan.next(); //输入两个数
//                    String res = add(s1, s2); //输出
//                    System.out.println(res);
//                }
//            }
//
//            private static String add(String s1, String s2) { //两个字符串整数相加
//                StringBuilder res = new StringBuilder();
//                int n = s1.length() - 1;
//                int m = s2.length() - 1;
//                int carry = 0; //进位
//                while (n >= 0 || m >= 0) { //从两个人字符串最后一位开始相加
//                    char c1 = n >= 0 ? s1.charAt(n--) : '0'; //没有了就用0代替
//                    char c2 = m >= 0 ? s2.charAt(m--) : '0';
//                    int sum = (c1 - '0') + (c2 - '0') + carry; //两个数子与进位相加
//                    res.append(sum % 10); //余数添加进结果
//                    carry = sum / 10;  //进位
//                }
//
//                if (carry == 1) { //最后的进位
//                    res.append(carry);
//                }
//                return res.reverse().toString(); //反转后转成字符串
//
//
//            //找出字符串中第一个只出现一次的字符
//                public static void main(String[] args){
//                    Scanner sc = new Scanner(System.in);
//                    while(sc.hasNextLine()){
//                        //设置信号量
//                        int signal = 0;
//                        //读取输入内容
//                        String str = sc.nextLine();
//                        //遍历输入内容
//                        for(int i = 0; i < str.length(); i++){
//                            //判断每个字符是否出现第二次，如果存在，设置信号量signal为1；
//                            if(str.indexOf(str.charAt(i)) == str.lastIndexOf(str.charAt(i))){
//                                System.out.print(str.charAt(i));
//                                signal = 1;
//                                break;
//                            }
//                        }
//                        //如果信号量为零，证明不存在重复字符
//                        if(signal == 0){
//                            System.out.println(-1);
//                        }
//                        //每读取一行输出一个回车
//                        System.out.println();
//                    }
//
//                    //DNA序列
//                    public static void main(String[] args) {
//                        Scanner scanner = new Scanner(System.in);
//                        while (scanner.hasNext()) {
//                            String str = scanner.next();
//                            int n = scanner.nextInt();
//                            System.out.println(Solution(str, n));
//                        }
//                    }
//
//                    public static String Solution(String str, int n) {
//                        // GC字母个数
//                        int maxSum = 0;
//                        // 结果子串的起始索引
//                        int index = 0;
//                        // 起始索引
//                        for(int i = 0; i <= str.length() - n; i++) {
//                            int gcSum = 0;
//                            // 从起点索引开始向后遍历n个字符
//                            for(int j = i; j < i + n; j++) {
//                                if(str.charAt(j) == 'C' || str.charAt(j) == 'G') {
//                                    gcSum++;
//                                }
//                            }
//                            if(gcSum > maxSum) {
//                                index = i;
//                                maxSum = gcSum;
//                                // 剪枝
//                                if(gcSum == n) {
//                                    return str.substring(index, index + n);
//                                }
//                            }
//                        }
//                        return str.substring(index, index + n);
//
//
//                        //MP3光标位置
//                        public static void main(String[] args) {
//                            Scanner in = new Scanner(System.in);
//                            // 注意 hasNext 和 hasNextLine 的区别
//                            //先解决输出选中歌曲
//                            int n = in.nextInt();
//                            in.nextLine();
//                            String a = in.nextLine();
//                            int start = 1;
//                            int pos = 1;
//                            //经过分析 与这也第一个 start有关 start与光标选的pos有关
//                            if (n <= 4) {
//                                for (char p : a.toCharArray()) {
//                                    switch (p) {
//                                        case 'U':
//                                            if (pos == 1) {
//                                                pos = n;
//                                            } else {
//                                                pos = (pos - 1 + n) % n;
//                                            }
//                                            break;
//                                        case 'D':
//                                            pos = (pos + 1 + n) % n;
//                                            break;
//                                    }
//                                }
//                                for (int i = 1; i <= n; i++) {
//                                    System.out.print(i + " ");
//                                }
//                                System.out.println();
//                                System.out.print(pos);
//                            } else { //n>4
//                                int count = 0;
//                                for (char p : a.toCharArray()) {
//                                    switch (p) {
//                                        case 'U':
//                                            count = 1;
//                                            if (pos == 1) {
//                                                pos = n;
//                                                start = n - 3;
//                                            } else if (start == pos) {
//                                                pos = pos - 1;
//                                                start = start - 1;
//                                            } else {
//                                                pos = pos - 1;
//                                            }
//                                            break;
//                                        case 'D':
//                                            count = -1;
//                                            if (pos == n) {
//                                                pos = 1;
//                                                start = 1;
//                                            } else if (start + 3 == pos) {
//                                                pos = pos + 1;
//                                                start = start + 1;
//                                            } else {
//                                                pos = pos + 1;
//                                            }
//                                            break;
//                        /*
//                        分析 当歌曲数量大于4时 特殊页 1-4 及后面四个 比如1 2 3 4 5 6 7  1-4 3-7特殊
//                        中间页面 start = pos+ -1  pos = pos+ -1
//                         */
//                                    }
//                                }
//                                System.out.println(start + " " + (start + 1) + " " + (start + 2) + " " + (start + 3));
//                                System.out.print(pos);
//
//
//                                //查找两个字符串a,b中的最长公共子串
//                                public static void main(String[] args) {
//                                    Scanner in = new Scanner(System.in);
//                                    String s1 = in.next(); // 长
//                                    String s2 = in.next(); // 短
//                                    if (s1.length() < s2.length()) { // 保证s2短
//                                        String t = s1;
//                                        s1 = s2;
//                                        s2 = t;
//                                    }
//
//                                    for (int d = s2.length(); d > 0; d--) {  // d 即子串长度，从大到小，找到匹配的即所求
//                                        for (int i = 0; i + d <= s2.length(); i++) { // 从左到右 扫描s2子串
//                                            String sb = s2.substring(i, i + d);
//                                            if (s1.length() - s1.replace(sb, "").length() > 0) { // s1含子串sb？
//                                                System.out.print(sb);
//                                                return; // 直接返回
//                                            }
//                                        }
//                                    }
//                                }
//
//                                //配置文件恢复
//
//                                public static void main(String[] args)
//                                {
//                                    Scanner sc=new Scanner(System.in);
//                                    Map<String,String> command=new HashMap<String,String>();//建立命令哈希表
//                                    //向哈希表里添加命令键值对
//                                    command.put("reset","reset what");
//                                    command.put("reset board","board fault");
//                                    command.put("board add","where to add");
//                                    command.put("reboot backplane","impossible");
//                                    command.put("backplane abort","install first");
//                                    command.put("board delet","no board at all");
//                                    Set<String[]> order=new HashSet<String[]>();//建立哈希命令视图
//                                    //遍历哈希表的set视图,向哈希命令表里添加命令
//                                    for(String s:command.keySet())
//                                    {
//                                        order.add(s.split(" "));
//                                    }
//                                    while(sc.hasNextLine())
//                                    {
//                                        String input=sc.nextLine();
//                                        String[] inputChange=input.split(" ");//将输入字符串用空格分隔，以便比较
//                                        String[] compitable=null;//匹配的命令字符串
//                                        //开始遍历命令视图
//                                        for(String[] cmpOrder:order)
//                                        {
//                                            //输入字符串数组长度为一
//                                            if(inputChange.length==1)
//                                            {
//                                                //命令字符串数组长度为二，不匹配
//                                                if(cmpOrder.length==2)
//                                                    continue;
//                                                else
//                                                {
//                                                    //匹配成功
//                                                    if(cmpOrder[0].startsWith(inputChange[0]))
//                                                    {
//                                                        compitable=cmpOrder;
//                                                        break;
//                                                    }
//                                                }
//                                            }
//                                            //输入字符串数组长度为二的情况
//                                            if(inputChange.length==2)
//                                            {
//                                                //如果待比较命令字符串长度为1，不匹配
//                                                if(cmpOrder.length==1)
//                                                    continue;
//                                                else
//                                                    //如果输入命令字符串与待比较命令字符串一一匹配，匹配成功
//                                                    if(cmpOrder[0].startsWith(inputChange[0]))
//                                                        if(cmpOrder[1].startsWith(inputChange[1]))
//                                                        {
//                                                            compitable=cmpOrder;
//                                                            break;
//                                                        }
//                                            }
//                                        }
//                                        //从哈希表中找出命令的执行结果并输出
//                                        if(compitable==null)
//                                            System.out.println("unkown command");
//                                        else if(compitable.length==1)
//                                            System.out.println(command.get(compitable[0]));
//                                        else
//                                            System.out.println(command.get(compitable[0]+" "+compitable[1]));
//                                    }
//                                    sc.close();
//                                }
//
//                                // 24点游戏算法
//                                public static void main(String[] args) throws IOException{
//                                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//                                    String str;
//                                    while ((str = br.readLine()) != null) {
//                                        String[] numstr = str.split(" ");
//                                        int[] nums = new int[4]; // 存放数字
//                                        int[] visit = new int[4]; // 存放对应位置数字的使用状态（1代表已使用）
//                                        boolean flag = false;
//                                        for (int i = 0; i < 4; i++) {
//                                            nums[i] = Integer.parseInt(numstr[i]); // 读取数字
//                                        }
//                                        for (int i = 0; i < 4; i++) {
//                                            visit[i] = 1; // 把当前数字标记为已使用
//                                            if (dfs(nums, visit, nums[i])) { // 进入递归
//                                                flag = true;
//                                                break;
//                                            }
//                                        }
//                                        System.out.println(flag);
//                                    }
//
//
//
//                                }
//
//                                public static boolean dfs(int[] nums, int[] visit, int temp) {
//                                    for (int i = 0; i < nums.length; i++) {
//                                        if (visit[i] == 0) { // 如果是未使用的数字
//                                            visit[i] = 1; // 标记为已使用
//                                            if (dfs(nums, visit, temp+nums[i]) // 递归判断
//                                                    || dfs(nums, visit, temp-nums[i])
//                                                    || dfs(nums, visit, temp*nums[i])
//                                                    || (temp%nums[i] == 0 && dfs(nums, visit, temp/nums[i]))) {
//                                                // 如果存在满足条件的，终止循环
//                                                return true;
//                                            }
//                                            // 不存在满足条件的，说明当前的数字顺序不符要求，进行回溯，把标记重置为0
//                                            visit[i] = 0;
//                                        }
//                                    }
//                                    // 数字都已使用且结果为24，返回真
//                                    if (temp == 24) {
//                                        return true;
//                                    }
//                                    // 不存在24，返回假
//                                    return false;
//                                }
//
////                                矩阵乘法
//                            import java.util.*;
//
//                                public class Main {
//                                    public static void main(String[] args) {
//                                        Scanner sc = new Scanner(System.in);
//                                        if (sc.hasNext()) {
//                                            int x = sc.nextInt();
//                                            int y = sc.nextInt();
//                                            int z = sc.nextInt();
//                                            int[][] aArr = new int[x][y];
//                                            int[][] bArr = new int[y][z];
//                                            for (int i = 0; i < x; i++) {
//                                                for (int j = 0; j < y; j++) {
//                                                    aArr[i][j] = sc.nextInt();
//                                                }
//                                            }
//                                            for (int i = 0; i < y; i++) {
//                                                for (int j = 0; j < z; j++) {
//                                                    bArr[i][j] = sc.nextInt();
//                                                }
//                                            }
//                                            for (int i = 0; i < x; i++) {
//                                                for (int j = 0; j < z; j++) {
//                                                    int result = 0;
//                                                    for (int k = 0; k < y; k++) {
//                                                        result += aArr[i][k] * bArr[k][j];
//                                                    }
//                                                    System.out.print(result + " ");
//                                                }
//                                                System.out.println();
//                                            }
//                                        }
//                                    }
//                                }
//
//
//
////                                矩阵乘法计算量估算
//import java.util.*;
//                                public class Main {
//                                    public static void main(String[] args) {
//                                        Scanner in = new Scanner(System.in);
//                                        while(in.hasNextInt()){
//                                            int n = in.nextInt();
//                                            int a[][] = new int[n][2];
//                                            for(int i=0;i<n;i++){
//                                                a[i][0] = in.nextInt();
//                                                a[i][1] = in.nextInt();
//                                            }
//                                            String s = in.next();
//                                            Stack<Integer> stack = new Stack();                 // 存放矩阵行数和列数
//                                            int sum = 0;
//                                            for(int i=s.length()-1,j=n-1;i>=0;i--){
//                                                if(s.charAt(i)>='A' && s.charAt(i)<='Z'){       // 属于字母则把相应的矩阵列数和行数入栈
//                                                    stack.push(a[j][1]);
//                                                    stack.push(a[j][0]);
//                                                    j--;
//                                                }else if(s.charAt(i) == '('){                   // 括号：推出计算
//                                                    int x0 = stack.pop(), y0 = stack.pop();     // 矩阵尺寸x0*y0
//                                                    int x1 = stack.pop(), y1 = stack.pop();     // 矩阵尺寸x1*y1
//                                                    sum += x0*y0*y1;      // 两个矩阵的乘法次数为x0*y0*y1或x0*x1*y1（其中y0==x1）
//                                                    stack.push(y1);       // 把相乘后得到的矩阵列数入栈
//                                                    stack.push(x0);       // 把相乘后得到的矩阵行数入栈
//                                                }
//                                            }
//                                            System.out.println(sum);
//                                        }
//                                    }
//                                }
//
//                                //字符串通配符
//                                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//                                String s=null;
//                                while((s=br.readLine())!=null){
//                                    String ss=br.readLine();
//
//                                    s=s.toLowerCase();
//                                    ss=ss.toLowerCase();
//                                    char sss[]=ss.toCharArray();
//                                    char ppp[]=s.toCharArray();
//
//                                    boolean dp[][]=new boolean[ppp.length+1][sss.length+1];
//                                    dp[0][0]=true;
//                                    for(int i=1;i<=ppp.length;i++){
//                                        if(ppp[i-1]=='*'){
//                                            dp[i][0]=true;
//                                        }else break;
//
//                                    }
//                                    for(int i=1;i<=ppp.length;i++){
//                                        for(int j=1;j<=sss.length;j++){
//                                            if(ppp[i-1]==sss[j-1]){
//                                                dp[i][j]=dp[i-1][j-1];
//                                            }
//                                            if(ppp[i-1]=='?'&&((sss[j-1]>='a'&&sss[j-1]<='z')||(sss[j-1]>='0'&&sss[j-1]<='9'))){
//                                                dp[i][j]=dp[i-1][j-1];
//                                            }else if(ppp[i-1]=='*'&&((sss[j-1]>='a'&&sss[j-1]<='z')||(sss[j-1]>='0'&&sss[j-1]<='9'))){
//                                                dp[i][j]=dp[i-1][j]|dp[i][j-1]|dp[i-1][j-1];
//                                            }
//                                            //System.out.print(dp[i][j]+"  ");
//                                        }
//                                        //System.out.print("\n");
//                                    }
//                                    System.out.println(dp[ppp.length][sss.length]);
//
////                                    参数解析
//                                    public static void main(String[] args) {
//                                        Scanner scanner = new Scanner(System.in);
//
//                                        String nextLine = scanner.nextLine();
//
//                                        StringBuilder stringBuilder = new StringBuilder();
//                                        ArrayList<String> arrayList = new ArrayList();
//                                        boolean flag = false;
//                                        for (int i = 0; i < nextLine.length(); i++) {
//                                            char c = nextLine.charAt(i);
//
//                                            if (String.valueOf(c).equals("\"")) {
//                                                flag = flag ? false : true;
//                                                continue;
//                                            }
//
//                                            if (String.valueOf(c).equals(" ") && !flag) {
//
//                                                arrayList.add(stringBuilder.toString());
//                                                stringBuilder = new StringBuilder();
//                                            } else {
//                                                stringBuilder.append(c);
//                                            }
//
//                                        }
//                                        arrayList.add(stringBuilder.toString());
//                                        System.out.println(arrayList.size());
//                                        for (String s : arrayList) {
//                                            System.out.println(s);
//                                        }
//
//                                        //公共子串计算
//                                        Scanner in = new Scanner(System.in);
//                                        String ss1 = in.nextLine();
//                                        String ss2 = in.nextLine();
//                                        String s1 = ss1.length()<ss2.length() ? ss1:ss2;  // 短的字符串
//                                        String s2 = ss1.length()<ss2.length() ? ss2:ss1;  // 长的字符串
//                                        int n = 0;
//                                        for(int i=0;i<s1.length();i++){              // 头指针从第一位开始递增
//                                            for(int j=s1.length();j>i;j--){          // 尾指针从最后一位开始缩减
//                                                if(s2.contains(s1.substring(i,j))){  // 第一次发现合集的长度一定是最大的
//                                                    n = j-i>n ? j-i:n;               // 取每一次比较的最大值
//                                                    break;                           // 已经是最大的，无需再进行后续的操作
//                                                }
//                                            }
//                                        }
//                                        System.out.println(n);
//                                    }
//
//                                    //火车进站
//                                    Scanner in = new Scanner(System.in);
//
//                                    while (in.hasNext()) {
//                                        l.clear(); //静态变量，每次先清空
//                                        int nums = in.nextInt();
//                                        int[] id = new int[nums];
//                                        Stack<Integer> stack = new Stack<>();
//                                        for (int i = 0; i < nums; i++) {
//                                            id[i] = in.nextInt();
//                                        }
//                                        trainOut(id, 0, stack, "", 0);
//                                        //对结果集排序
//                                        Collections.sort(l);
//                                        for (String str : l) {
//                                            System.out.println(str);
//                                        }
//                                    }
//                                    in.close();
//
//                                    // 将真分数分解为埃及分数
//                                    public static void main(String[] args) {
//                                        Scanner sc = new Scanner(System.in);
//
//                                        while (sc.hasNext()) {
//                                            String[] str = sc.next().split("/");
//                                            // 分子
//                                            int a = Integer.parseInt(str[0]);
//                                            // 分母
//                                            int b = Integer.parseInt(str[1]);
//
//                                            result = new StringBuffer();
//                                            process(a, b, result);
//                                            System.out.println(result);
//                                        }
//                                    }
//
//                                    private static void process(int a, int b, StringBuffer result) {
//                                        if (result.length() != 0) {
//                                            result.append("+");
//                                        }
//
//                                        int x = b / a;
//
//                                        if (a == 1 || b % a == 0) {
//                                            result.append("1/").append(x);
//                                        } else {
//                                            int y = b % a;
//                                            result.append("1/").append(x + 1);
//                                            process(a - y, b * (x + 1), result);
//                                        }
//                                    }
//
//                                    static StringBuffer result;
//
////                                    仰望水面的歪
//                                    public static void main(String[] args) {
//                                        Scanner in = new Scanner(System.in);
//                                        long n = in.nextInt();
//                                        long h = in.nextInt();
//                                        in.nextLine();
//                                        while (n-- > 0) {
//
//                                            long a = in.nextInt();
//                                            long b = in.nextInt();
//                                            long z = in.nextInt();
//                                            long c = 2 * h - z;
//                                            //System.out.println(a + " " + b + " " + c);
//                                            if ((a == 0 && b == 0) || (a == 0 && c == 0) || (b == 0 && b == 0)) {
//                                                System.out.println(a + " " + b + " " + c);
//                                            } else if (a == 0 || b == 0 || c == 0) {
//                                                if (a == 0) {
//                                                    if (Math.max(b, c) % Math.min(b, c) == 0) {
//                                                        b = b / Math.min(b, c);
//                                                        c = c / Math.min(b, c);
//                                                    }
//                                                } else if (b == 0) {
//                                                    if (Math.max(a, c) % Math.min(a, c) == 0) {
//                                                        a = a / Math.min(a, c);
//                                                        c = c / Math.min(a, c);
//                                                    }
//
//                                                } else { //c==0
//                                                    if (Math.max(b, c) % Math.min(b, c) == 0) {
//                                                        b = a / Math.min(b, c);
//                                                        c = c / Math.min(b, c);
//                                                    }
//                                                }
//                                            } else if (a == b & b == c) {
//                                                System.out.println("1 1 1");
//                                            } else {
//                                                //还有一种情况 3个都不相等也没有0 但是互相是倍数关系 a=10 b=30 c=55这样 2 6 11
//                                                long[] x = new long[3];
//                                                x[0] = a;
//                                                x[1] = b;
//                                                x[2] = c;
//                                                if (a % x[0] == 0 && b % x[0] == 0 && c % x[0] == 0) {
//                                                    a = a / x[0];
//                                                    b = b / x[0];
//                                                    c = c / x[0];
//                                                    System.out.println(a + " " + b + " " + c);
//                                                } else {
//                                                    while ((a % 2 == 0 && b % 2 == 0 && c % 2 == 0)) {
//                                                        a = a / 2;
//                                                        b = b / 2;
//                                                        c = c / 2;
//                                                    }
//
//                                                    for (int i = 3; i < Math.sqrt(Math.min(Math.min(a, b), c)); i++) {
//                                                        while ((a % i == 0 && b % i == 0 && c % i == 0)) {
//                                                            a = a / i;
//                                                            b = b / i;
//                                                            c = c / i;
//                                                        }
//                                                        i++;
//                                                    }
//                                                    for (int i = 3; i <49;
//                                                         i++) {//这里一直增大i判断条件值 最后通过了  此处是处理  a=10 b=20 c=55的这种情况的  也是这种情况还没有想到处理方法
//                                                        while ((a % i == 0 && b % i == 0 && c % i == 0)) {
//                                                            a = a / i;
//                                                            b = b / i;
//                                                            c = c / i;
//                                                        }
//                                                        i++;
//                                                    }
//                                                    System.out.println(a + " " + b + " " + c);
//                                                }
//                                            }
//
//                                        }
//                                    }
//
////                                    合法IP
//                                    public static void main(String[] args) {
//                                        Scanner in = new Scanner(System.in);
//                                        // 注意 hasNext 和 hasNextLine 的区别
//                                        while (in.hasNext()) { // 注意 while 处理多个 case
//                                            String[] a = in.nextLine().split("\\.");
//                                            System.out.println(check(a));
//                                        }
//                                    }
//
//                                    public static String check(String[] a){
//                                        if(a.length!=4){
//                                            return "NO";
//                                        }
//                                        boolean flag=true;
//                                        for(int i=0;i<a.length;i++){
//                                            // .1.3.8
//                                            if(a[i]==null||a[i].equals("")){
//                                                flag = false;
//                                                break;
//                                            }
//                                            int k=Integer.parseInt(a[i]);
//                                            // 01 ->1
//                                            if((""+k).length()!=(a[i].length())){
//                                                flag = false;
//                                                break;
//                                            }
//                                            //>255&nbs***bsp;<0
//                                            if(k<0||k>255){
//                                                flag = false;
//                                                break;
//                                            }
//                                        }
//                                        return flag?"YES":"NO";
//
//                                        //在字符串中找出连续最长的数字串
//
//                                        6
//                                        7
//                                        8
//                                        9
//                                        10
//                                        11
//                                        12
//                                        13
//                                        14
//                                        15
//                                        16
//                                        17
//                                        18
//                                        19
//                                        20
//                                        21
//                                        22
//                                        23
//                                        24
//                                        25
//                                        26
//                                        27
//                                        28
//                                        29
//                                        30
//                                        31
//import java.io.*;
//import java.util.*;
//
//                                        public class Main{
//                                            public static void main(String[] args) throws Exception{
//                                                Scanner sc = new Scanner(System.in);
//
//                                                while(sc.hasNextLine()){
//                                                    String line = sc.nextLine();
//                                                    String[] ss = line.split("[^0-9]+");
//                                                    int max  = 0;
//                                                    ArrayList<String> list = new ArrayList<>();
//                                                    for(String s : ss){
//                                                        if(s.length() > max){
//                                                            max = s.length();
//                                                            list.clear();
//                                                            list.add(s);
//                                                        }else if(s.length() == max){
//                                                            max = s.length();
//                                                            list.add(s);
//                                                        }
//                                                    }
//                                                    StringBuilder sb = new StringBuilder();
//                                                    for(String item : list){
//                                                        sb.append(item);
//                                                    }
//                                                    sb.append(",").append(max);
//                                                    System.out.println(sb.toString());
//                                                }
//                                            }
//
////                                            Redraiment的走法
//
//                                            public static void main(String[] arg) {
//                                                Scanner scan = new Scanner(System.in);
//                                                while (scan.hasNext()) {
//                                                    scan.nextLine();
//                                                    String[] input1 = scan.nextLine().split(" ");
//                                                    int[] intArr = Arrays.stream(input1).mapToInt(Integer::parseInt).toArray();
//                                                    int[] k=new int[intArr.length];
//                                                    for(int j=1;j<intArr.length;j++){
//                                                        for(int i=0;i<j;i++){
//                                                            if(intArr[i]<intArr[j]){
//                                                                k[j]=Math.max(k[j],k[i]+1);
//                                                            }
//                                                        }
//                                                    }
//                                                    Arrays.sort(k);
//                                                    System.out.println(k[k.length-1]+1);
//                                                }
//                                            }
//
//                                            //小红走网格
//                                            public static void main(String[] args) {
//                                                Scanner in = new Scanner(System.in);
//                                                int T = in.nextInt();
//                                                in.nextLine();
//                                                while (T-- > 0) {
//                                                    int x = in.nextInt();
//                                                    int y = in.nextInt();
//                                                    int a = in.nextInt();
//                                                    int b = in.nextInt();
//                                                    int c = in.nextInt();
//                                                    int d = in.nextInt();
//                                                    if (x % gcd(c, d) == 0 && y % gcd(a, b) == 0) {
//                                                        System.out.println("YES");
//                                                    } else {
//                                                        System.out.println("NO");
//                                                    }
//                                                }
//                                            }
//                                            private  static int gcd(int a, int b) {
//                                                return b == 0 ? a : gcd(b, a % b);
//                                            }
//
//                                            //小红的二叉树
//
//                                            public static void main(String[] args) throws IOException {
//                                                // ((2^n - 1) * 3 -2 ) mod 100000007
//                                                int base = 1000000007;
//                                                long baseRemainder = (1 << 30) % base;
//                                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//                                                int deep = Integer.parseInt(bufferedReader.readLine()) - 1;
//                                                int len = deep / 30;
//                                                int less = deep % 30;
//                                                long remainder = 1;
//                                                for (int i = 0; i < len; i++) {
//                                                    remainder = (remainder * baseRemainder) % base;
//                                                }
//                                                remainder = remainder * ((1L << less) % base);
//                                                remainder = (remainder - 1) * 3 % base;
//                                                System.out.println(remainder > 0 ? remainder - 2 : remainder);
//                                            }
//                                        }
//
