package com.hoboss;

import java.util.*;

class Node {
    public int user_id; // 结点的名称，这里使用用户id
    public HashSet<Integer> friends; // 使用哈希映射存放相连的朋友结点。

    public Node(int id) {
        user_id = id;
        friends = new HashSet<>();
    }
}

class RelationInfo {
    public int degree;          // 与指定结点是几度好友
    // 与指定结点的前置好友。比如。b是指定结点。的直接好友(1度)，那么pre_friend就是批定结点，
    // 如果 b通过a认识指定结点,则b的 pre_friend是 a。
    public int pre_friend;

    public RelationInfo(int degree, int pre_friend) {
        this.degree = degree;
        this.pre_friend = pre_friend;
    }
}

public class Main {

    // 双向广度优先查找 user_id_a, user_id_b 之前的最短通路。
    static List<Integer> bidirectional_breadth_first_search(Node[] user_nodes, int user_id_a, int user_id_b) {
        List<Integer> result = new LinkedList<>();
        if (user_id_a > user_nodes.length || user_id_b > user_nodes.length || user_id_a == user_id_b) {
            return result;
        }

        HashMap<Integer, RelationInfo> relations_a = new HashMap<>();
        relations_a.put(user_id_a, new RelationInfo(0, -1));

        HashMap<Integer, RelationInfo> relations_b = new HashMap<>();
        relations_b.put(user_id_b, new RelationInfo(0, -1));

        Queue<Integer> queue_a = new LinkedList<>();
        queue_a.add(user_id_a);
        Queue<Integer> queue_b = new LinkedList<>();
        queue_b.add(user_id_b);

        int key = -1;
        while (queue_a.size() > 0 || queue_b.size() > 0) {
            key = is_queue_know_set(queue_a, relations_b);
            if (-1 != key) {
                break;
            }
            key = is_queue_know_set(queue_b, relations_a);
            if (-1 != key) {
                break;
            }

            // 优先扩展个数少，且不为0的那个
            if (queue_b.size() == 0 || (queue_a.size() > 0 && queue_a.size() < queue_b.size())) {
                expand_queue(user_nodes, queue_a, relations_a);
            } else {
                expand_queue(user_nodes, queue_b, relations_b);
            }
        }

        if (-1 != key) {
            int n = key;
            while (n != -1) {
                result.add(n);
                n = relations_a.get(n).pre_friend;
            }
            Collections.reverse(result);
            n = relations_b.get(key).pre_friend;
            while (n != -1) {
                result.add(n);
                n = relations_b.get(n).pre_friend;
            }
        }

        return result;
    }

    static int is_queue_know_set(Queue<Integer> nodes, HashMap<Integer, RelationInfo> relations) {
        for (Integer n : nodes) {
            if (relations.keySet().contains(n)) {
                return n;
            }
        }
        return -1;
    }

    static void expand_queue(Node[] user_nodes, Queue<Integer> nodes, HashMap<Integer, RelationInfo> relations) {
        Queue<Integer> q = new LinkedList<>();
        for (Integer n : nodes) {
            for (Integer i : user_nodes[n].friends) {
                if (relations.keySet().contains(i)) {
                    continue;
                }
                q.add(i);
                int degree = relations.get(n).degree + 1;
                relations.put(i, new RelationInfo(degree, n));
            }
        }
        nodes.clear();
        nodes.addAll(q);
    }


    public static void main(String[] args) {
        // generate some fake users;
        int user_num = 100; // 用户数量
        int relation_num = 300; // 好友关系数量，也就是边的数量。
        Node[] user_nodes = new Node[user_num];
        // 生成所有表示用户的结点。
        for (int i = 0; i < user_num; ++i) {
            user_nodes[i] = new Node(i);
        }
        // 生成所有表示好友关系的边。
        Random rand = new Random();
        for (int i = 0; i < relation_num; ++i) {
            int friend_a_id = rand.nextInt(user_num);
            int friend_b_id = rand.nextInt(user_num);
            while (friend_a_id == friend_b_id) {
                friend_b_id = rand.nextInt(user_num);
            }
            Node friend_a = user_nodes[friend_a_id];
            Node friend_b = user_nodes[friend_b_id];
            friend_a.friends.add(friend_b_id);
            friend_b.friends.add(friend_a_id);
        }

        // 输出关系链
        List<Integer> r = bidirectional_breadth_first_search(user_nodes, 1, 3);
        for (Integer i : r) {
            System.out.println(i);
        }
    }

}
