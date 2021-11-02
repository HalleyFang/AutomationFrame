package core.data;

public enum TestPlanType {

    INTERFACE_PLAN(1),
    UI_PLAN(2),
    PLAN_ALL(3);

    private Integer type;

    TestPlanType(Integer type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
