package com.example.fogostore.common.utils.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Node<T> {
    private Integer id;           //Current node id, Example: 2
    private Integer parentId;     //Parent node id, Example: 1
    private T value;       // Example: Two
    private Node parent;
    private List<Node> children = new ArrayList<>();

    public Node(){

    }

    public void addChild(Node child) {
        if (!this.children.contains(child) && child != null)
            this.children.add(child);
    }

    @Override
    public String toString() {
        return "Node [id=" + id + ", parentId=" + parentId + ", value=" + value + ", children="
                + children + "]";
    }
}
