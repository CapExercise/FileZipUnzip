package com.capexercise.general.helpers.nodes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CharTreeNodeTest {

    TreeNode testRef,left,right;

    @Before
    public void setup(){

        testRef = new CharTreeNode('$',0);

        left = new CharTreeNode('a',2);
        right = new CharTreeNode('b',3);

    }

    @Test
    public void testSetVar() {
        TreeNode mySpy = Mockito.spy(testRef);
        mySpy.setVar('x');
        Mockito.verify(mySpy, Mockito.times(1)).setVar('x');
    }

    @Test
    public void testSetFrequency() {
        TreeNode mySpy = Mockito.spy(testRef);
        mySpy.setFrequency(5);
        Mockito.verify(mySpy, Mockito.times(1)).setFrequency(5);
    }

    @Test
    public void testSetLeft() {
        TreeNode mySpy = Mockito.spy(testRef);
        mySpy.setLeft(left);
        Mockito.verify(mySpy, Mockito.times(1)).setLeft(left);
    }

    @Test
    public void testSetRight() {
        TreeNode mySpy = Mockito.spy(testRef);
        mySpy.setRight(right);
        Mockito.verify(mySpy, Mockito.times(1)).setRight(right);
    }

    @Test
    public void testGetVar() {
        testRef.setVar('a');
        char actual = (char) testRef.getVar();
        char expected = 'a';
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFrequency() {
        testRef.setFrequency(3);
        int actual = testRef.getFrequency();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetLeft() {
        testRef.setLeft(left);
        TreeNode actual = testRef.getLeft();
        TreeNode expected = left;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRight() {
        testRef.setRight(right);
        TreeNode actual = testRef.getRight();
        TreeNode expected = right;
        assertEquals(expected, actual);
    }
}