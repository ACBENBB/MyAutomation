package io.securitize.infra.googleapi;

public enum TestsSkipList {
    // true means a real actual sheet
    // false means a global term and not a real sheet
    UI(true),
    API(true),
    ALL(false),
    NONE(false);

    private final boolean isRealSheet;

    TestsSkipList(final boolean isRealSheet) {
        this.isRealSheet = isRealSheet;
    }

    public boolean isRealSheet() {
        return isRealSheet;
    }
}
