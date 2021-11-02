import core.utils.ReadExcel;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataTest {

    @Test(dataProvider = "data")
    public void dateTest(String username, String password) {
        System.out.println(username);
        System.out.println(password);
    }

    @DataProvider(name = "data")
    public Object[][] data() {
        Object[][] objects = ReadExcel.readExcelObject("login");
        return objects;
    }
}
