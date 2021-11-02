package android.pageobject;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    MobileDriver driver;

    public BasePage(PerformsTouchActions performsTouchActions) {
        this.driver = (MobileDriver) performsTouchActions;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
