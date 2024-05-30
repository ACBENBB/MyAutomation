package tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import tests.abstractClass.AbstractScripts;

public class False_Test extends AbstractScripts {

    public String getTaskNumber() {
        return "A";
    }

    @Test(description = "click on upper menu with one string")
    public void First_False_Test() {
        Assert.assertTrue(false);
        System.out.println("I'm the 1st that not working!");
    }

    @Test(description = "click on upper menu with one string")
    public void Second_False_Test() {
        Assert.assertTrue(true);
        System.out.println("I'm the 2nd and working fine!");
    }

    @Test(description = "click on upper menu with one string")
    public void Third_False_Test() {
        Assert.assertTrue(true);
        System.out.println("I'm the 3rd and working fine!");
    }

    @Test(description = "click on upper menu with one string")
    public void Forth_False_Test() {
        Assert.assertTrue(false);
        System.out.println("I'm the 4th and not working!");
    }
}

