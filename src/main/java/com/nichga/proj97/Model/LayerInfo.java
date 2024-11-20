package com.nichga.proj97.Model;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class LayerInfo {
    private List<Layer> layers;

    @JsonProperty("layers")
    public List<Layer> getLayers() { return layers; }
    @JsonProperty("layers")
    public void setLayers(List<Layer> value) { this.layers = value; }
}
