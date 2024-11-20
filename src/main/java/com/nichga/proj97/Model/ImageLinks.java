package com.nichga.proj97.Model;

import com.fasterxml.jackson.annotation.*;

public class ImageLinks {
    private String smallThumbnail;
    private String thumbnail;
    private String small;
    private String medium;
    private String large;
    private String extraLarge;

    @JsonProperty("smallThumbnail")
    public String getSmallThumbnail() { return smallThumbnail; }
    @JsonProperty("smallThumbnail")
    public void setSmallThumbnail(String value) { this.smallThumbnail = value; }

    @JsonProperty("thumbnail")
    public String getThumbnail() { return thumbnail; }
    @JsonProperty("thumbnail")
    public void setThumbnail(String value) { this.thumbnail = value; }

    @JsonProperty("small")
    public String getSmall() { return small; }
    @JsonProperty("small")
    public void setSmall(String value) { this.small = value; }

    @JsonProperty("medium")
    public String getMedium() { return medium; }
    @JsonProperty("medium")
    public void setMedium(String value) { this.medium = value; }

    @JsonProperty("large")
    public String getLarge() { return large; }
    @JsonProperty("large")
    public void setLarge(String value) { this.large = value; }

    @JsonProperty("extraLarge")
    public String getExtraLarge() { return extraLarge; }
    @JsonProperty("extraLarge")
    public void setExtraLarge(String value) { this.extraLarge = value; }
}
