package io.securitize.infra.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import io.securitize.infra.dataGenerator.InvestorDataObject;
import io.securitize.tests.InvestorDetails;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class CSVUtils {
    private static final String DEFAULT_SEPARATOR = ",";
    private BufferedReader reader;
    private List<String> lines;
    private String errorMessage;

    public CSVUtils(File file) {
        try {
            if (!file.exists()) {
                waitForFileToDownload(3000);
            }
            this.reader = new BufferedReader(new FileReader(file));
            lines = this.reader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            errorMessage = "Error parsing date: " + e.getMessage();
            errorAndStop(errorMessage, true);
        } catch (InterruptedException e) {
            errorMessage = "Error parsing date: " + e.getMessage();
            errorAndStop(errorMessage, true);
            Thread.currentThread().interrupt();
        }
    }

    public String[] getRow(int rowNumber) {
        return lines.get(rowNumber).split(DEFAULT_SEPARATOR);
    }

    public int getRowCount() {
        return lines.size();
    }

    private void waitForFileToDownload(int millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    public String getCSVInvestorDetailByIndex(int index, String tableColumn) {
        String[] investorCSVRow = lines.get(index).split(DEFAULT_SEPARATOR);
        int columnCSVIndex = getCSVColumnIndex(tableColumn);
        return investorCSVRow[columnCSVIndex];
    }

    public int getCSVColumnIndex(String name) {
        String[] headers = lines.get(0).split(DEFAULT_SEPARATOR);
        for (int i = 0; i < headers.length; i++) {
            String currentColumnHeader = headers[i];
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }
        throw new RuntimeException("Unable to find column with header name of " + name);
    }

    public void updateCSVInvestorEmailByIndex(int index, String tableColumn, String newEmail, String csvPath) throws IOException {

        String[] investorCSVRow = lines.get(index).split(DEFAULT_SEPARATOR);
        int columnCSVIndex = getCSVColumnIndex(tableColumn);

        investorCSVRow[columnCSVIndex] = newEmail;

        String[] headers = lines.get(0).split(DEFAULT_SEPARATOR);

        CSVWriter writer = new CSVWriter(new FileWriter(csvPath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        writer.writeNext(headers);
        writer.writeNext(investorCSVRow);
        writer.flush();
        writer.close();

    }

    public void updateMultipleInvestorEmail(String tableColumn, String firstValue, String secondValue, String thirdValue, String fourthValue, String csvPath) throws IOException, CsvException {

        int columnCSVIndex = getCSVColumnIndex(tableColumn);

        String[] firstInvestorCSVRow = lines.get(1).split(DEFAULT_SEPARATOR);
        firstInvestorCSVRow[columnCSVIndex] = firstValue;

        String[] secondInvestorCSVRow = lines.get(2).split(DEFAULT_SEPARATOR);
        secondInvestorCSVRow[columnCSVIndex] = secondValue;

        String[] thirdInvestorCSVRow = lines.get(3).split(DEFAULT_SEPARATOR);
        thirdInvestorCSVRow[columnCSVIndex] = thirdValue;

        String[] fourthInvestorCSVRow = lines.get(4).split(DEFAULT_SEPARATOR);
        fourthInvestorCSVRow[columnCSVIndex] = fourthValue;

        String[] headers = lines.get(0).split(DEFAULT_SEPARATOR);

        CSVWriter writer = new CSVWriter(new FileWriter(csvPath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        writer.writeNext(headers);
        writer.writeNext(firstInvestorCSVRow);
        writer.writeNext(secondInvestorCSVRow);
        writer.writeNext(thirdInvestorCSVRow);
        writer.writeNext(fourthInvestorCSVRow);
        writer.flush();
        writer.close();
    }

    public void updateCsvFileForBulkIssuance(String tableColumn1, String tableColumn2, int rows, String csvPath, String lastName) {

        int columnCSVIndex1 = getCSVColumnIndex(tableColumn1);
        int columnCSVIndex2 = getCSVColumnIndex(tableColumn2);
        String[] headers = lines.get(0).split(DEFAULT_SEPARATOR);
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvPath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeNext(headers);

            for (int i = 1; i <= rows; i++) {

                InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
                String investorEmail = investorDetails.getEmail();
                String[] investorCSVRow = lines.get(i).split(DEFAULT_SEPARATOR);
                investorCSVRow[columnCSVIndex1] = investorEmail;
                investorCSVRow[columnCSVIndex2] = lastName;
                writer.writeNext(investorCSVRow);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            errorMessage = "Error parsing date: " + e.getMessage();
            errorAndStop(errorMessage, true);
        }
    }

    public static List<InvestorDataObject> importTestDataFromCsv(String fileName) {

        List<InvestorDataObject> beans = new ArrayList<InvestorDataObject>();
        try {
            beans = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(InvestorDataObject.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        beans.remove(0);
        return beans;
    }

    public void updateCsvFileForForAutoIssuance(String csvHeader, int rowsCount, String outputCsvPath, String valueToWrite) throws IOException {

        int csvColumnIndex = getCSVColumnIndex(csvHeader);
        String[] headers = lines.get(0).split(DEFAULT_SEPARATOR);
        CSVWriter writer = new CSVWriter(new FileWriter(outputCsvPath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        writer.writeNext(headers);
        for (int i = 1; i < rowsCount; i++) {
            String[] csvRow = lines.get(i).split(DEFAULT_SEPARATOR);
            csvRow[csvColumnIndex] = valueToWrite;
            writer.writeNext(csvRow);
        }
        writer.flush();
        writer.close();

    }

}