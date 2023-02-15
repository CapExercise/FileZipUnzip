package com.capexercise.generalPackage;

import java.io.Serializable;

public class Node implements Serializable
{
   public char var;
    public int frequency=0;
    public Node left=null;
    public Node right=null;

    public char getVar() {
        return var;
    }

    public void setVar(char var) {
        this.var = var;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
