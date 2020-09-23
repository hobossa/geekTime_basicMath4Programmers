package com.hoboss;

import com.sun.source.tree.Tree;

import java.util.HashMap;

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
}

class MyDictionary {
    private TreeNode root = null;

    public MyDictionary() {
        root = new TreeNode(null, null);
    }

    private boolean _AddWord(String word, TreeNode node, String pre, String exp) {
        if (word.length() > 0) {
            char c = word.charAt(0);
            boolean newNode = false;

            if (!node.children.containsKey(c)) {
                newNode = true;
                node.children.put(c, new TreeNode(c, pre));
            }

            if (word.length() == 1) {
                if (newNode) {
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
        return "";
    }

    public String Search(String work) {
        String explanation = null;
        return explanation;
    }

    public void listAllWords() {

    }
}

public class Main {
    public static void main(String[] args) {

    }
}
