package com.capexercise.huffman.compression;

import com.capexercise.general.IFileReader;
import com.capexercise.general.IMap;
import com.capexercise.general.Node;
import com.capexercise.general.TreeNode;

import java.util.Map;

public interface Compress
{
  IMap calculateFreq(IFileReader fileReader);
    void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s, IMap huffmanMap);
    StringBuilder getCodes(IMap huffmanMap, IFileReader fobj);

}
