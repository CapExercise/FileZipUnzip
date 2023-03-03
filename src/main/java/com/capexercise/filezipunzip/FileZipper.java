package com.capexercise.filezipunzip;

import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.nodes.TreeNode;

import java.io.IOException;
import java.sql.SQLException;

public interface FileZipper {
    void compress() throws SQLException, IOException, ClassNotFoundException;

    void decompress() throws SQLException, IOException, ClassNotFoundException;

    TreeNode constructTree(IMap imap);
}
