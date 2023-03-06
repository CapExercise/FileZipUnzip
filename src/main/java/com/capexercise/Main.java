package com.capexercise;

import com.capexercise.filezipunzip.FileZipper;
import com.capexercise.general.Path;
import com.capexercise.huffman.variations.character.CharZipperUnZipper;
import com.capexercise.huffman.general.auxiliary.GeneralMethods;
import com.capexercise.huffman.variations.modtopword.ModTopWordZipperUnzipper;
import com.capexercise.huffman.variations.topword.TopWordZipperUnZipper;
import com.capexercise.huffman.variations.word.WordZipperUnZipper;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        Scanner scr = new Scanner(System.in);
        int choice = 0;

        int atleastOnce = 0;
        boolean flag = true;

       FileZipper zipper=null;
        System.out.println("Enter 1 for Using Character based compression\nEnter 2 for Word based Compression\nEnter 3 for Combination of Word and Character based Compression\nEnter 4 for modified Top Word");
        choice=scr.nextInt();


            switch (choice)
            {
                case 1:
                    zipper = new CharZipperUnZipper();
                    break;
                case 2:
                    zipper = new WordZipperUnZipper();
                    break;
                case 3:
                    zipper = new TopWordZipperUnZipper();
                    break;
                case 4:
                     zipper=new ModTopWordZipperUnzipper();
                     break;

            }

        while (flag) {

            System.out.println("Enter the option\n1.Compression\n2.Decompression\n3.Check validity of the decompressed file\n4.Exit");
            choice = scr.nextInt();
            int startTime = (int) System.currentTimeMillis();
            switch (choice) {
                case 1:
                    atleastOnce = 1;
                    try {
                        zipper.compress();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Time for compression:" + ((int) System.currentTimeMillis() - startTime));
                    break;

                case 2:

                    if (atleastOnce != 1) {
                        System.out.println("You have to perform compression at least once");
                        break;
                    }
                    try {
                        zipper.decompress();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Time for De-compression:" + ((int) System.currentTimeMillis() - startTime));
                    break;

                case 3:
                    if (new GeneralMethods().check(Path.inputFilePath, Path.decompressedFilePath)) {
                        System.out.println("Files match!");
                    }
                    else
                    {
                        System.out.println("Files don't match!");
                    }
                    break;

                case 4:flag=false;
                break;

                default:
                    System.out.println("Enter a valid choice");
            }
        }



    }
}