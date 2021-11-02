package ui.pageobject.pagedata;

/**
 * 按钮枚举
 */
public enum ButtonData {
    BUTTON_ADD("新增"),
    BUTTON_UPDATE("编辑"),
    BUTTON_SAVE("保存"),
    BUTTON_CONFIRM("确定"),
    BUTTON_CANCEL("取消"),
    BUTTON_SEARCH("查询"),
    BUTTON_BACK("返回"),
    BUTTON_CHECK("查看"),
    BUTTON_DELETE("删除"),
    BUTTON_RESET("重置");

    private String name;

    ButtonData(String name) {
        this.name = name;
    }

    public String getButton() {
        return name;
    }

}
