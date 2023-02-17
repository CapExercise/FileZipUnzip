package com.capexercise.filezipunzip;

import com.capexercise.general.IMap;
import com.capexercise.general.TreeNode;

public interface FileZipper {
    void compress();

   void decompress();

   TreeNode constructTree(IMap imap);
}
