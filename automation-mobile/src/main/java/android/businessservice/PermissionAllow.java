package android.businessservice;

import android.pageobject.pagedata.BaseData;
import core.utils.GetTime;
import io.appium.java_client.MobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class PermissionAllow {

    public void permissionAllow(MobileDriver driver) {
        try {
            System.out.println(GetTime.getCurrentTimeM());
            WebElement permissionElement = driver.findElement(By.id(BaseData.PERMISSION_ALLOW_ID.getId()));
            while (permissionElement.isDisplayed()) {
                permissionElement.click();
                permissionElement = driver.findElement(By.id(BaseData.PERMISSION_ALLOW_ID.getId()));
            }
        } catch (Exception e) {
            System.out.println(GetTime.getCurrentTimeM());
            e.printStackTrace();
        }
    }
}
