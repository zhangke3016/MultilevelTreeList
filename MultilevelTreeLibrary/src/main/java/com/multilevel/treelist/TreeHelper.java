package com.multilevel.treelist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangke on 2017-1-14.
 */
public class TreeHelper {

    /**
     * 传入node  返回排序后的Node
     *
     * @param datas
     * @param defaultExpandLevel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static List<Node> getSortedNodes(List<Node> datas,
                                                int defaultExpandLevel) {
        List<Node> result = new ArrayList<Node>();
        // 设置Node间父子关系
        List<Node> nodes = convetData2Node(datas);
        // 拿到根节点
        List<Node> rootNodes = getRootNodes(nodes);
        // 排序以及设置Node间关系
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     *
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNode(List<Node> nodes) {
        List<Node> result = new ArrayList<Node>();

        for (Node node : nodes) {
            // 如果为跟节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
     */
     private static List<Node> convetData2Node(List<Node> nodes) {

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if(m.getpId() instanceof String){
                    if (m.getpId().equals(n.getId())) {
                        n.getChildren().add(m);
                        m.setParent(n);
                    } else if (m.getId().equals(n.getpId())) {
                        m.getChildren().add(n);
                        n.setParent(m);
                    }
                }else{
                    if (m.getpId() == n.getId()) {
                        n.getChildren().add(m);
                        m.setParent(n);
                    } else if (m.getId() == n.getpId()) {
                        m.getChildren().add(n);
                        n.setParent(m);
                    }
                }
            }
        }
        return nodes;
    }

    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     */
    private static <T,B> void addNode(List<Node> nodes, Node<T,B> node,
                                int defaultExpandLeval, int currentLevel) {
        nodes.add(node);

        if (node.isNewAdd && defaultExpandLeval >= currentLevel) {
            node.setExpand(true);
        }

        if (node.isLeaf())
            return;
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(nodes, node.getChildren().get(i), defaultExpandLeval,
                    currentLevel + 1);
        }
    }

    /**
     * 设置节点的图标
     *
     * @param node
     */
    private static void setNodeIcon(Node node) {
        if (node.getChildren().size() > 0 && node.isExpand()) {
            node.setIcon(node.iconExpand);
        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
            node.setIcon(node.iconNoExpand);
        } else {
            node.setIcon(-1);
        }
    }

}
