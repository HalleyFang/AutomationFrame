package ui.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import ui.config.BaseData;
import ui.config.OpenType;

/**
 * 登陆页
 */
public class LoginPage extends BasePage {

    private String url = BaseData.getUrl();

    @CacheLookup
    @FindBy(xpath = "//div[@class='lw-login-form-item']/input")
    private WebElement username;

    @CacheLookup
    @FindBy(xpath = "//div[contains(@class,'lw-login-form-item')]/input[@placeholder='密码']")
    private WebElement password;

    @CacheLookup
    @FindBy(xpath = "//button[contains(text(),'登  录')]")
    private WebElement login_button;


    @CacheLookup
    @FindBy(xpath = "//div[@class='forgot-password-prompt']/span")
    private WebElement forget_password;

    @CacheLookup
    @FindBy(xpath = "//div[@class='login-title']")
    private WebElement login_title;

    public HomePage loginSuccess(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.login_button.click();
        return new HomePage(driver, OpenType.JUMP);
    }

    public void login(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.login_button.click();
    }

    public String loginTitle() {
        return login_title.getText();
    }


    @Override
    public String getWaitElementXpath() {
        return "//button[contains(text(),'登  录')]";
    }

    public LoginPage(WebDriver driver,OpenType openType) {
        super(driver,openType);
    }

    public LoginPage(WebDriver driver) {
        super(driver);
    }

}
