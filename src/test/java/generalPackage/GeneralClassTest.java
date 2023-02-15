package generalPackage;

import com.capexercise.general.GeneralClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class GeneralClassTest {


    @Before
    public void beforeTest()
    {
        FileWriter f1=null;
        try {
            f1 =new FileWriter("src/test/java/generalPackage/test.txt");
            f1.write("aabbcd");
            f1.flush();
            f1.close();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileWriter f2=null;
        try {
            f2 =new FileWriter("src/test/java/generalPackage/test1.txt");
            f2.write("aabbcd");
            f2.flush();
            f2.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        FileWriter f6=null;
        try {
            f6 =new FileWriter("src/test/java/generalPackage/test2.txt");
            f6.write("aabbcdd");
            f6.flush();
            f6.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileWriter f7=null;
        try {
            f7 =new FileWriter("src/test/java/generalPackage/test3.txt");
            f7.write("aabbc");
            f7.flush();
            f7.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void TestComparaion()
    {
        try
        {
            assertTrue(GeneralClass.check("src/test/java/generalPackage/test.txt","src/test/java/generalPackage/test1.txt"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TestComparaionfornegativeSituation()
    {
        try
        {
            assertFalse(GeneralClass.check("src/test/java/generalPackage/test2.txt","src/test/java/generalPackage/test3.txt"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @After
    public void afterAll()
    {
        new File("src/test/java/generalPackage/test.txt").delete();
        new File("src/test/java/generalPackage/test1.txt").delete();
        new File("src/test/java/generalPackage/test2.txt").delete();
        new File("src/test/java/generalPackage/test3.txt").delete();
    }



}