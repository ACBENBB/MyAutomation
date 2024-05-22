package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class False_Test extends BaseTest{
    @Override
    public String getTaskNumber() {
        return "B";
    }

    @Test(description = "click on upper menu with one string")
    public void False_Test() {
        Assert.assertTrue(false);
        System.out.println("I'm not working!");
    }
}

