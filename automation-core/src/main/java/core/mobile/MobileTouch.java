package core.mobile;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public interface MobileTouch {

    void opSwipeScroll(PointOption from, PointOption to);

    void opDragAndDrop(PointOption from, PointOption to);

    void opMultiTouch(TouchAction... actions);

}
