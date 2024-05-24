package test.java;

import config.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class True_Test extends BaseTest {

    @Override
    public String getTaskNumber() {
        return null;
    }

    @Test(description = "verify table cell text")
    public void First_True_Test() {
        Assert.assertTrue(true);
        System.out.println("I'm the 1st that working fine!");
    }

    @Test(description = "verify table cell text")
    public void Second_True_Test() {
        Assert.assertTrue(true);
        System.out.println("I'm the 2nd that working fine!");
    }

    @Test(description = "click on upper menu with one string")
    public void Third_True_Test() {
        Assert.assertTrue(true);
        System.out.println("I'm the 3rd and working fine!");
    }

    @Test(description = "click on upper menu with one string")
    public void Forth_True_Test() {
        Assert.assertTrue(false);
        System.out.println("I'm the 4th and not working!");
    }

}