package com.nichga.proj97.Model;

import java.util.List;

public class Books {
    String kind;
    String id;
    String etag;
    String selfLink;
    VolumeInfo volumeInfo;
    LayerInfo layerInfo;
    SaleInfo saleInfo;
    AccessInfo accessInfo;

    public String getKind() { return kind; }
    public void setKind(String kind) { this.kind = kind; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEtag() { return etag; }
    public void setEtag(String etag) { this.etag = etag; }

    public String getSelfLink() { return selfLink; }
    public void setSelfLink(String selfLink) { this.selfLink = selfLink; }

    public VolumeInfo getVolumeInfo() { return volumeInfo; }
    public void setVolumeInfo(VolumeInfo volumeInfo) { this.volumeInfo = volumeInfo; }

    public LayerInfo getLayerInfo() { return layerInfo; }
    public void setLayerInfo(LayerInfo layerInfo) { this.layerInfo = layerInfo; }

    public SaleInfo getSaleInfo() { return saleInfo; }
    public void setSaleInfo(SaleInfo saleInfo) { this.saleInfo = saleInfo; }

    public AccessInfo getAccessInfo() { return accessInfo; }
    public void setAccessInfo(AccessInfo accessInfo) { this.accessInfo = accessInfo; }

    @Override
    public String toString() {
        // Đảm bảo rằng volumeInfo, authors, và publisher không phải null
        String authors = (getVolumeInfo().getAuthors() != null && !getVolumeInfo().getAuthors().isEmpty())
                ? String.join(", ", getVolumeInfo().getAuthors())
                : "Unknown Author";

        // ISBN-10 xử lý nếu không có dữ liệu
        String isbn = "Unknown ISBN";
        List<IndustryIdentifier> industryIdentifiers = getVolumeInfo().getIndustryIdentifiers();
        if (industryIdentifiers != null && !industryIdentifiers.isEmpty()) {
            isbn = industryIdentifiers.get(0).getIdentifier(); // Lấy ISBN đầu tiên
        }

        // Lấy năm xuất bản
        String publishedYear = "Unknown Year";
        if (getVolumeInfo().getPublishedDate() != null && getVolumeInfo().getPublishedDate().length() >= 4) {
            publishedYear = getVolumeInfo().getPublishedDate().substring(0, 4);
        }

        return "ID: " + getId() + "\n"
                + "Title: " + getVolumeInfo().getTitle() + "\n"
                + "Authors: " + authors + "\n"
                + "Publisher: " + (getVolumeInfo().getPublisher() != null ? getVolumeInfo().getPublisher() : "Unknown Publisher") + "\n"
                + "ISBN-10: " + isbn + "\n"
                + "PublishedYear: " + publishedYear + "\n";
    }
}
