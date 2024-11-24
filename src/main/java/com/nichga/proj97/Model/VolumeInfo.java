package com.nichga.proj97.Model;

import java.time.LocalDate;
import java.util.List;

public class VolumeInfo {
    String title;
    List<String> authors;
    String publisher;
    String publishedDate;
    String description;
    List<IndustryIdentifier> industryIdentifiers;
    ReadingModes readingModes;
    long pageCount;
    long printedPageCount;
    Dimensions dimensions;
    String printType;
    List<String> categories;
    double averageRating;
    long ratingsCount;
    String maturityRating;
    boolean allowAnonLogging;
    String contentVersion;
    PanelizationSummary panelizationSummary;
    ImageLinks imageLinks;
    String language;
    String previewLink;
    String infoLink;
    String canonicalVolumeLink;

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> value) { this.authors = value; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String value) { this.publisher = value; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String value) { this.publishedDate = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public List<IndustryIdentifier> getIndustryIdentifiers() { return industryIdentifiers; }
    public void setIndustryIdentifiers(List<IndustryIdentifier> value) { this.industryIdentifiers = value; }

    public ReadingModes getReadingModes() { return readingModes; }
    public void setReadingModes(ReadingModes value) { this.readingModes = value; }

    public long getPageCount() { return pageCount; }
    public void setPageCount(long value) { this.pageCount = value; }

    public long getPrintedPageCount() { return printedPageCount; }
    public void setPrintedPageCount(long value) { this.printedPageCount = value; }

    public Dimensions getDimensions() { return dimensions; }
    public void setDimensions(Dimensions value) { this.dimensions = value; }

    public String getPrintType() { return printType; }
    public void setPrintType(String value) { this.printType = value; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> value) { this.categories = value; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double value) { this.averageRating = value; }

    public long getRatingsCount() { return ratingsCount; }
    public void setRatingsCount(long value) { this.ratingsCount = value; }

    public String getMaturityRating() { return maturityRating; }
    public void setMaturityRating(String value) { this.maturityRating = value; }

    public boolean getAllowAnonLogging() { return allowAnonLogging; }
    public void setAllowAnonLogging(boolean value) { this.allowAnonLogging = value; }

    public String getContentVersion() { return contentVersion; }
    public void setContentVersion(String value) { this.contentVersion = value; }

    public PanelizationSummary getPanelizationSummary() { return panelizationSummary; }
    public void setPanelizationSummary(PanelizationSummary value) { this.panelizationSummary = value; }

    public ImageLinks getImageLinks() { return imageLinks; }
    public void setImageLinks(ImageLinks value) { this.imageLinks = value; }

    public String getLanguage() { return language; }
    public void setLanguage(String value) { this.language = value; }

    public String getPreviewLink() { return previewLink; }
    public void setPreviewLink(String value) { this.previewLink = value; }

    public String getInfoLink() { return infoLink; }
    public void setInfoLink(String value) { this.infoLink = value; }

    public String getCanonicalVolumeLink() { return canonicalVolumeLink; }
    public void setCanonicalVolumeLink(String value) { this.canonicalVolumeLink = value; }
}
