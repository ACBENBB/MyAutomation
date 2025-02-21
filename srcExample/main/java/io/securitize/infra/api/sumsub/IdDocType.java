package io.securitize.infra.api.sumsub;

// list from: https://developers.sumsub.com/api-reference/#supported-document-types
public enum IdDocType {
    DRIVERS("NEW_FILE_UPLOAD"),
    PASSPORT("NEW_FILE_UPLOAD"),
    ID_CARD("NEW_FILE_UPLOAD"),
    SELFIE("NEW_ID_SELFIE");

    private final String levelName;

    IdDocType(final String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }
}