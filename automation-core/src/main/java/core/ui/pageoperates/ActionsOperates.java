package core.ui.pageoperates;

import org.openqa.selenium.WebElement;

/**
 * 鼠标操作
 */
public interface ActionsOperates {

    void doubleClick(WebElement element);//双击

    void moveToElement(WebElement element);//悬停

    void contextClick(WebElement element);//右击

    void dragAndDrop(WebElement element, WebElement elementTo);//拖拽

    void dragAndDrop(WebElement element, int x, int y);//拖拽

    void mouseClickAndHold(WebElement element);//按住不放

    void mouseRelease(WebElement element);//松开
}
