package tests.testData;

import tests.abstractClass.AbstractScripts;

public class TestData {

    public String sport5Title;

    public TestData(AbstractScripts.TestScenario testScenario) {
        switch (testScenario) {
            case Sport5:
                this.sport5Title = "אתר ערוץ הספורט";
                break;
        }
    }

}
