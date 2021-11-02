package core.ui.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetWait {

    public WebDriverWait getWebWait(ChromeDriver driver) {
        int wait_time = 0;
        WebDriverWait wait = new WebDriverWait(driver, wait_time);
        return wait;
    }
}
