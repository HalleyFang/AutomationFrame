package android.pageobject;

import core.mobile.MobileOperates;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;

/**
 * 58同城app测试
 */
public class MobileTestPage extends BasePage {

    @AndroidFindBy(xpath = "//android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/" +
            "android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/" +
            "android.widget.TextView[1]")
    @iOSXCUITFindBy(xpath = "//android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/" +
            "android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/" +
            "android.widget.TextView[1]")
    private MobileElement searchBar;

    @AndroidFindBy(xpath = "//android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout" +
            "/android.widget.LinearLayout[1]/android.widget.GridView/android.view.ViewGroup[3]" +
            "/android.widget.RelativeLayout/android.widget.ImageView")
    @iOSXCUITFindBy(xpath = "//android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout" +
            "/android.widget.LinearLayout[1]/android.widget.GridView/android.view.ViewGroup[3]" +
            "/android.widget.RelativeLayout/android.widget.ImageView")
    private MobileElement rentingHouse;

    @AndroidFindBy(xpath = "//android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/" +
            "android.widget.LinearLayout[2]/android.widget.GridView/android.view.ViewGroup[3]/" +
            "android.widget.RelativeLayout/android.widget.ImageView")
    @iOSXCUITFindBy(xpath = "//android.view.ViewGroup/android.view.ViewGroup/android.widget.LinearLayout/" +
            "android.widget.LinearLayout[2]/android.widget.GridView/android.view.ViewGroup[3]/" +
            "android.widget.RelativeLayout/android.widget.ImageView")
    private MobileElement newCars;

    MobileOperates mOperates;

    public MobileTestPage(PerformsTouchActions performsTouchActions) {
        super(performsTouchActions);
        mOperates = new MobileOperates(performsTouchActions);
    }

    /**
     * swipe 滑动
     */
    public void swipeTest() {
        //由屏幕的宽和高来决定滑动的起始点和终止点
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        //向下滑动
        //滑动的起始点坐标
        PointOption pointOption1 = PointOption.point(width / 2, height / 4);
        //滑动的终止点坐标
        PointOption pointOption2 = PointOption.point(width / 2, height * 3 / 4);
        mOperates.opSwipeScrollWithWait(pointOption1, pointOption2, 2000);
    }
}
