package decompressionPackage;

import static org.mockito.Mockito.spy;

public class ImplemenatationClassForDecompressionTest
{
    /*

    CharDecompress d=new CharDecompress();

    Node root=null;
    @Before
    public void beforeTest()
    {
        Node leftNode=new Node();
        leftNode.setVar('a');
        leftNode.setFrequency(6);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        Node rightNode=new Node();
        rightNode.setVar('b');
        rightNode.setFrequency(7);
        rightNode.setRight(null);
        rightNode.setLeft(null);

        Node rootNode=new Node();
        rootNode.setFrequency(leftNode.getFrequency()+ rightNode.getFrequency());
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        root=rootNode;
    }



  @Test
    public void Testget8BItCode()
  {
      ArrayList<Integer> expectedArray=new ArrayList<>();
      for(int i=0;i<8;i++)
      {
          expectedArray.add(0);
      }
      ArrayList<Integer> returnedArray= null;
      try {
          returnedArray = d.get8bitcode(0);
      } catch (RuntimeException e) {
          throw new RuntimeException(e);
      }
      assertTrue(expectedArray.equals(returnedArray));
  }


    @Test
    public void Testget8BitCode1()
    {
        ArrayList<Integer> expectedArray=new ArrayList<>();
        for(int i=0;i<6;i++)
        {
            expectedArray.add(0);
        }
        expectedArray.add(1);
        expectedArray.add(0);
        ArrayList<Integer> returnedArray= null;
        try {
            returnedArray = d.get8bitcode(2);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        assertTrue(expectedArray.equals(returnedArray));

    }

    @Test(expected = RuntimeException.class)
    public void Test8BitCode2()
    {
        ArrayList<Integer> expectedArray=new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            expectedArray.add(0);
        }
        try {
            ArrayList<Integer> returnedArray=d.get8bitcode(-2);
        } catch(RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void TestgetCodedString_UsingMocking()
    {

      CharDecompress mockedDecompression= Mockito.spy(d);

       ArrayList<Integer> list1=new ArrayList<Integer>(Arrays.asList(0,1,0,1,0,0,1,0));
       ArrayList<Integer> list2=new ArrayList<Integer>(Arrays.asList(0,1,1,1,1,0,0,0));
       ArrayList<Integer> list3=new ArrayList<Integer>();

      Mockito.doReturn(list1).when(mockedDecompression).get8bitcode(82);
      Mockito.doReturn(list2).when(mockedDecompression).get8bitcode(120);
      Mockito.doReturn(list3).when(mockedDecompression).get8bitcode(0);
      assertEquals(mockedDecompression.getDecodedString(new byte[]{82}).toString(),"01010010");
      assertEquals(mockedDecompression.getDecodedString(new byte[]{82,120}).toString(),"0101001001111000");
      assertEquals(mockedDecompression.getDecodedString(new byte[]{}).toString(),"");
    }

    @Test
    public void TestgoLeftOrRight()
    {
        Node leftNode=new Node();
        leftNode.setVar('a');
        leftNode.setFrequency(7);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        TreeNode rightNode=new TreeNode();
        rightNode.setVar('b');
        rightNode.setFrequency(5);
        rightNode.setLeft(null);
        rightNode.setRight(null);

        TreeNode parentNode=new Node();
        parentNode.setVar('-');
        parentNode.setFrequency(leftNode.getFrequency()+rightNode.getFrequency());
        parentNode.setLeft(leftNode);
        parentNode.setRight(rightNode);

       assertEquals(leftNode.getVar(),d.goLeftorRightAndReturnNode(parentNode,'0').getVar());
       assertEquals(rightNode.getVar(),d.goLeftorRightAndReturnNode(parentNode,'1').getVar());
    }

    @Test
    public void TestwriteIntoDecompressedFile()
    {
        StringBuilder decodedString=new StringBuilder("0101001001111000");

        try {
            d.writeIntoDecompressedFile(root,decodedString,3);
            FileReader fin=new FileReader(Path.decompressedFilePath);
            StringBuilder expected=new StringBuilder();
            int c=fin.read();
            while(c!=-1)
            {
                expected.append((char)c);
                c=fin.read();
            }
            assertEquals("ababaabaabbbb",expected.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TestwriteIntDEcompressedFile()
    {
        StringBuilder decodedString=new StringBuilder("01101000");

        Node leftNode=new Node();
        leftNode.setRight(null);
        leftNode.setLeft(null);
        leftNode.setVar('a');
        leftNode.setFrequency(2);

        Node rightLeft=new Node();
        rightLeft.setVar('b');
        rightLeft.setFrequency(3);
        rightLeft.setRight(null);
        rightLeft.setLeft(null);

        Node rightRight=new Node();
        rightRight.setVar('c');
        rightRight.setFrequency(2);
        rightRight.setLeft(null);
        rightRight.setRight(null);

        Node rightNode=new Node();
        rightNode.setLeft(rightLeft);
        rightNode.setRight(rightRight);
        rightNode.setVar('-');
        rightNode.setFrequency(rightRight.getFrequency()+rightLeft.getFrequency());


        Node parent=new Node();
        parent.setLeft(leftNode);
        parent.setRight(rightNode);
        parent.setFrequency(leftNode.getFrequency()+rightNode.getFrequency());

        try
        {
            d.writeIntoDecompressedFile(parent,decodedString,2);
            FileReader fin=new FileReader(Path.decompressedFilePath);
            StringBuilder expected=new StringBuilder();
            int c=fin.read();
            while(c!=-1)
            {
                expected.append((char)c);
                c=fin.read();
            }
            assertEquals("abbaba",expected.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


     */

}