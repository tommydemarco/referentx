package com.DLBCSPSE01.referentx.model;

public enum CitationStyle {
    APA_7TH("apa_7th", "APA 7th Edition"),
    MLA("mla", "MLA"),
    CHICAGO("chicago", "Chicago Style");

    private final String value;
    private final String label;

    CitationStyle(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}