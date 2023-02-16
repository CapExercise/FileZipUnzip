package com.capexercise.general;


public interface TreeNode {
    void setVar(Object value);
    void setFrequency(int freq);

    void setLeft(TreeNode left);

    void setRight(TreeNode right);
    Object getVar();
    int getFrequency();
    TreeNode getLeft();
    TreeNode getRight();

}
