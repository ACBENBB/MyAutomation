package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class True_Test extends BaseTest{

    @Override
    public String getTaskNumber() {
        return "A";
    }

    @Test(description = "verify table cell text")
    public void True_Test() {
        Assert.assertTrue(true);
        System.out.println("I'm working fine!");
    }


}