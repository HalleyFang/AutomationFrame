package ui.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

/**
 * 登出页
 */
public class LogoutPage extends BasePage{

    private String urlPath = "/logout";

    @FindBy(xpath = "//span[text()='您已成功登出']")
    @CacheLookup
    private WebElement logout_msg ;

    @FindBy(xpath = "//span[text()='请重新登录']")
    @CacheLookup
    private WebElement re_login;

    public LoginPage reLogin(){
        this.re_login.click();
        return new LoginPage(driver);
    }

    public String logoutMsg(){
        return logout_msg.getText();
    }

    @Override
    public String getWaitElementXpath() {
        return "//span[text()='请重新登录']";
    }

    public LogoutPage(WebDriver driver){
        super(driver);
    }
}
