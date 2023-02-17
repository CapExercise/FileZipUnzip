package com.capexercise.huffman.compression;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;

public interface Compress
{
  IMap calculateFreq(IDataHandle fileReader);
    void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s, IMap huffmanMap);
    StringBuilder getCodes(IMap huffmanMap, IDataHandle fobj);

}
