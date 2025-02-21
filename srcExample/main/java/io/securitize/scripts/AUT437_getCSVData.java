package io.securitize.scripts;

import com.opencsv.bean.CsvToBeanBuilder;
import io.securitize.infra.dataGenerator.InvestorDataObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AUT437_getCSVData {

    public static void main(String[] args) {
        List<InvestorDataObject> beans = new ArrayList<InvestorDataObject>();
        try {
            beans = new CsvToBeanBuilder(new FileReader("testData.csv"))
                    .withType(InvestorDataObject.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        beans.remove(0);
        System.out.println(beans.toString());
    }

}