package com.capexercise.huffman.compression;

import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;

public interface ICompress {
    IMap calculateFreq(IDataHandle fileReader);

    StringBuilder appendRemainingZeros(StringBuilder coded);

    int noofZerosToBeAppended(StringBuilder coded);

    byte[] compress(StringBuilder coded);

    void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s, IMap huffmanMap);

    StringBuilder getCodes(IMap huffmanMap, IDataHandle fobj);

}
