import core.utils.AssertionRuntime;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Tng2Test extends TngTest{

    @BeforeClass
    public void setUp(){
        System.out.println("setup");
    }

    @Test
    public void test1(){
        System.out.println("test1");
        Assert.assertEquals(1,1);
        System.out.println("test11");
    }

    @Test(priority = 1)
    public void test2(){
        System.out.println("test2");
        AssertionRuntime.verifyEquals(-1,1);
        System.out.println("test22");
    }

    @Test(priority = 2)
    public void test3(){
        System.out.println("test3");
        Assert.assertEquals(1,1);
        System.out.println("test33");
    }

    @AfterClass
    public void tearDown(){
        System.out.println("tearDown");
    }
}
