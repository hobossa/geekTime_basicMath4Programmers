package com.hoboss;

import java.util.*;

class Node {
    public int id; // 结点ID
    public HashMap<Integer, Float> next_node_paths; // 结点到其它结点的距离
    public float min_length; // 到达该结点的最短路径。
    public HashSet<Integer> pre_nodes;   // 路径的前一个结点集合，有可能最短路径有多条。

    public Node(int id) {
        this.id = id;
        next_node_paths = new HashMap<>();
        min_length = Float.MAX_VALUE;
        pre_nodes = new HashSet<>();
    }

    @Override
    public String toString() {
        return "id: " + id + " min length: " + min_length + " pre nodes: " + pre_nodes.toString();
    }

    public static void dijkstra(Node[] nodes, int startId) {
        nodes[startId].min_length = 0f;
        for (int i = 0; i < nodes.length; ++i) {
            if (i != startId) {
                nodes[i].min_length = Float.MAX_VALUE;
            }
        }

        HashSet<Integer> exclusive_ids = new HashSet<>();

        int minId = find_mini_node(nodes, exclusive_ids);
        while (-1 != minId) {
            for (int id : nodes[minId].next_node_paths.keySet()) {
                float temp = nodes[minId].min_length + nodes[minId].next_node_paths.get(id);
                if ( nodes[id].min_length > temp) {
                    // 找到更短路径
                    nodes[id].min_length = temp;
                    nodes[id].pre_nodes.clear();
                    nodes[id].pre_nodes.add(minId);
                } else if (nodes[id].min_length == temp) {
                    // 找到多条同样短路径
                    nodes[id].pre_nodes.add(minId);
                }
            }
            exclusive_ids.add(minId);
            minId = find_mini_node(nodes, exclusive_ids);
        }
    }

    private static int find_mini_node(Node[] nodes, HashSet<Integer> exclusive_ids) {
        int result = -1;
        float min = Float.MAX_VALUE;

        for (Node node : nodes) {
            if (exclusive_ids.contains(node.id)) {
                continue;
            }
            if (min > node.min_length) {
                min = node.min_length;
                result = node.id;
            }
        }
        return result;
    }
}

public class Main {

    public static void main(String[] args) {
        // generate some fake data;
        Node[] nodes = new Node[9];
        for (int i = 0; i < 9; ++i) {
            nodes[i] = new Node(i);
        }
        // path data
        nodes[0].next_node_paths.put(1, 0.5f);
        nodes[0].next_node_paths.put(2, 0.3f);
        nodes[0].next_node_paths.put(3, 0.2f);
        nodes[0].next_node_paths.put(4, 0.4f);

        nodes[1].next_node_paths.put(5, 0.3f);

        nodes[2].next_node_paths.put(1, 0.2f);
        nodes[2].next_node_paths.put(6, 0.1f);

        nodes[3].next_node_paths.put(6, 0.4f);
        nodes[3].next_node_paths.put(8, 0.8f);

        nodes[4].next_node_paths.put(3, 0.1f);
        nodes[4].next_node_paths.put(8, 0.6f);

        nodes[5].next_node_paths.put(7, 0.1f);

        nodes[6].next_node_paths.put(5, 0.1f);
        nodes[6].next_node_paths.put(8, 0.2f);

        nodes[8].next_node_paths.put(7, 0.4f);

        // find the shortest path from node 0 to other nodes (1-8)
        int startId = 0;
        Node.dijkstra(nodes, startId);
        // output
        for ( Node node : nodes) {
            if (node.id == startId) {
                continue;
            }
            System.out.println(node.toString());
        }
    }

}
