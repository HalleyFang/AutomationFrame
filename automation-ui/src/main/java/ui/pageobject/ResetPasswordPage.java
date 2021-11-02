package ui.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

/**
 * 忘记密码找回页面
 */
public class ResetPasswordPage extends BasePage {

    @Override
    public String getWaitElementXpath() {
        return "//input[@placeholder='请输入要找回的用户名']";
    }

    ResetPasswordPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//div[@class='titleStyle']")
    @CacheLookup
    private WebElement title;
    @FindBy(xpath = "//input[@placeholder='请输入要找回的用户名']")
    @CacheLookup
    private WebElement username;
}
