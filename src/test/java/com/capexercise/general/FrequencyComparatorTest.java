package com.capexercise.general;

import com.capexercise.general.helpers.nodes.CharTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class FrequencyComparatorTest {

    Comparator<TreeNode> testRef;
    TreeNode node1, node2;

    @Before
    public void setup(){
        testRef = new FrequencyComparator();

        node1 = new CharTreeNode('a',2);
        node2 = new CharTreeNode('b',3);
    }

    @Test
    public void testCompareForNegative() {
        int actual = testRef.compare(node1, node2);
        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    public void testCompareForPositive(){
        int actual = testRef.compare(node2, node1);
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void testCompareForEqualNodes(){
        TreeNode node3 = new CharTreeNode('c',3);
        int actual = testRef.compare(node2, node3);
        int expected = -1;
        assertEquals(expected, actual);
    }
}