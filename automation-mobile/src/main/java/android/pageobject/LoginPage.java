package android.pageobject;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BasePage {

    @AndroidFindBy(xpath = "//android.view.View[2]/android.widget.EditText")
    @iOSXCUITFindBy(xpath = "//android.view.View[2]/android.widget.EditText")
    MobileElement userElement;

    @AndroidFindBy(xpath = "//android.view.View[3]/android.widget.EditText")
    @iOSXCUITFindBy(xpath = "//android.view.View[3]/android.widget.EditText")
    MobileElement passwordElement;

    @AndroidFindBy(xpath = "//android.widget.Button")
    @iOSXCUITFindBy(xpath = "//android.widget.Button")
    MobileElement loginButton;

    public HomePage login(String username, String password) {
        userElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginButton.click();
        return new HomePage(driver);
    }

    public LoginPage(MobileDriver driver) {
        super(driver);
    }

}
