package com.nichga.proj97.Model;

import com.fasterxml.jackson.annotation.*;

public class Books {
    private String kind;
    private String id;
    private String etag;
    private String selfLink;
    private VolumeInfo volumeInfo;
    private LayerInfo layerInfo;
    private SaleInfo saleInfo;
    private AccessInfo accessInfo;

    @JsonProperty("kind")
    public String getKind() { return kind; }
    @JsonProperty("kind")
    public void setKind(String value) { this.kind = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("etag")
    public String getEtag() { return etag; }
    @JsonProperty("etag")
    public void setEtag(String value) { this.etag = value; }

    @JsonProperty("selfLink")
    public String getSelfLink() { return selfLink; }
    @JsonProperty("selfLink")
    public void setSelfLink(String value) { this.selfLink = value; }

    @JsonProperty("volumeInfo")
    public VolumeInfo getVolumeInfo() { return volumeInfo; }
    @JsonProperty("volumeInfo")
    public void setVolumeInfo(VolumeInfo value) { this.volumeInfo = value; }

    @JsonProperty("layerInfo")
    public LayerInfo getLayerInfo() { return layerInfo; }
    @JsonProperty("layerInfo")
    public void setLayerInfo(LayerInfo value) { this.layerInfo = value; }

    @JsonProperty("saleInfo")
    public SaleInfo getSaleInfo() { return saleInfo; }
    @JsonProperty("saleInfo")
    public void setSaleInfo(SaleInfo value) { this.saleInfo = value; }

    @JsonProperty("accessInfo")
    public AccessInfo getAccessInfo() { return accessInfo; }
    @JsonProperty("accessInfo")
    public void setAccessInfo(AccessInfo value) { this.accessInfo = value; }
}
