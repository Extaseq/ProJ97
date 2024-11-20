package com.nichga.proj97.Model;

import com.fasterxml.jackson.annotation.*;

public class Layer {
    private String layerID;
    private String volumeAnnotationsVersion;

    @JsonProperty("layerId")
    public String getLayerID() { return layerID; }
    @JsonProperty("layerId")
    public void setLayerID(String value) { this.layerID = value; }

    @JsonProperty("volumeAnnotationsVersion")
    public String getVolumeAnnotationsVersion() { return volumeAnnotationsVersion; }
    @JsonProperty("volumeAnnotationsVersion")
    public void setVolumeAnnotationsVersion(String value) { this.volumeAnnotationsVersion = value; }
}
