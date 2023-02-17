package com.capexercise.general.helpers.nodes;

public class CharTreeNode implements TreeNode {

    private Character character;
    private Integer frequency;
    TreeNode left, right;

    public CharTreeNode(char character, int frequency) {
        this.character = new Character(character);
        this.frequency = new Integer(frequency);
        this.left = null;
        this.right = null;
    }

    @Override
    public void setVar(Object value) {
        this.character = new Character((char) value);
    }

    @Override
    public void setFrequency(int freq) {
        this.frequency = frequency;
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
        return this.character.charValue();
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
