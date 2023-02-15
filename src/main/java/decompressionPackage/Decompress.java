package decompressionPackage;

import generalPackage.Node;

import java.io.IOException;
import java.util.ArrayList;

public interface Decompress
{
    ArrayList<Integer> get8bitcode(int val) throws RuntimeException;
    Node goLeftorRightAndReturnNode(Node root,char val);
    StringBuilder getDecodedString(byte[] byteArray);
    void writeIntoDecompressedFile(Node root, StringBuilder decoded, int no_of_zeros) throws IOException;

}
