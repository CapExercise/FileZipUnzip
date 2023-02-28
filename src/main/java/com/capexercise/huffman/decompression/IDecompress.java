package com.capexercise.huffman.decompression;

import com.capexercise.general.helpers.nodes.TreeNode;

import java.util.ArrayList;

public interface IDecompress {

    void decompress(byte[] byteArray, int noOfZeroes, TreeNode root);

    String getCode(int val);
}
