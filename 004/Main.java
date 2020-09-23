package com.hoboss;

import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Stack;

class MyDictionary {

    class TreeNode {
        public Character letter;
        public HashMap<Character, TreeNode> children = null;
        public String prefix = null;    // 从根节点到止节点的所有节点组成的前缀。
        public String explanation = null;   // 如果当前节点对应一个单词，那么这里保存该单词的解析。

        public TreeNode(Character ch, String pre) {
            letter = ch;
            prefix = pre;
            children = new HashMap<>();
        }

        @Override
        public String toString() {
            return "letter:" + letter + " prefix:" + prefix + " explanation:" + explanation;
        }
    }

    private TreeNode root = null;

    public MyDictionary() {
        root = new TreeNode(null, null);
    }

    private boolean _AddWord(String word, TreeNode node, String pre, String exp) {
        if (word.length() > 0) {
            char c = word.charAt(0);

            if (!node.children.containsKey(c)) {
                node.children.put(c, new TreeNode(c, pre));
            }

            if (word.length() == 1) {
                if (node.children.get(c).explanation == null) {
                    node.children.get(c).explanation = exp;
                    return true;
                } else {
                    // already exist;
                    return false;
                }
            } else {
                return _AddWord(word.substring(1), node.children.get(c), pre+c, exp);
            }
        } else {
            return false;
        }
    }

    public boolean AddWord(String word, String exp) {
        return _AddWord(word, root, "", exp);
    }

    private String _Search(String word, TreeNode node) {
        if (word.length() <= 0) {
            return null;
        }
        char c = word.charAt(0);
        if (!node.children.containsKey(c)) {
            return null;
        }
        if (word.length() == 1) {
            return node.children.get(c).explanation;
        } else {
            return _Search(word.substring(1), node.children.get(c));
        }
    }

    public String Search(String word) {
        return _Search(word, root);
    }

    private void _listAllWords(Stack<TreeNode> nodes) {
        if (nodes.empty()) {
            return;
        }
        TreeNode node = nodes.pop();
        if (node.explanation != null){
            System.out.println(node.prefix + node.letter +":" + node.explanation);
        }

        for (TreeNode n : node.children.values()) {
            nodes.push(n);
        }
        _listAllWords(nodes);
    }

    public void listAllWords() {
        Stack<TreeNode> ts = new Stack<>();
        ts.push(root);
        _listAllWords(ts);
    }

    private void _listAllNodes(Stack<TreeNode> nodes) {
        if (nodes.empty()) {
            return;
        }
        TreeNode node = nodes.pop();
        System.out.println(node.toString());
        for (TreeNode n : node.children.values()) {
            nodes.push(n);
        }
        _listAllNodes(nodes);
    }

    public void listAllNodes() {
        Stack<TreeNode> ts = new Stack<>();
        ts.push(root);
        _listAllNodes(ts);
    }
}

public class Main {
    public static void main(String[] args) {
        MyDictionary d = new MyDictionary();
        d.AddWord("abd", "explanation of abc");
        d.AddWord("aba", "explanation of aba");
        d.AddWord("ab", "explanation of ab");
        System.out.println(d.Search("abd"));
        System.out.println(d.Search("abc"));
        d.listAllNodes();
        d.listAllWords();
    }
}
