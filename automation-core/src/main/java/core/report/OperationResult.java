package core.report;

import core.ui.utils.ScreenShot;

import java.io.File;

public class OperationResult {

    private boolean success;
    private String operation;
    private String message;
    private String screenshot;

    public OperationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = ScreenShot.failedImageDir + File.separator + screenshot + ".png";
    }

}
