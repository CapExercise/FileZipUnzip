package com.capexercise.filezipunzip;

import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;

public interface FileZipper {
    void compress();

   void decompress();

   TreeNode constructTree(IMap imap);
}
