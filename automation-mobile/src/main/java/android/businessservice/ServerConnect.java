package android.businessservice;

import android.pageobject.pagedata.BaseData;
import io.appium.java_client.MobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ServerConnect {

    public void serverConnect(MobileDriver driver, String url) {
        try {
            WebElement urlElement = driver.findElement(By.id(BaseData.URL_INPUT_ID.getId()));
            WebElement confirmButton = driver.findElement(By.id(BaseData.CONFIRM_BUTTON_ID.getId()));
            urlElement.sendKeys(url);
            confirmButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseDiverMenu(MobileDriver driver) {
        try {
            WebElement dElement = driver.findElement(By.xpath("//android.widget.TextView[3]"));
            dElement.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
