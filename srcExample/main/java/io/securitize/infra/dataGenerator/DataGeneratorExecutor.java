package io.securitize.infra.dataGenerator;

public class DataGeneratorExecutor {

    //this main method runs the data generator
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "/src/main/java/io/securitize/infra/dataGenerator/";
        SecIDInvestorGenerator gen = new SecIDInvestorGenerator(path + "testData.csv");
        gen.generate();
    }

}
