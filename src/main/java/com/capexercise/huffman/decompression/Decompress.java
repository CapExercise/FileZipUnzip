package com.capexercise.huffman.decompression;

import com.capexercise.general.helpers.nodes.TreeNode;

import java.io.IOException;
import java.util.ArrayList;

public interface Decompress
{
    ArrayList<Integer> get8bitcode(int val) throws RuntimeException;
    TreeNode goLeftorRightAndReturnNode(TreeNode root, char val);
    StringBuilder getDecodedString(byte[] byteArray);
    void writeIntoDecompressedFile(TreeNode root, StringBuilder decoded, int no_of_zeros) throws IOException;

}
