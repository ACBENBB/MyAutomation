package io.securitize.tests.transferAgent.pojo;

public class w8beneTaxForm {



    private String fileKey;
    private String contentType;
    private String formType;

        public w8beneTaxForm() {
        this.formType = "W-8BENE";
        this.contentType = "application/pdf";
        this.fileKey = "";
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

}
