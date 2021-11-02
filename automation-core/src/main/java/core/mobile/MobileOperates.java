package core.mobile;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;

public class MobileOperates extends TouchAction implements MobileTouch {

    TouchAction tAction;
    MultiTouchAction mAction;

    public MobileOperates(PerformsTouchActions performsTouchActions) {
        super(performsTouchActions);
        tAction = new TouchAction(performsTouchActions);
        mAction = new MultiTouchAction(performsTouchActions);
    }


    @Override
    public void opSwipeScroll(PointOption from, PointOption to) {
        tAction.press(from).moveTo(to).release().perform();
    }

    public void opSwipeScrollWithWait(PointOption from, PointOption to, long millis) {
        //调整滑动的时间
        Duration duration = Duration.ofMillis(millis);
        WaitOptions waitOptions = WaitOptions.waitOptions(duration);
        tAction.press(from).waitAction(waitOptions).moveTo(to).release().perform();
    }

    @Override
    public void opDragAndDrop(PointOption from, PointOption to) {
        tAction.press(from).perform();
        tAction.moveTo(to).release().perform();
    }

    @Override
    public void opMultiTouch(TouchAction... actions) {
        for(TouchAction action:actions){
            mAction.add(action);
        }
        mAction.perform();
    }

}
