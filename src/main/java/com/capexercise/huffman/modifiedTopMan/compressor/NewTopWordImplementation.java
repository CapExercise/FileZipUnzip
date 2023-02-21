package com.capexercise.huffman.modifiedTopMan.compressor;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.FileContents;
import com.capexercise.general.FrequencyComparator;
import com.capexercise.general.IFileContents;
import com.capexercise.general.Path;
import com.capexercise.general.helpers.input.FileHandler;
import com.capexercise.general.helpers.input.IDataHandle;
import com.capexercise.general.helpers.maps.IMap;
import com.capexercise.general.helpers.maps.WordMaps;
import com.capexercise.general.helpers.nodes.StringTreeNode;
import com.capexercise.general.helpers.nodes.TreeNode;
import com.capexercise.huffman.decompression.IDecompress;
import com.capexercise.huffman.general.GeneralMethods;
import com.capexercise.huffman.general.IGeneral;
import com.capexercise.huffman.topword.decompressor.TopWordDecompress;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class NewTopWordImplementation implements FileZipper
{
    IGeneral method;

    int cur_min=Integer.MAX_VALUE;
    int prec=0;
    public NewTopWordImplementation(){
        method = new GeneralMethods();
    }


    @Override
    public void compress()
    {
        int len1= (int) new File("src/main/java/com/capexercise/Files/input.txt").length();
      modifiedTopWordCompress compressor = new modifiedTopWordCompress();

        IDataHandle dataObj = new FileHandler(Path.inputFilePath);

     IMap ref=null;
          for(int i=10;i<70;i=i+10)
          {
              IMap compressionMaps =new WordMaps();
              dataObj.set(i);
           compressionMaps= compressor.calculateFreq(dataObj);

          TreeNode root = this.constructTree(compressionMaps);

          compressor.iterateTreeAndCalculateHuffManCode(root, "", compressionMaps);

              StringBuilder coded = compressor.getCodes(compressionMaps, dataObj);

        int sum=0;
//          for(Map.Entry<Object,String> entry:compressionMaps.returnHuffMap().entrySet())
//          {
//              int len=entry.getValue().length();
//
//              int freq=compressionMaps.getFrequency(entry.getKey());
//              sum+=len*freq;
//          }
              File f=new File("src/main/java/com/capexercise/Files/mapSize.txt");
              try {
                  ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
                  out.writeObject(compressionMaps.returnFreqMap());
                  out.close();
                  sum += f.length();
                  sum+=coded.length()/8;
                  f.delete();
              } catch (FileNotFoundException e) {
                  throw new RuntimeException(e);
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }

              if(sum<cur_min)
          {

              cur_min=sum;
              prec=i;
              ref=compressionMaps;
              System.out.println("For percentage "+i+" total sum "+(float)sum);

          }
              compressionMaps.setFreqMap(new HashMap<>());
              compressionMaps.setHuffMap(new HashMap<>());
       }

        System.out.println("percentage used for compression is "+prec);

          IMap cd=new WordMaps();
          dataObj.set(prec);
        cd= compressor.calculateFreq(dataObj);

        TreeNode root = this.constructTree(cd);

        compressor.iterateTreeAndCalculateHuffManCode(root, "", cd);

        StringBuilder coded = compressor.getCodes(cd, dataObj);

        int noOfZerosAppended = compressor.noofZerosToBeAppended(coded);

        if (noOfZerosAppended != 0)
            coded = compressor.appendRemainingZeros(coded);

        byte[] byteArray = compressor.compress(coded);

        cd.clearHuffMap();

        IFileContents fileContents = new FileContents(cd.returnFreqMap(), noOfZerosAppended, byteArray);
        method.addCompressedContents(fileContents);
        File f=new File("src/main/java/com/capexercise/Files/mapSize.txt");
        try
        {
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(cd.returnFreqMap());
            out.close();
            System.out.println("top Size of map is "+f.length());
            System.out.println("Average bit for top word is "+(float)(coded.length())/(new File("src/main/java/com/capexercise/Files/input.txt").length()));
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress() {
//        IDecompress decompressor = new TopWordDecompress();
        modifiedTopWordDecompress decompressor=new modifiedTopWordDecompress();
        IFileContents fileContents = method.extractContents(new File(Path.compressedFilePath));
        Map<Object, Integer> freq = fileContents.getFrequencyMap();
        int noOfZeros = fileContents.getExtraBits();
        byte[] byteArray = fileContents.getByteArray();

        IMap decompressionMap = new WordMaps(freq, null);
        TreeNode root = this.constructTree(decompressionMap);
        StringBuilder decoded = decompressor.getDecodedString(byteArray);
        decompressor.writeIntoDecompressedFile(root, decoded, noOfZeros);

        System.out.println("De-Compression done Successfully");

        method.displayStats(Path.inputFilePath, Path.compressedFilePath, Path.decompressedFilePath);

    }

    @Override
    public TreeNode constructTree(IMap imap)
    {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>(imap.freqSize(), new FrequencyComparator());
        Map<Object, Integer> freq = (Map<Object, Integer>) imap.returnFreqMap();

        for (Map.Entry<Object, Integer> entry : freq.entrySet()) {
            TreeNode node = new StringTreeNode((String) entry.getKey(), entry.getValue());
            pq.add(node);
        }
        TreeNode root = null;
        if (pq.size() == 1) {
            TreeNode leftSideNode = pq.peek();
            pq.poll();
            TreeNode newNode = new StringTreeNode("-", leftSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(null);
            root = newNode;
            return root;
        }
        while (pq.size() > 1) {
            TreeNode leftSideNode = pq.poll();

            TreeNode rightSideNode = pq.poll();
            TreeNode newNode = new StringTreeNode("-", leftSideNode.getFrequency() + rightSideNode.getFrequency());
            newNode.setLeft(leftSideNode);
            newNode.setRight(rightSideNode);
            root = newNode;
            pq.add(newNode);
        }
        return root;
    }


}
