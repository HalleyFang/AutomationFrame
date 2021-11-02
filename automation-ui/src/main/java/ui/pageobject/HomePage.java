package ui.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.config.OpenType;

/**
 * 首页
 */
public class HomePage extends BasePage {

    //用于显示等待判断页面元素加载，页面加载完成后进行PageFactory初始化
    @Override
    public String getWaitElementXpath() {
        return "//iframe[@src='/assets/home.html']";
    }

    //url
    private String url = "/home";

    //元素
    //首页iframe元素
    @FindBy(xpath = "//iframe[@src='/assets/home.html']")
    private WebElement iframe;

    //逻辑代码
    public Boolean isIframeDisplay(){
        return iframe.isDisplayed();
    };

    //PageFactory初始化
    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage(WebDriver driver,OpenType openType){
        super(driver,openType);
    }

}
