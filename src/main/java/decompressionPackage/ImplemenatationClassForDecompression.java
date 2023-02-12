package decompressionPackage;

import generalPackage.Node;
import generalPackage.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import generalPackage.*;

import javax.jws.soap.SOAPBinding;

public class ImplemenatationClassForDecompression implements Decompress{


    @Override
    public Node returnRootOfTree(ObjectInputStream in)
    {
            Node root=null;
            try
            {
                root=(Node)in.readObject();
            }
            catch(Exception e)
            {
               throw new RuntimeException(e);
            }
            return root;
    }
    @Override
    public int returnNoofZeros(ObjectInputStream ip) throws IOException
    {
        int no_of_Zeros=0;
        no_of_Zeros=ip.readInt();
        return no_of_Zeros;
    }
    @Override
    public ArrayList<Integer> get8bitcode(int val)
    {
        if(val<0)
        {
          throw new RuntimeException();
        }
        //this method will do the decimal to binary conversion( 8 bit code)
        ArrayList<Integer> ans=new ArrayList<>();
        while(val!=0)
        {
            ans.add(val%2);
            val=val/2;
        }
        if(ans.size()<8)
        {
            while(ans.size()<8)
            {
                ans.add(0);
            }
        }
        Collections.reverse(ans);

        return ans;
    }
    @Override
    public Node goLeftorRightAndReturnNode(Node root, char val)
    {
        if(val=='0')
        {
            root=root.left;
            return root;
        }
        else
        {
            root=root.right;
            return root;
        }
    }

    @Override
    public StringBuilder getDecodedString(byte[] byteArray)
    {
        StringBuilder decoded = new StringBuilder();

        for (byte x : byteArray) {
            int val = x;
            ArrayList<Integer> newip = null;
            newip = this.get8bitcode(val < 0 ? val + 256 : val);
            for (int m = 0; m < 8; m++) {
                decoded.append(newip.get(m));
            }
        }

        return decoded;
    }

    @Override
    public void getFinal(Node root,StringBuilder decoded,int no_of_zeros)
    {

            Node head=root;

            FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(Path.decompressedFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(int i=0;i<decoded.length()-no_of_zeros;i++)
            {
                char cc=(decoded.charAt(i));
                Node newNode=goLeftorRightAndReturnNode(root,cc);
                if(newNode.left==null && newNode.right==null)
                {
                    try {
                        fileWriter.write(newNode.var);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //  finalAns.append(newNode.var);
                    root=head;
                }
                else
                {
                    root=newNode;
                }
            }
          //  return finalAns;
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        catch (IOException | ClassNotFoundException e)
//        {
//            throw new RuntimeException(e);
//        }


    }
}