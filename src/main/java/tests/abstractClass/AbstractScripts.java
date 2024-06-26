package tests.abstractClass;

import org.testng.Assert;
import pageObjects.Sport5.Sport5_MainPage;
import tests.testData.TestData;

public abstract class AbstractScripts extends AbstractBaseTest {


    public enum TestScenario {
        Sport5("Sport 5");
        private final String displayName;

        TestScenario(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public TestData createTestDataObject(AbstractScripts.TestScenario testScenario) {
        return new TestData(testScenario);
    }

    public void verifyPageTitle(String expected) {
        Sport5_MainPage s5 = new Sport5_MainPage(getBrowser());
        String actual = s5.getSport5Title();
        Assert.assertEquals(actual, expected);
    }

    public abstract String getWebsiteName();


}
