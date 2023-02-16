package com.capexercise;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.huffman.CharZipperUnZipper;
import com.capexercise.general.GeneralClass;
import com.capexercise.general.Path;
import com.capexercise.huffman.GeneralClassImplementation;
import com.capexercise.huffman.WordZipperUnZipper;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException
    {
        Scanner scr = new Scanner(System.in);
        int choice = 0;

        int atleastOnce = 0;
        boolean flag = true;


      // FileZipper zipper = new CharZipperUnZipper();
       FileZipper zipper=new WordZipperUnZipper();
      /*

      if I want some new Zipping Algorithm to be implemented

        General_Package.FileZipper zipper=new SomeNewAlgorithm();

        zipper.compress()  -> it will call that corresponding Algorithms Compression_Package.Compress Method

        zipper.decompress()  -> it will call that corresponding Algorithms Decompression Method
        */


        while (flag) {
            System.out.println("Enter the option\n1.Compression\n2.Decompression\n3.Check validity of the decompressed file\n4.Exit");
            choice = scr.nextInt();
            int startTime= (int)System.currentTimeMillis();
            switch (choice)
            {
                case 1:
                    atleastOnce = 1;
                    zipper.compress();
                    System.out.println("Time for compression:"+((int)System.currentTimeMillis()-startTime));
                    break;

                case 2:

                    if (atleastOnce != 1) {
                        System.out.println("You have to perform compression at least once");
                        break;
                    }
                    zipper.decompress();
                    System.out.println("Time for De-compression:"+((int)System.currentTimeMillis()-startTime));
                    break;

                case 3:
                    if (new GeneralClassImplementation().check(Path.inputFilePath, Path.decompressedFilePath) == true)
                    {
                        System.out.println("Files match!");
                    }
                    else
                    {
                        System.out.println("Files don't match!");
                    }
                    break;

                case 4:flag = false;
                    break;

                default:
                    System.out.println("Enter a valid choice");
            }
        }
    }
}