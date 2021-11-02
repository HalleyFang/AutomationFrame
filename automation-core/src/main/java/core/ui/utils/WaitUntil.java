package core.ui.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 等待元素加载
 *
 * @author Halley.Fang
 */
public class WaitUntil {
    private static Logger logger = Logger.getLogger(WaitUntil.class);

    public synchronized static boolean waitUntil(WebDriverWait wait, String xpath) {
        try {
            ExpectedCondition<WebElement> element = ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath));
            wait.until(element);
        } catch (Exception waituntilException) {
            //waituntilException.printStackTrace();
            logger.error("----wait time out----xpath:" + xpath);
            return false;
        }
        return true;
    }

    public synchronized static Boolean waitUntilIsEnable(WebDriverWait wait, String xpath) {
        Boolean enable_flag = false;
        try {
            ExpectedCondition<WebElement> element = ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath));
            enable_flag = wait.until(element).isEnabled();
        } catch (Exception waituntilException) {
            //waituntilException.printStackTrace();
            logger.error("----wait time out----xpath:" + xpath);
        }
        return enable_flag;
    }

    public synchronized static Boolean waitUntilisSelected(WebDriverWait wait, String xpath) {
        Boolean enable_flag = false;
        try {
            ExpectedCondition<WebElement> element = ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath));
            enable_flag = wait.until(element).isSelected();
        } catch (Exception waituntilException) {
            //waituntilException.printStackTrace();
            logger.error("----wait time out----xpath:" + xpath);
        }
        return enable_flag;
    }

    public synchronized static Boolean waitUntilisDisplayed(WebDriverWait wait, String xpath) {
        Boolean enable_flag = false;
        try {
            ExpectedCondition<WebElement> element = ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath));
            enable_flag = wait.until(element).isDisplayed();
        } catch (Exception waituntilException) {
            //waituntilException.printStackTrace();
            logger.error("----wait time out----xpath:" + xpath);
        }
        return enable_flag;
    }

    public synchronized static Boolean waitUntilExesist(WebDriverWait wait, String xpath) {
        Boolean enable_flag = false;
        try {
            ExpectedCondition<WebElement> element = ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath));
            wait.until(element);
            enable_flag = true;
        } catch (Exception waituntilException) {
            //waituntilException.printStackTrace();
            logger.error("----wait time out----xpath:" + xpath);
        }
        return enable_flag;
    }

}
