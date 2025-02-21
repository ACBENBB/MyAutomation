package io.securitize.infra.api.healthchecks;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.utils.ResourcesUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.debug;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public abstract class AbstractHealthCheck {

    protected List<HealthCheckEntry> loadHealthCheckEndpoints(String fileName) {
        String healthChecksEndpointsPath;
        healthChecksEndpointsPath = ResourcesUtils.getResourcePathByName("config" + "/" + fileName);
        File healthChecksEndpointsFile = new File(healthChecksEndpointsPath);
        if (!healthChecksEndpointsFile.exists()) {
            errorAndStop("Can't find file of list of health check endpoints in: " + healthChecksEndpointsPath, false);
        }
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        List<HealthCheckEntry> endpoints = null;
        try {
            // due to bug in the CSV reader, we have to manually remove all empty lines
            List<String> lines = Files.readAllLines(Paths.get(healthChecksEndpointsPath));
            lines = lines.stream().filter(l -> l.trim().length() > 0).collect(Collectors.toList());
            byte[] linesAsBytes = StringUtils.join(lines, System.lineSeparator()).getBytes();

            MappingIterator<HealthCheckEntry> HealthCheckEndpoints = new CsvMapper().readerWithSchemaFor(HealthCheckEntry.class).with(schema).readValues(linesAsBytes);
            endpoints = HealthCheckEndpoints.readAll();
        } catch (IOException e) {
            errorAndStop("An error occur trying to parse health checks endpoints file. Details: " + e.toString(), false);
        }

        return endpoints;
    }

    protected RequestSpecification getSpecWithDebugFilter() {
        return given().filter((requestSpec, responseSpec, ctx) -> {
            Response response = ctx.next(requestSpec, responseSpec);

            String fullDebugInfo = System.lineSeparator() + "========================================================================" + System.lineSeparator();
            fullDebugInfo += "healthCheck request: " + requestSpec.getMethod() + " " + requestSpec.getURI() + " -> " + response.getStatusCode();
            String requestAsString = System.lineSeparator() +
                    "Request method:\t" + requestSpec.getMethod() + System.lineSeparator() +
                    "Request URI:\t" + requestSpec.getURI() + System.lineSeparator() +
                    "Request params: " + requestSpec.getRequestParams() + System.lineSeparator() +
                    "Query params:\t" + requestSpec.getQueryParams() + System.lineSeparator() +
                    "Form params:\t" + requestSpec.getFormParams() + System.lineSeparator() +
                    "Headers:\t\t" + requestSpec.getHeaders() + System.lineSeparator() +
                    "Cookies:\t" + requestSpec.getCookies() + System.lineSeparator() + System.lineSeparator() +
                    "Body:\t" + new Prettifier().getPrettifiedBodyIfPossible(requestSpec);
            fullDebugInfo += System.lineSeparator() + "raw request: " + System.lineSeparator() + requestAsString;

            String responseAsString = System.lineSeparator() +
                    response.getStatusLine() + System.lineSeparator() +
                    response.getHeaders() + System.lineSeparator() + System.lineSeparator() +
                    new Prettifier().getPrettifiedBodyIfPossible(response, response.getBody());
            fullDebugInfo += System.lineSeparator() + "raw response: " + System.lineSeparator() + responseAsString;

            fullDebugInfo += System.lineSeparator() + "========================================================================" + System.lineSeparator();
            debug(fullDebugInfo);
            return response;
        });
    }
}
