package com.hoboss;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<List<Integer>> getCombinations(List<Integer> m, int n) {
        List<List<Integer>> combinations = new ArrayList<>();
        if (1 == n) {
            for (Integer temp : m) {
                combinations.add(new ArrayList<Integer>() {{
                    add(temp);
                }});
            }
        } else if (n > m.size()) {
            // wrong
        } else if (n == m.size()) {
            combinations.add(m);
        } else if (n < m.size()) {
            for (int i = 0; i < m.size(); ++i) {
                List<List<Integer>> temp = getCombinations(m.subList(i + 1, m.size()), n - 1);
                for (List<Integer> l : temp) {
                    List<Integer> comb = new ArrayList<Integer>();
                    comb.add(m.get(i));
                    comb.addAll(l);
                    combinations.add(comb);
                }
            }
        }

        return combinations;
    }

    public static void main(String[] args) {
        // 假设现在需要设计一个抽奖系统。需要依次从 10 个人中，抽取三等奖 3 名，二等奖 2 名和一等奖 1 名。
        // 请列出所有可能的组合，需要注意的每人最多只能被抽中 1 次。
        int total = 10;         // The number of people who takes part in the lucky draw
        int firstPrize = 1;     // The number of 1st prize.
        int secondPrize = 2;    // The number of 2nd prize.
        int thirdPrize = 3;     // The number of 3rd prize.

        List<Integer> all = new ArrayList<Integer>();
        for (int i = 0; i < total; ++i) {
            all.add(i);
        }

        List<List<List<Integer>>> results = new ArrayList<>();

        List<List<Integer>> res1st = getCombinations(all, firstPrize);
        for (List<Integer> first : res1st) {
            List<Integer> left1st = new ArrayList<>(all);
            left1st.removeAll(first);
            List<List<Integer>> res2nd = getCombinations(left1st, secondPrize);
            for (List<Integer> second : res2nd) {
                List<Integer> left2nd = new ArrayList<>(left1st);
                left2nd.removeAll(second);
                List<List<Integer>> res3rd = getCombinations(left2nd, thirdPrize);
                for (List<Integer> third : res3rd) {
                    List<List<Integer>> r= new ArrayList<>();
                    r.add(first);
                    r.add(second);
                    r.add(third);
                    results.add(r);
                }
            }
        }

        System.out.println(results.size());
        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
        }
    }
}
