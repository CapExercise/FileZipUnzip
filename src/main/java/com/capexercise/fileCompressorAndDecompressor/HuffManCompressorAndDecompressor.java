package com.capexercise.fileCompressorAndDecompressor;

import com.capexercise.compressionPackage.Compress;
import com.capexercise.compressionPackage.ImplementationClassForCompression;
import com.capexercise.decompressionPackage.Decompress;
import com.capexercise.decompressionPackage.ImplemenatationClassForDecompression;
import com.capexercise.generalPackage.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import generalPackage.*;
public class HuffManCompressorAndDecompressor implements FileZipper
{

    @Override
    public void compress()
    {
        Compress c = new ImplementationClassForCompression();

        IFileReader fop=new ImplementationForFileOpearations(Path.inputFilePath);
        try
        {
            Map<Character, Integer> frequencyMap = c.calculateFreq(fop);
            Node root = c.addElementIntoQueueAndReturnRoot(frequencyMap);
            Map<Character,String> HuffMan_Map=new HashMap<>();
            c.iterateTreeAndCalculateHuffManCode(root, "",HuffMan_Map);
            StringBuilder coded=c.getCodes(HuffMan_Map,fop);
            int noOfZerosAppended =c.noofZerosToBeAppended(coded);
            if(noOfZerosAppended !=0)
            {
                coded = c.appendRemainingZeros(coded);
            }
            byte[] byteArray=c.compress(coded);
            ObjectOutputStream outStream=new ObjectOutputStream(new FileOutputStream(Path.compressedFilePath));
            outStream.writeObject(root);
            outStream.writeInt(noOfZerosAppended);
            outStream.writeObject(byteArray);
            outStream.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress()
    {
        Decompress d = new ImplemenatationClassForDecompression();

        try
        {
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
            Node root=(Node) inStream.readObject();
            int noOfZeros=inStream.readInt();
            byte[] byteArray= (byte[])inStream.readObject();
            StringBuilder decoded=d.getDecodedString(byteArray);
            d.writeIntoDecompressedFile(root,decoded,noOfZeros);

        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

    //   Node root=d.returnRootOfTree(in);

//        int no_of_Zeros=0;
//        try {
//           no_of_Zeros=d.returnNoofZeros(in);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }




        System.out.println("De-Compression done Successfully");


        GeneralClass.displayStats(Path.inputFilePath,Path.compressedFilePath,Path.decompressedFilePath);

    }
}
