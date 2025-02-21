package io.securitize.scripts.checkapichanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MicroserviceMapping {
    @JsonProperty("MicroServiceName")
    private String microServiceName;

    @JsonProperty("SwaggerUrl")
    private String swaggerUrl;

    @JsonProperty("Comments")
    private String comments;


    public MicroserviceMapping() {
    }

    public void setMicroServiceName(String microServiceName) {
        this.microServiceName = microServiceName;
    }

    public String getMicroServiceName() {
        return microServiceName;
    }

    public String getSwaggerUrl() {
        return swaggerUrl;
    }

    public void setSwaggerUrl(String swaggerUrl) {
        this.swaggerUrl = swaggerUrl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
