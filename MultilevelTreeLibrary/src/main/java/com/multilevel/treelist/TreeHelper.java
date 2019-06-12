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
        List<Node> res = convert(nodes);
        return res;
    }
     /**
     * 设置Node间，父子关系;使用MAP优化效率
     */
    public static List<Node> convert(List<Node> sourceNodeList) {
        List<Node> destNodeList = new ArrayList<Node>();
        //第一步，找出第一级的节点
        //1.1 统计所有节点的id
        List<String> allIds = new ArrayList<String>();
        Map<String, List<Node>> pIdMap = new HashMap<>();
        for (Node tempNode : sourceNodeList) {
            allIds.add((String) tempNode.getId());
            //构造一个存储Pid得map.递归中直接使用键值查找
            String pid = (String) tempNode.getpId();
            if (pIdMap.containsKey(pid)) {
                pIdMap.get(pid).add(tempNode);
            } else {
                List<Node> valueList = new ArrayList<>();
                valueList.add(tempNode);
                pIdMap.put(pid, valueList);
            }

        }

        //所有父节点找不到对应的都是一级id
        for (Node sourceNode : sourceNodeList) {
            if (!allIds.contains(sourceNode.getpId())) {
                //从每个一级节点，递归查找children
                Node destNode = new Node();
                destNode.setId(sourceNode.getId());
                destNode.setName(sourceNode.getName());
                destNode.setpId(sourceNode.getpId());
                destNode.setLevel(1);
                destNode.bean = sourceNode.bean;
                destNode.iconExpand = sourceNode.iconExpand;
                destNode.iconNoExpand = sourceNode.iconNoExpand;
                List<Node> myChilds = getChilderen(destNode, pIdMap);
                destNode.setChildren(myChilds.isEmpty() ? new ArrayList<>() : myChilds);
                destNodeList.add(destNode);
            }
        }
        return destNodeList;
    }


    //    递归获取子节点
    private static List<Node> getChilderen(Node parentNode, Map<String, List<Node>> pIdMap) {
        List<Node> childrenList = new ArrayList<Node>();
        //使用map来进行查找优化效率
        if (!pIdMap.containsKey(parentNode.getId())) {
            return childrenList;
        } else {
            List<Node> sourceNode = pIdMap.get(parentNode.getId());
            for (Node node : sourceNode) {
                Node children = new Node();
                children.setId(node.getId());
                children.setName(node.getName());
                children.setpId(node.getpId());
                children.setLevel(node.getLevel() + 1);
                children.bean = node.bean;
                children.iconExpand = node.iconExpand;
                children.iconNoExpand = node.iconNoExpand;
                children.setParent(parentNode);
                List<Node> myChilds = getChilderen(children, pIdMap);
                children.setChildren(myChilds.isEmpty() ? new ArrayList<>() : myChilds);
                childrenList.add(children);
            }

            return childrenList;
        }

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
