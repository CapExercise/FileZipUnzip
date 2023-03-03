package com.capexercise.huffman.general;

import com.capexercise.general.IFileContents;

import java.io.File;

public interface InputOutput {

    IFileContents extractContents(File fileObj);

    void addCompressedContents(IFileContents fileContents);
}
