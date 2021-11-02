package android.cases;

import android.pageobject.MobileTestPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileTestCase extends BaseCase {

    MobileTestPage mPage = new MobileTestPage(driver);

    @Test
    public void swipeTest() {
        mPage.swipeTest();
        Assert.assertEquals(1, 1);//todo
    }
}
