package core.ui.pageoperates;

import org.openqa.selenium.WebElement;

/**
 * 页面元素操作
 */
public interface Operates {

    void input(WebElement element, String value) throws InterruptedException;

    void click(WebElement element);

    void url(String url);

    void lwSelect(WebElement element, String value) throws InterruptedException;

    void upload(String filePath, String browser) throws InterruptedException;
}
