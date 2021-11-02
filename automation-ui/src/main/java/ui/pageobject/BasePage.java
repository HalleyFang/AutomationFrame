package ui.pageobject;

import core.ui.pageoperates.OperatesMethods;
import core.ui.utils.WaitUntil;
import core.utils.GetBeanFactoryUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.config.BaseData;
import ui.config.OpenType;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * 页面基类
 */
public abstract class BasePage {
    private Logger logger = Logger.getLogger(BasePage.class);

    WebDriver driver;

    OperatesMethods operatesMethods = (OperatesMethods) GetBeanFactoryUtil.getBean(OperatesMethods.class);

    //用户信息
    @FindBy(xpath = "//div[@class='pop-btn user']")
    @CacheLookup
    private WebElement userinfo;

    private WebElement save;

    @FindBy(xpath = "//span[text()='退出']")
    private WebElement logout;

    public WebElement getUserinfo() {
        return userinfo;
    }

    public WebElement getSave() {
        return save;
    }

    public void setSave(WebElement save) {
        this.save = this.driver.findElement(By.xpath(button("保存")));
    }

    public String getUserinfoMsg() {
        return userinfo.getText();
    }

    public LogoutPage logout() {
        userinfo.click();
        logout.click();
        return new LogoutPage(driver);
    }

    /**
     * 元素xpath用于验证页面是否成功加载
     *
     * @return
     */
    public abstract String getWaitElementXpath();

    /**
     * 根据按钮名称生成xpath
     *
     * @param text
     * @return
     */
    public String button(String text) {
        return "//button/span[text()='" + text + "']";
    }

    /**
     * 根据input label名称生成xpath
     *
     * @param text
     * @return
     */
    public String input(String text) {
        return "//div/label[text()='" + text + "']/following-sibling::div/descendant::input";
    }

    /**
     * 根据textarea label名称生成xpath
     *
     * @param text
     * @return
     */
    public String textarea(String text) {
        return "//div/label[text()='" + text + "']/following-sibling::div/descendant::textarea";
    }

    /**
     * 根据uploadFile label名称生成xpath
     *
     * @param text
     * @return
     */
    public String uploadFile(String text) {
        return "//div/label[text()='" + text + "']/parent::div//button[string()='点击上传']";
    }

    /**
     * 根据页签名称生成xpath
     *
     * @param text
     * @return
     */
    public String clickTab(String text) {
        return "//div[@class='el-tabs__item' and text()='" + text + "']";
    }

    /**
     * 根据列表行和列生成xpath（列表列数较少，当前页面足够展示，不会生成left和right）
     *
     * @param row
     * @param column
     * @param wait
     * @return
     */
    public String getTableListXpath(int row, String column, WebDriverWait wait) {
        if (column.equalsIgnoreCase("勾选")) {
            return "//div[@class='el-table__header-wrapper']/table/thead/tr[" + row + "]/td//input[@type='checkbox']";
        }
        String[] titleArray = wait.until(presenceOfElementLocated(By.xpath("//div[@class='el-table__header-wrapper']/table/thead/tr"))).getText().split(" |\\n");
        int columnNum = 0;
        if (row > 0 && StringUtils.isNoneBlank(column)) {
            for (int i = 0; i < titleArray.length; i++) {
                if (column.equalsIgnoreCase(titleArray[i])) {
                    columnNum = i + 3;
                    break;
                }
            }
            return "//div[contains(@class,'el-table__body-wrapper')]//tbody/tr[@class='el-table__row '][" + row + "]/td[" + columnNum + "]";
        }
        return null;
    }

    /**
     * 公共操作：输入内容查询列表
     *
     * @param value
     * @throws InterruptedException
     */
    public void search(String value) throws InterruptedException {

    }

    /**
     * 公共操作：选择查询条件并输入内容查询列表
     *
     * @param condition
     * @param value
     * @throws InterruptedException
     */
    public void search(String condition, String value) throws InterruptedException {

        search(value);
    }

    /**
     * 公共操作：获取提示信息内容
     *
     * @return
     */
    public String getMessageBox() {
        return "";
    }

    BasePage(WebDriver driver) {
        this(driver, OpenType.OPEN);
    }

    BasePage(WebDriver driver, OpenType openType) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, 5, 1);
        if (OpenType.OPEN.equals(openType)) {
            operatesMethods.url(BaseData.getUrl());
        }
        if (!StringUtils.isEmpty(this.getWaitElementXpath())) {
            if (WaitUntil.waitUntil(wait, this.getWaitElementXpath())) {
                PageFactory.initElements(driver, this);
            } else {
                try {
                    throw new Exception("页面初始化失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
