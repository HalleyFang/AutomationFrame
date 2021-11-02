package ui.pageobject.pagedata;

/**
 * 状态和提示信息枚举
 */
public enum PromptMessage {

    TASK_STATUS_EXEC("执行中"),
    TASK_STATUS_SUCCESS("执行成功"),
    TASK_STATUS_FAILED("执行失败"),
    TASK_STATUS_SHUTDOWN("宕机终止"),
    ASSERT_NO_RESULT("共 0 条"),
    LOGIN_ERROR("用户名 or 密码不正确"),
    ALERT_TEXT("一段提示信息"),
    ALERT_SUCCESS_TEXT("一段成功信息"),
    SAVE_SUCCESS_MESSAGE("数据已保存");


    private String name;

    PromptMessage(String name) {
        this.name = name;
    }

    public String getMsg() {
        return name;
    }
}
