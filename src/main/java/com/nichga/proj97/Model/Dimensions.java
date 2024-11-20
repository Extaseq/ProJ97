package com.nichga.proj97.Model;

import com.fasterxml.jackson.annotation.*;

public class Dimensions {
    private String height;
    private String width;

    @JsonProperty("height")
    public String getHeight() { return height; }
    @JsonProperty("height")
    public void setHeight(String value) { this.height = value; }

    @JsonProperty("width")
    public String getWidth() { return width; }
    @JsonProperty("width")
    public void setWidth(String value) { this.width = value; }
}
