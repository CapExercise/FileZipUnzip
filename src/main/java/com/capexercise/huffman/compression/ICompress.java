package com.capexercise.huffman.compression;

import com.capexercise.general.helpers.filedata.IFileContents;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;

public interface ICompress {
    IMap calculateFreq(IDataHandle fileReader);

    void iterateTreeAndCalculateHuffManCode(TreeNode newNode, String s, IMap huffmanMap);

    IFileContents compress(IMap iMap, IDataHandle dataObj);

}
