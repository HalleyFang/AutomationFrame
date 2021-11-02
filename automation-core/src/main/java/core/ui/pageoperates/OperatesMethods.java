package core.ui.pageoperates;

import core.testplan.TestPlanExec;
import core.ui.captcha.CaptchaOperate;
import core.ui.utils.Coordinate;
import core.ui.utils.GetCaptcha;
import core.ui.utils.ScreenShot;
import core.utils.GetTime;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class OperatesMethods implements Operates, InfoText, ActionsOperates, CaptchaOperate {

    private Logger logger = Logger.getLogger(OperatesMethods.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setActions(WebDriver driver) {
        this.actions = new Actions(driver);
    }

    @Override
    public void input(WebElement element, String value) throws InterruptedException {
        if (element != null && element.isEnabled()) {
            element.click();
            Thread.sleep(500);
            element.sendKeys(Keys.CONTROL, "a");
            element.sendKeys(Keys.DELETE);
//        element.clear();
            Thread.sleep(500);
            element.sendKeys(value);
        } else {
            try {
                throw new Exception(" can not find element.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void click(WebElement element) {
        if (element != null && element.isEnabled()) {
            element.click();
        } else {
            try {
                throw new Exception(" can not find element :");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
//        element.click();
    }

    @Override
    public void url(String url) {
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {

        }
        driver.get(url);
    }

    @Override
    public void lwSelect(WebElement element, String value) throws InterruptedException {
        element.click();
        Boolean readonly = Boolean.valueOf(element.getAttribute("readonly"));
        if (!readonly) {
            Thread.sleep(500);
            element.clear();
            Thread.sleep(500);
            element.sendKeys(value);
        }
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.xpath("//li//span[text()='" + value + "']"));
//        WebElement elementToClick = getElement("//li/span/span[text()='"+value+"']");
        if (elements.size() == 0) {
            elements = driver.findElements(By.xpath("//li[text()='" + value + "']"));
        }
        if (elements.size() > 0) {
            WebElement elementToClick = elements.get(0);
            elementToClick.click();
        }
    }

    public void lwSelectWithSearch(WebElement element, String searchValue, String chooseValue) throws InterruptedException {
        element.click();
        Thread.sleep(500);
        element.clear();
        Thread.sleep(500);
        element.sendKeys(searchValue);
        Thread.sleep(1000);
        WebElement elementToClick = getElement("//li/span/span[text()='" + chooseValue + "']");
        if (elementToClick != null) {
            elementToClick.click();
        } else {
            elementToClick = getElement("//li[text()='" + chooseValue + "']");
            if (elementToClick != null) {
                elementToClick.click();
            }
        }
    }

    @Override
    public void upload(String filePath, String browser) {
        //todo
        String path = System.getProperty("user.dir") + File.separator + "test.exe";
        File file = new File(filePath);
        String cmd = "\"" + path + "\"" + " " + "\"" + browser + "\"" + " " + "\"" + file.getAbsolutePath() + "\"";
        if (file.exists()) {
            try {
                Process p = Runtime.getRuntime().exec(cmd);
                p.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getText(WebElement webElement) {
        String text = null;
        try {
            ExpectedCondition<WebElement> element_tmp = ExpectedConditions.visibilityOf(webElement);
            WebElement element = wait.until(element_tmp);
            if (element != null) {
                text = element.getText();
            }
        } catch (Exception e) {
            logger.error("没有找到控件:" + webElement.toString());
        }
        return text;
    }

    @Override
    public void inputCaptcha() throws InterruptedException, IOException {
        ScreenShot screenShot = new ScreenShot();
        WebElement element = getElement("//div[@class='lw-login-form-item captcha']/img");
        Dimension size = driver.manage().window().getSize();
        Coordinate coordinate = screenShot.windowsSize(size);
        String pName = "testCaptcha_" + GetTime.getCurrentTimeM();
        //根据浏览器窗口大小判断验证码位置截取验证码并处理后保存为png(保存是为了问题定位方便)
        screenShot.captchaScreenShot(driver, pName, element, coordinate);
        //识别图片验证码
        GetCaptcha getCaptcha = new GetCaptcha();
        String captcha = getCaptcha.getCaptcha(pName);
        logger.debug("captcha:" + captcha);
        //输入识别后的验证码
        input(getElement("//div[@class='lw-login-form-item captcha']/input"), captcha);
    }

    public WebElement getElement(String xpath) {
        WebElement element = null;
        try {
            ExpectedCondition<WebElement> element_tmp = ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath));
            element = wait.until(element_tmp);
        } catch (Exception e) {
            logger.error("没有找到控件:" + xpath);
        }
        return element;
    }

    public void exit(Object service) {
        if (service == null) {
            driver.quit();
        } else {
            Map<String, Object> runParameters = (Map<String, Object>) TestPlanExec.threadLocal.get();
            switch (runParameters.get("browser").toString()) {
                case "firefox":
                    driver.quit();
                    break;
                case "chrome":
                    driver.quit();
                    ChromeDriverService chromeDriverService = (ChromeDriverService) service;
                    chromeDriverService.stop();
                    break;
                case "ie":
                    driver.quit();
                    InternetExplorerDriverService internetExplorerDriverService = (InternetExplorerDriverService) service;
                    internetExplorerDriverService.stop();
                    break;
                case "edge":
                    driver.quit();
                    EdgeDriverService edgeDriverService = (EdgeDriverService) service;
                    edgeDriverService.stop();
                    break;
                default:
                    driver.quit();
                    break;
            }
        }
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    @Override
    public void doubleClick(WebElement element) {
        if (element != null) {
            actions.doubleClick(element).perform();
        }
    }

    @Override
    public void moveToElement(WebElement element) {
        if (element != null) {
            actions.moveToElement(element).perform();
        }
    }

    @Override
    public void contextClick(WebElement element) {
        if (element != null) {
            actions.contextClick(element).perform();
        }
    }

    @Override
    public void dragAndDrop(WebElement element, int x, int y) {
        if (element != null) {
            actions.dragAndDropBy(element, x, y).perform();
        }
    }

    @Override
    public void dragAndDrop(WebElement element, WebElement elementTo) {
        if (element != null && elementTo != null) {
            actions.dragAndDrop(element, elementTo).perform();
        }
    }

    @Override
    public void mouseClickAndHold(WebElement element) {
        if (element != null) {
            actions.clickAndHold(element).perform();
        }
    }

    @Override
    public void mouseRelease(WebElement element) {
        if (element != null) {
            actions.release(element).perform();
        }
    }

    //移动到元素element对象的“顶端”与当前窗口的“顶部”对齐
    public void scrollElementTop(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    //移动到元素element对象的“底端”与当前窗口的“底部”对齐
    public void scrollElementBottom(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    //移动到页面最底部
    public void scrollBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    //移动到窗口绝对位置坐标
    public void scrollTo(int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(x, y)");
    }

    //移动到指定的坐标(相对当前的坐标移动)
    public void scrollBy(int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(x, y)");
    }
}
