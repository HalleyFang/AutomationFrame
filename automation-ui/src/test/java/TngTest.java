import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TngTest {

    @BeforeClass
    public void tSetUp(){
        System.out.println("t setUp");
    }

    @AfterClass
    public void tTearDown(){
        System.out.println("t tearDown");
    }
}
