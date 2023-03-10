package com.capexercise.general.helpers.nodes;

public class StringTreeNode implements TreeNode {
    private String stringVal;
    private Integer frequency;
    TreeNode left, right;

    public StringTreeNode(String stringVal, int frequency) {
        this.stringVal = stringVal;
        this.frequency = new Integer(frequency);
        this.left = null;
        this.right = null;
    }

    @Override
    public void setVar(Object value) {
        this.stringVal = (String) value;
    }

    @Override
    public void setFrequency(int freq) {
        this.frequency = new Integer(freq);
    }

    @Override
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    @Override
    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public Object getVar() {
        return this.stringVal;
    }

    @Override
    public int getFrequency() {
        return this.frequency.intValue();
    }

    @Override
    public TreeNode getLeft() {
        return this.left;
    }

    @Override
    public TreeNode getRight() {
        return this.right;
    }
}
