package com.nichga.proj97.Model;

public class AccessInfo {
    String country;
    String viability;
    boolean embeddable;
    boolean publicDomain;
    String textToSpeechPermission;
    Epub epub;
    Epub pdf;
    String webReaderLink;
    String accessViewStatus;
    boolean quoteSharingAllowed;

    public String getCountry() { return country; }
    public void setCountry(String value) { this.country = value; }

    public String getViability() { return viability; }
    public void setViability(String value) { this.viability = value; }

    public boolean getEmbeddable() { return embeddable; }
    public void setEmbeddable(boolean value) { this.embeddable = value; }

    public boolean getPublicDomain() { return publicDomain; }
    public void setPublicDomain(boolean value) { this.publicDomain = value; }

    public String getTextToSpeechPermission() { return textToSpeechPermission; }
    public void setTextToSpeechPermission(String value) { this.textToSpeechPermission = value; }

    public Epub getEpub() { return epub; }
    public void setEpub(Epub value) { this.epub = value; }

    public Epub getPDF() { return pdf; }
    public void setPDF(Epub value) { this.pdf = value; }

    public String getWebReaderLink() { return webReaderLink; }
    public void setWebReaderLink(String value) { this.webReaderLink = value; }

    public String getAccessViewStatus() { return accessViewStatus; }
    public void setAccessViewStatus(String value) { this.accessViewStatus = value; }

    public boolean getQuoteSharingAllowed() { return quoteSharingAllowed; }
    public void setQuoteSharingAllowed(boolean value) { this.quoteSharingAllowed = value; }
}
