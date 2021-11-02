package core.report;


import core.utils.GetTime;

import java.util.ArrayList;
import java.util.List;

public class TestPlanResult {

    private long startTime;
    private long completeTime;
    private List<TestCaseResult> testCaseResults = new ArrayList<>();

    public List<TestCaseResult> getTestCaseResults() {
        return testCaseResults;
    }

    public void addCaseResult(TestCaseResult testCaseResult) {
        testCaseResults.add(testCaseResult);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }

    public String getName() {
        return "teatPlan-" + GetTime.getCurrentTimeM();
    }

    public String getDescription() {
        return getName();
    }

}
