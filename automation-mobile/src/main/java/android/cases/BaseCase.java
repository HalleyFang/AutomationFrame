package android.cases;

import android.config.BaseConf;
import android.config.TestType;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;

public class BaseCase {

    MobileDriver driver;

    TestType type;

    public void setType(TestType type) {
        this.type = type;
    }

    @BeforeClass
    public void globalSetup() throws MalformedURLException {
        if (TestType.ANDROID.equals(type)) {
            this.driver = new AndroidDriver(BaseConf.getAppiumUrl(), BaseConf.getDesiredCapabilities());
        } else if (TestType.IOS.equals(type)) {
            this.driver = new IOSDriver(BaseConf.getAppiumUrl(), BaseConf.getDesiredCapabilities());
        }
    }

    @AfterClass
    public void globalTearDown() {
        this.driver.close();
    }
}
