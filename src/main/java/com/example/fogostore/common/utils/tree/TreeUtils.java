package com.example.fogostore.common.utils.tree;

import com.example.fogostore.model.Brand;
import com.example.fogostore.model.Category;

import java.util.*;
import java.util.stream.Collectors;

public class TreeUtils {

    private static Map<Integer, Node> createTree(List<Node> nodes) {

        LinkedHashMap<Integer, Node> mapTmp = new LinkedHashMap<>();

        //Save all nodes to a map
        for (Node current : nodes) {
            mapTmp.put(current.getId(), current);
        }

        //loop and assign parent/child relationships
        for (Node current : nodes) {
            Integer parentId = current.getParentId();

            if (parentId != null) {
                Node parent = mapTmp.get(parentId);
                if (parent != null) {
                    current.setParent(parent);
                    parent.addChild(current);
                    mapTmp.put(parentId, parent);
                    mapTmp.put(current.getId(), current);
                }
            }
        }

        return mapTmp;
    }

    public static List<Integer> getChidrenListIdFromNode(Node node){
        List<Integer> ids = new ArrayList<>();
        if(node.getChildren().size() == 0) {
            return new ArrayList<>();
        } else {
            for (Object child : node.getChildren()) {
                List<Integer> idList = getChidrenListIdFromNode((Node) child);
                ids.addAll(idList);
            }
        }
        return ids;
    }

    public static Map<Integer, Node> convertCategoryListToTree(List<Category> categories){
        List<Node> nodes = categories.stream().map(cate -> {
            Node node = new Node();
            node.setId(cate.getId());
            node.setParentId(cate.getParentId());
            node.setValue(cate);
            return node;
        }).collect(Collectors.toList());
        return createTree(nodes);
    }

    public static Map<Integer, Node> convertBrandListToTree(List<Brand> brands){
        List<Node> nodes = brands.stream().map(brand -> {
            Node node = new Node();
            node.setId(brand.getId());
            node.setParentId(brand.getParentId());
            node.setValue(brand);
            return node;
        }).collect(Collectors.toList());
        return createTree(nodes);
    }
}
