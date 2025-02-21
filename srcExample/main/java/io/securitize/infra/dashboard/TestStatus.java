package io.securitize.infra.dashboard;

import io.securitize.infra.dashboard.enums.TestCategory;
import io.securitize.infra.dashboard.enums.TestType;
import jakarta.persistence.*;


@Entity
@Table(name = "testStatus")
@SqlResultSetMapping(name = "TEST_STATUS_MAPPING", classes = {@ConstructorResult(
        targetClass = TestStatus.class, columns = {
        @ColumnResult(name = "testClass"),
        @ColumnResult(name = "testName"),
        @ColumnResult(name = "status"),
        @ColumnResult(name = "failureReason")
})})
@SuppressWarnings("unused")
public class TestStatus {
    // should write when starting and update when done?
    private long id;
    private String testClass;
    private String testName;
    private String description;
    private String status;
    private String startTime;
    private String endTime;
    private long executionId;
    private String browser = "UNDEFINED";
    private String environment; // rc, prod
    private String testType; // api or ui
    private String category; // nightly, manual, CICD
    private String failureReason; // exception type
    private String lastStep; // description of the last step before failure occur

    public TestStatus() {

    }

    public TestStatus(String testClass, String testName, String failureReason) {
        this.testClass = testClass;
        this.testName = testName;
        this.failureReason = failureReason;
    }

    @Id
    @Column(name = "test_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(long executionId) {
        this.executionId = executionId;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType.name();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(TestCategory category) {
        this.category = category.name();
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getLastStep() {
        return lastStep;
    }

    public void setLastStep(String lastStep) {
        this.lastStep = lastStep;
    }
}