package tests.Sport5;


import org.testng.annotations.Test;
import tests.abstractClass.AbstractClassSport5;
import tests.Sport5.testData.TestData;

import static infra.reporting.MultiReporter.endTestLevel;
import static infra.reporting.MultiReporter.startTestLevel;


public class S5_Check_Titles extends AbstractClassSport5 {
    @Override
    public String getWebsiteName() {
        return "sport5";
    }

    @Test(description = "Check Title of Sport 5 main page", priority = 1)
    public void S5_Check_Titles_test() {
        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractClassSport5.TestScenario testScenario = TestScenario.Sport5;
        TestData td = createTestDataObject(testScenario);
        endTestLevel();


        verifyPageTitle("אתר ערוץ הספורט");
    }

}
