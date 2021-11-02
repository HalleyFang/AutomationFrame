package core.report;

import java.util.ArrayList;
import java.util.List;

public class TestCaseResult {

    private String name;
    private boolean skip;
    private boolean isSuccess;
    private String filePath;
    private String description;
    private List<OperationResult> operationResults = new ArrayList<>();

    public TestCaseResult(String name, String filePath, String description, boolean skip, boolean isSuccess) {
        this.name = name;
        this.skip = skip;
        this.isSuccess = isSuccess;
        this.filePath = filePath;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void addStepResult(OperationResult operationResult) {
        this.operationResults.add(operationResult);
    }

    public List<OperationResult> getOperationResults() {
        return operationResults;
    }

    public boolean isSkip() {
        return skip;
    }

    public boolean isSuccess() {
        return isSuccess;
    }


}
