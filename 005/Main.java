package com.hoboss;

import java.util.*;

class Node {
    public int user_id; // 结点的名称，这里使用用户id
    public HashSet<Integer> friends; // 使用哈希映射存放相连的朋友结点。
    public int degree; // 用于存放和给定的用户结点，是几度好友

    public Node(int d) {
        user_id = d;
        friends = new HashSet<>();
        degree = 0;
    }

    // 广度优先查找 user_id 在 user_nodes关系图中的所有好友和n度好友。
    public static HashSet<Integer> breadth_first_search(Node[] user_nodes, int user_id) {
        HashSet<Integer> friends = new HashSet<>();
        if (user_id > user_nodes.length) {
            return friends;
        }

        Queue<Integer> queue = new LinkedList<>();  // 用FIFO来实现广度优先。
        queue.offer(user_id);

        while (!queue.isEmpty()) {
            int cur_id = queue.poll();

            if (user_nodes[cur_id].friends == null) {
                continue;
            }

            for (Integer id : user_nodes[cur_id].friends) {
                if (id == user_id || friends.contains(id)) {
                    continue;
                }
                friends.add(id);
                queue.offer(id);
                user_nodes[id].degree = user_id == cur_id ? 1 : user_nodes[cur_id].degree + 1;
            }
        }

        return friends;
    }
}

public class Main {

    public static void main(String[] args) {
        // generate some fake users;
        int user_num = 100; // 用户数量
        int relation_num = 600; // 好友关系数量，也就是边的数量。
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

        //
        HashSet<Integer> friends = Node.breadth_first_search(user_nodes, 1);
        for (Integer i : friends) {
            System.out.println(i + " " + user_nodes[i].degree);
        }
    }

}
