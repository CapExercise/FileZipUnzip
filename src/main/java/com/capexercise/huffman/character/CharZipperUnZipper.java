package com.capexercise.huffman.character;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.helpers.input.FileHandler;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.nodes.CharTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.MapImplemenationForChar;
import com.capexercise.huffman.compression.GeneralMethods;
import com.capexercise.huffman.compression.IGeneral;
import com.capexercise.huffman.compression.Compress;
import com.capexercise.huffman.character.compressor.CharCompress;
import com.capexercise.huffman.decompression.Decompress;
import com.capexercise.huffman.character.decompressor.CharDecompress;
import com.capexercise.general.*;

import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;

public class CharZipperUnZipper implements FileZipper
{

    @Override
    public void compress()
    {
        Compress c = new CharCompress();
        IGeneral generalInterface=new GeneralMethods();
        IDataHandle fop=new FileHandler(Path.inputFilePath);
        try
        {
          IMap compressionMaps = c.calculateFreq(fop);
          //  TreeNode root = c.addElementIntoQueueAndReturnRoot(frequencyMap);
            TreeNode root=this.constructTree(compressionMaps);
         //   IMap HuffMan_Map=new MapImplemenationForChar();
            c.iterateTreeAndCalculateHuffManCode(root, "",compressionMaps);
            StringBuilder coded=c.getCodes(compressionMaps,fop);
            int noOfZerosAppended =generalInterface.noofZerosToBeAppended(coded);
            if(noOfZerosAppended !=0)
            {
                coded = generalInterface.appendRemainingZeros(coded);
            }
            byte[] byteArray=generalInterface.compress(coded);
            compressionMaps.clearHuffMap();
            ObjectOutputStream outStream=new ObjectOutputStream(new FileOutputStream(Path.compressedFilePath));
            outStream.writeObject(compressionMaps.returnMap());
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
        Decompress d = new CharDecompress();
        IGeneral generalInterface=new GeneralMethods();
        try
        {
            //
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
            Map<Character,Integer> freq=(Map<Character, Integer>) inStream.readObject();
            IMap decompressionMap=new MapImplemenationForChar(freq,null);
            TreeNode  root=this.constructTree(decompressionMap);
            //TreeNode root=(TreeNode)  inStream.readObject();
            int noOfZeros=inStream.readInt();
            byte[] byteArray= (byte[])inStream.readObject();
            StringBuilder decoded=d.getDecodedString(byteArray);
            d.writeIntoDecompressedFile(root,decoded,noOfZeros);

        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println("De-Compression done Successfully");


        generalInterface.displayStats(Path.inputFilePath,Path.compressedFilePath,Path.decompressedFilePath);

    }

    @Override
    public TreeNode constructTree(IMap frequencyMap) {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(frequencyMap.freqSize(), new FrequencyComparator());
        Map<Character,Integer> freq= (Map<Character, Integer>) frequencyMap.returnMap();

        for (Map.Entry<Character, Integer> entry : freq.entrySet())
        {
            TreeNode nd = new CharTreeNode(entry.getKey(),entry.getValue());
            pq.add(nd);
        }
        TreeNode root = null;
        if(pq.size()==1)
        {
            TreeNode leftSideNode=pq.peek();
            pq.poll();
            TreeNode newNode = new CharTreeNode('-', leftSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(null);
            root=newNode;
            return root;
        }
        while (pq.size() > 1) {
            TreeNode leftSideNode= pq.poll();

            TreeNode rightSideNode = pq.poll();
            TreeNode newNode = new CharTreeNode('-',leftSideNode.getFrequency() + rightSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(rightSideNode);
            root = newNode;
            pq.add(newNode);
        }
        return root;
    }
}
