package com.capexercise.huffman.decompression;

import com.capexercise.general.helpers.nodes.TreeNode;

import java.util.ArrayList;

public interface IDecompress {
    ArrayList<Integer> get8bitcode(int val) throws RuntimeException;

    StringBuilder getDecodedString(byte[] byteArray);

    void writeIntoDecompressedFile(TreeNode root, StringBuilder decoded, int noOfZeros);

}
