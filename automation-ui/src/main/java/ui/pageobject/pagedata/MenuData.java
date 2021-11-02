package ui.pageobject.pagedata;

/**
 * 菜单枚举
 */
public enum MenuData {
    SYSTEM_CONFIG("系统配置"),
    TASK_SCHEDULE("计划任务"),
    TASK_MONITOR("任务监控"),
    TASK_BUSINESS_NUMBER("业务编号"),
    DEMO_MENU("示例");

    private String name;

    MenuData(String name) {
        this.name = name;
    }

    public String getMenu() {
        return name;
    }

}
