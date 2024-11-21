package com.nichga.proj97.Model;

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
        return "Title: " + getVolumeInfo().getTitle() + "\n" +
                "Authors: " + getVolumeInfo().getAuthors() + "\n" +
                "Publisher: " + getVolumeInfo().getPublisher() + "\n" +
                "ISBN-10: " + getVolumeInfo().getIndustryIdentifiers().getFirst().getIdentifier() + "\n" +
                "Thumbnail: " + getVolumeInfo().getImageLinks().getThumbnail() + "\n";
    }
}
