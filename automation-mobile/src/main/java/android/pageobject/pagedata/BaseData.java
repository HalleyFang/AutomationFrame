package android.pageobject.pagedata;

public enum BaseData {

    PERMISSION_ALLOW_ID("com.android.packageinstaller:id/permission_allow_button"),
    URL_INPUT_ID("com.test.app:id/inputView"),
    CONFIRM_BUTTON_ID("com.test.app:id/confirmTxt");

    private String id;

    BaseData(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
