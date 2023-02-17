package com.capexercise.general.helpers.nodes;


import java.io.Serializable;

public interface TreeNode extends Serializable {
    void setVar(Object value);

    void setFrequency(int freq);

    void setLeft(TreeNode left);

    void setRight(TreeNode right);

    Object getVar();

    int getFrequency();

    TreeNode getLeft();

    TreeNode getRight();

}
